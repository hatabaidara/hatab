package com.shaoume.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "publication_payments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PublicationPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    private String waveCheckoutId;
    private String wavePaymentRef;
    private BigDecimal amount;
    private String currency = "XOF";

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime paidAt;

    public enum PaymentStatus { PENDING, COMPLETED, FAILED, EXPIRED }
}
