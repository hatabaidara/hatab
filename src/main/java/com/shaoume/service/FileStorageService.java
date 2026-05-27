package com.shaoume.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    public String storeFile(MultipartFile file) throws IOException {
        String ext = "";
        String original = file.getOriginalFilename();
        if (original != null && original.contains("."))
            ext = original.substring(original.lastIndexOf("."));

        String filename = UUID.randomUUID().toString() + ext;
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);
        Files.copy(file.getInputStream(), uploadPath.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + filename;
    }
}
