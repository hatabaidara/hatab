package com.shaoume.controller;
import com.shaoume.dto.request.CategoryRequest;
import com.shaoume.dto.response.ApiResponse;
import com.shaoume.dto.response.CategoryResponse;
import com.shaoume.entity.Category;
import com.shaoume.exception.ConflictException;
import com.shaoume.exception.ResourceNotFoundException;
import com.shaoume.repository.CategoryRepository;
import com.shaoume.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
@RestController @RequestMapping("/categories") @RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(categoryRepository.findByActiveTrue().stream().map(this::map).collect(Collectors.toList())));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(map(categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categorie", id)))));
    }
    @PostMapping @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponse>> create(@Valid @RequestBody CategoryRequest r) {
        if (categoryRepository.existsByName(r.getName())) throw new ConflictException("Categorie existante: " + r.getName());
        Category c = new Category();
        c.setName(r.getName());
        c.setDescription(r.getDescription());
        c.setImageUrl(r.getImageUrl());
        c.setIcon("tag");
        c.setColor("#dbeafe");
        c.setActive(true);
        c.setProductCount(0);
        if (r.getParentId() != null) c.setParentId(r.getParentId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created("Categorie creee", map(categoryRepository.save(c))));
    }
    @PutMapping("/{id}") @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest r) {
        Category c = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categorie", id));
        c.setName(r.getName());
        c.setDescription(r.getDescription());
        c.setImageUrl(r.getImageUrl());
        if (r.getParentId() != null) c.setParentId(r.getParentId());
        return ResponseEntity.ok(ApiResponse.success("Mise a jour", map(categoryRepository.save(c))));
    }
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        Category c = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categorie", id));
        c.setActive(false);
        categoryRepository.save(c);
        return ResponseEntity.ok(ApiResponse.success("Supprimee", null));
    }
    private CategoryResponse map(Category c) {
        long count = productRepository.countByCategoryIdAndActiveTrue(c.getId());
        return CategoryResponse.builder().id(c.getId()).name(c.getName()).description(c.getDescription()).imageUrl(c.getImageUrl()).active(c.isActive()).parentId(c.getParentId()).productCount((int) count).createdAt(c.getCreatedAt()).build();
    }
}
