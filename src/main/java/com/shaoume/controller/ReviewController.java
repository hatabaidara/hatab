package com.shaoume.controller;
import com.shaoume.dto.request.ReviewRequest;
import com.shaoume.dto.response.ApiResponse;
import com.shaoume.dto.response.ReviewResponse;
import com.shaoume.repository.UserRepository;
import com.shaoume.service.impl.ReviewServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/reviews") @RequiredArgsConstructor
public class ReviewController {
    private final ReviewServiceImpl reviewService;
    private final UserRepository userRepository;
    @GetMapping("/product/{pid}")
    public ResponseEntity<ApiResponse<Page<ReviewResponse>>> getByProduct(@PathVariable Long pid,@PageableDefault(size=10) Pageable p) {
        return ResponseEntity.ok(ApiResponse.success(reviewService.getProductReviews(pid,p)));
    }
    @PostMapping("/product/{pid}")
    public ResponseEntity<ApiResponse<ReviewResponse>> create(@PathVariable Long pid,@Valid @RequestBody ReviewRequest r,@AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created("Avis soumis",reviewService.createReview(uid(ud),pid,r)));
    }
    @GetMapping("/admin/pending") @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<ReviewResponse>>> pending(@PageableDefault(size=20) Pageable p) {
        return ResponseEntity.ok(ApiResponse.success(reviewService.getPendingReviews(p)));
    }
    @PutMapping("/admin/{id}/approve") @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ReviewResponse>> approve(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Approuvé",reviewService.approveReview(id)));
    }
    private Long uid(UserDetails ud) { return userRepository.findByEmail(ud.getUsername()).orElseThrow().getId(); }
}
