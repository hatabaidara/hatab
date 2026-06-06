package com.shaoume.entity;

import com.shaoume.entity.enums.PaymentRequestStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "merchant_payment_requests")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MerchantPaymentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal montant = new BigDecimal("5000");

    @Column(length = 50)
    private String moyenPaiement;

    @Column(length = 20)
    private String numeroDeTelephone;

    @Column(length = 100)
    private String referenceTransaction;

    @Column(length = 500)
    private String recuImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PaymentRequestStatus statut = PaymentRequestStatus.EN_ATTENTE;

    @Column(length = 500)
    private String motifRefus;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateDemande;

    private LocalDateTime dateValidation;
}
