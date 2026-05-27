package com.shaoume.controller;
import com.shaoume.dto.request.LoginRequest;
import com.shaoume.dto.request.RegisterRequest;
import com.shaoume.dto.response.ApiResponse;
import com.shaoume.dto.response.AuthResponse;
import com.shaoume.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth") @RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest r) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created("Compte créé",authService.register(r)));
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest r) {
        return ResponseEntity.ok(ApiResponse.success("Connexion réussie",authService.login(r)));
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(@RequestParam String refreshToken) {
        return ResponseEntity.ok(ApiResponse.success("Token renouvelé",authService.refreshToken(refreshToken)));
    }
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@AuthenticationPrincipal UserDetails ud) {
        authService.logout(ud.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Déconnexion réussie",null));
    }
}
