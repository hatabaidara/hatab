package com.shaoume.controller;

import com.shaoume.dto.response.ApiResponse;
import com.shaoume.entity.*;
import com.shaoume.entity.enums.MerchantStatus;
import com.shaoume.repository.UserRepository;
import com.shaoume.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;
    private final UserRepository userRepository;

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<Map<String,Object>>> getStatus(
            @AuthenticationPrincipal UserDetails ud) {
        User user = getUser(ud);
        Merchant merchant = merchantService.getMerchantByUser(user);
        if (merchant == null) {
            return ResponseEntity.ok(ApiResponse.success(Map.of(
                "hasMerchant", false,
                "statut", "NONE"
            )));
        }
        return ResponseEntity.ok(ApiResponse.success(Map.of(
            "hasMerchant", true,
            "statut", merchant.getStatut().name(),
            "merchantId", merchant.getId(),
            "shopName", merchant.getShopName() != null ? merchant.getShopName() : ""
        )));
    }

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    public ResponseEntity<ApiResponse<Map<String,Object>>> register(
            @AuthenticationPrincipal UserDetails ud) {
        User user = getUser(ud);
        Merchant merchant = merchantService.createMerchant(user);
        return ResponseEntity.ok(ApiResponse.success(Map.of(
            "merchantId", merchant.getId(),
            "statut", merchant.getStatut().name()
        )));
    }

    @PostMapping("/payment-request")
    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    public ResponseEntity<ApiResponse<String>> submitRequest(
            @AuthenticationPrincipal UserDetails ud,
            @RequestBody Map<String,String> body) {
        User user = getUser(ud);
        Merchant merchant = merchantService.getMerchantByUser(user);
        if (merchant == null) merchant = merchantService.createMerchant(user);
        merchantService.submitPaymentRequest(
            merchant,
            body.get("moyenPaiement"),
            body.get("numeroDeTelephone"),
            body.get("referenceTransaction"),
            body.get("recuImageUrl")
        );
        return ResponseEntity.ok(ApiResponse.success("Demande envoyee"));
    }

    @GetMapping("/my-requests")
    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    public ResponseEntity<ApiResponse<?>> getMyRequests(
            @AuthenticationPrincipal UserDetails ud) {
        User user = getUser(ud);
        Merchant merchant = merchantService.getMerchantByUser(user);
        if (merchant == null) return ResponseEntity.ok(ApiResponse.success(java.util.List.of()));
        return ResponseEntity.ok(ApiResponse.success(merchantService.getMerchantRequests(merchant)));
    }

    // ADMIN
    @GetMapping("/admin/requests")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> getAllRequests(
            @PageableDefault(size=20) Pageable p) {
        return ResponseEntity.ok(ApiResponse.success(merchantService.getAllRequestsDTO(p)));
    }

    @GetMapping("/admin/requests/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> getPending(
            @PageableDefault(size=20) Pageable p) {
        return ResponseEntity.ok(ApiResponse.success(merchantService.getPendingRequestsDTO(p)));
    }

    @PostMapping("/admin/requests/{id}/validate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> validate(@PathVariable Long id) {
        merchantService.validatePayment(id);
        return ResponseEntity.ok(ApiResponse.success("Paiement valide"));
    }

    @PostMapping("/admin/requests/{id}/refuse")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> refuse(
            @PathVariable Long id,
            @RequestBody Map<String,String> body) {
        merchantService.refusePayment(id, body.get("motif"));
        return ResponseEntity.ok(ApiResponse.success("Paiement refuse"));
    }

    private User getUser(UserDetails ud) {
        return userRepository.findByEmail(ud.getUsername())
            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
    }
}
