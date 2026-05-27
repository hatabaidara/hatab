package com.shaoume.controller;
import com.shaoume.dto.response.ApiResponse;
import com.shaoume.dto.response.ProductResponse;
import com.shaoume.repository.UserRepository;
import com.shaoume.service.impl.FavoriteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/favorites") @RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteServiceImpl favoriteService;
    private final UserRepository userRepository;
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> get(@AuthenticationPrincipal UserDetails ud,@PageableDefault(size=12) Pageable p) {
        return ResponseEntity.ok(ApiResponse.success(favoriteService.getUserFavorites(uid(ud),p)));
    }
    @PostMapping("/toggle/{pid}")
    public ResponseEntity<ApiResponse<Void>> toggle(@PathVariable Long pid,@AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(ApiResponse.success(favoriteService.toggleFavorite(uid(ud),pid),null));
    }
    @GetMapping("/check/{pid}")
    public ResponseEntity<ApiResponse<Map<String,Boolean>>> check(@PathVariable Long pid,@AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("isFavorite",favoriteService.isFavorite(uid(ud),pid))));
    }
    private Long uid(UserDetails ud) { return userRepository.findByEmail(ud.getUsername()).orElseThrow().getId(); }
}
