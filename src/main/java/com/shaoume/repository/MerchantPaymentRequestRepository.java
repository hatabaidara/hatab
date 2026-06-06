package com.shaoume.repository;

import com.shaoume.entity.Merchant;
import com.shaoume.entity.MerchantPaymentRequest;
import com.shaoume.entity.enums.PaymentRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MerchantPaymentRequestRepository extends JpaRepository<MerchantPaymentRequest, Long> {
    Page<MerchantPaymentRequest> findByStatut(PaymentRequestStatus statut, Pageable p);
    Page<MerchantPaymentRequest> findAllByOrderByDateDemandeDesc(Pageable p);
    Optional<MerchantPaymentRequest> findTopByMerchantOrderByDateDemandeDesc(Merchant merchant);
    List<MerchantPaymentRequest> findByMerchantOrderByDateDemandeDesc(Merchant merchant);
}
