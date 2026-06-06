package com.shaoume.controller;

import com.shaoume.dto.response.ApiResponse;
import com.shaoume.entity.User;
import com.shaoume.repository.UserRepository;
import com.shaoume.service.WavePaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/wave")
@RequiredArgsConstructor
public class WavePaymentController {

    private final WavePaymentService wavePaymentService;
    private final UserRepository userRepository;

    @PostMapping("/initiate")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> initiate(
            @AuthenticationPrincipal UserDetails userDetails) {
        User seller = userRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouve"));
        Map<String, Object> result = wavePaymentService.initiatePayment(seller);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/verify/{checkoutId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<Boolean>> verify(@PathVariable String checkoutId) {
        boolean paid = wavePaymentService.verifyPayment(checkoutId);
        return ResponseEntity.ok(ApiResponse.success(paid));
    }

    @GetMapping("/status")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<Boolean>> status(
            @AuthenticationPrincipal UserDetails userDetails) {
        User seller = userRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouve"));
        boolean hasPaid = wavePaymentService.hasValidPayment(seller);
        return ResponseEntity.ok(ApiResponse.success(hasPaid));
    }
}
