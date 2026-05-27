package com.shaoume.controller;
import com.shaoume.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileStorageService fileStorageService;

    @PostMapping("/image")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) return ResponseEntity.badRequest().body(Map.of("error","Fichier vide"));
            String ct = file.getContentType();
            if (ct == null || !ct.startsWith("image/"))
                return ResponseEntity.badRequest().body(Map.of("error","Seules les images sont acceptées"));
            String url = fileStorageService.storeFile(file);
            return ResponseEntity.ok(Map.of("url", url));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
}
