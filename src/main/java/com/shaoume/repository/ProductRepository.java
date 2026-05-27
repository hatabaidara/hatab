package com.shaoume.repository;
import com.shaoume.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByActiveTrue(Pageable pageable);
    Page<Product> findByCategoryIdAndActiveTrue(Long categoryId, Pageable pageable);
    Page<Product> findByFeaturedTrueAndActiveTrue(Pageable pageable);
    boolean existsBySku(String sku);
    @Query("SELECT p FROM Product p WHERE p.active=true AND (LOWER(p.name) LIKE LOWER(CONCAT('%',:kw,'%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%',:kw,'%')) OR LOWER(p.brand) LIKE LOWER(CONCAT('%',:kw,'%')))")
    Page<Product> searchProducts(@Param("kw") String keyword, Pageable pageable);
    @Query("SELECT p FROM Product p WHERE p.active=true AND (:cid IS NULL OR p.category.id=:cid) AND (:min IS NULL OR p.price>=:min) AND (:max IS NULL OR p.price<=:max) AND (:brand IS NULL OR LOWER(p.brand)=LOWER(:brand))")
    Page<Product> filterProducts(@Param("cid") Long cid, @Param("min") BigDecimal min, @Param("max") BigDecimal max, @Param("brand") String brand, Pageable pageable);
    @Query("SELECT p FROM Product p WHERE p.active=true AND p.stock<=:threshold")
    List<Product> findLowStockProducts(@Param("threshold") int threshold);
    long countByActiveTrue();
    long countByCategoryIdAndActiveTrue(Long categoryId);
}
