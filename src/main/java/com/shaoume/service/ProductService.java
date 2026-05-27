package com.shaoume.service;
import com.shaoume.dto.request.ProductRequest;
import com.shaoume.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
public interface ProductService {
    ProductResponse createProduct(ProductRequest r);
    ProductResponse updateProduct(Long id, ProductRequest r);
    void deleteProduct(Long id);
    ProductResponse getProductById(Long id);
    Page<ProductResponse> getAllProducts(Pageable p);
    Page<ProductResponse> getProductsByCategory(Long cid, Pageable p);
    Page<ProductResponse> getFeaturedProducts(Pageable p);
    Page<ProductResponse> searchProducts(String kw, Pageable p);
    Page<ProductResponse> filterProducts(Long cid, BigDecimal min, BigDecimal max, String brand, Pageable p);
}
