package com.shaoume.repository;
import com.shaoume.entity.Order;
import com.shaoume.entity.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Optional;
@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Page<Order> findByUserId(Long userId, Pageable pageable);
    Optional<Order> findByOrderNumber(String orderNumber);
    Optional<Order> findByIdAndUserId(Long id, Long userId);
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    long countByStatus(OrderStatus status);
    @Query("SELECT COALESCE(SUM(o.totalAmount),0) FROM Order o WHERE o.status = :status")
    BigDecimal sumByStatus(@Param("status") OrderStatus status);
    default BigDecimal calculateTotalRevenue() { return sumByStatus(OrderStatus.DELIVERED); }
}
