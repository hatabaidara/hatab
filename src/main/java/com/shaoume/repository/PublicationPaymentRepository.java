package com.shaoume.repository;

import com.shaoume.entity.PublicationPayment;
import com.shaoume.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface PublicationPaymentRepository extends JpaRepository<PublicationPayment, Long> {
    Optional<PublicationPayment> findByWaveCheckoutId(String checkoutId);
    List<PublicationPayment> findBySellerOrderByCreatedAtDesc(User seller);
    boolean existsBySellerAndStatus(User seller, PublicationPayment.PaymentStatus status);
}
