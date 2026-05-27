package com.shaoume.repository;
import com.shaoume.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    Page<Review> findByProductIdAndApprovedTrue(Long productId, Pageable pageable);
    Optional<Review> findByUserIdAndProductId(Long userId, Long productId);
    boolean existsByUserIdAndProductId(Long userId, Long productId);
    Page<Review> findByApprovedFalse(Pageable pageable);
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.id=:pid AND r.approved=true")
    Double calculateAverageRating(@Param("pid") Long productId);
    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.id=:pid AND r.approved=true")
    long countApprovedByProductId(@Param("pid") Long productId);
}
