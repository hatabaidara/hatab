package com.shaoume.repository;
import com.shaoume.entity.Payment;
import com.shaoume.entity.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByTransactionId(String transactionId);
    Optional<Payment> findByOrderId(Long orderId);
    Page<Payment> findByStatus(PaymentStatus status, Pageable pageable);
}
