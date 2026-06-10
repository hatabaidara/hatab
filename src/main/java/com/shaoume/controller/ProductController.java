package com.shaoume.controller;
import com.shaoume.dto.request.ProductRequest;
import com.shaoume.dto.response.ApiResponse;
import com.shaoume.dto.response.ProductResponse;
import com.shaoume.service.ProductService;
import com.shaoume.repository.UserRepository;
import com.shaoume.entity.User;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
@RestController @RequestMapping("/products") @RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserRepository userRepository;
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getAll(@PageableDefault(size=12,sort="createdAt",direction=Sort.Direction.DESC) Pageable p) {
        return ResponseEntity.ok(ApiResponse.success(productService.getAllProducts(p)));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(productService.getProductById(id)));
    }
    @GetMapping("/category/{cid}")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getByCategory(@PathVariable Long cid,@PageableDefault(size=12) Pageable p) {
        return ResponseEntity.ok(ApiResponse.success(productService.getProductsByCategory(cid,p)));
    }
    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getFeatured(@PageableDefault(size=8) Pageable p) {
        return ResponseEntity.ok(ApiResponse.success(productService.getFeaturedProducts(p)));
    }
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> search(@RequestParam String keyword,@PageableDefault(size=12) Pageable p) {
        return ResponseEntity.ok(ApiResponse.success(productService.searchProducts(keyword,p)));
    }
    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> filter(
            @RequestParam(required=false) Long categoryId,@RequestParam(required=false) BigDecimal minPrice,
            @RequestParam(required=false) BigDecimal maxPrice,@RequestParam(required=false) String brand,
            @PageableDefault(size=12) Pageable p) {
        return ResponseEntity.ok(ApiResponse.success(productService.filterProducts(categoryId,minPrice,maxPrice,brand,p)));
    }
    @GetMapping("/seller/my-products") @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    public ResponseEntity<ApiResponse<org.springframework.data.domain.Page<ProductResponse>>> getMyProducts(
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault(size=12) Pageable p) {
        String email = userDetails.getUsername();
        User seller = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(ApiResponse.success(
            productService.getSellerProducts(seller.getId(), p)));
    }

    @PostMapping("/seller") @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    public ResponseEntity<ApiResponse<ProductResponse>> createAsSeller(
            @Valid @RequestBody ProductRequest r,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.created("Produit publie", productService.createProductAsSeller(r, userDetails.getUsername())));
    }

    @PostMapping @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProductResponse>> create(@Valid @RequestBody ProductRequest r) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created("Produit créé",productService.createProduct(r)));
    }
    @PutMapping("/{id}") @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProductResponse>> update(@PathVariable Long id,@Valid @RequestBody ProductRequest r) {
        return ResponseEntity.ok(ApiResponse.success("Mis à jour",productService.updateProduct(id,r)));
    }
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success("Supprimé",null));
    }
}
