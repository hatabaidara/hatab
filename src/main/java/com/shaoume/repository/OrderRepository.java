package com.shaoume.repository;
import com.shaoume.entity.Order;
import com.shaoume.entity.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Optional;
@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Page<Order> findByUserId(Long userId, Pageable pageable);
    Optional<Order> findByOrderNumber(String orderNumber);
    Optional<Order> findByIdAndUserId(Long id, Long userId);
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    @Query("SELECT COALESCE(SUM(o.totalAmount),0) FROM Order o WHERE o.status='DELIVERED'")
    BigDecimal calculateTotalRevenue();
    long countByStatus(OrderStatus status);
}
