package com.shaoume.entity;
import com.shaoume.entity.enums.PaymentMethod;
import com.shaoume.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity @Table(name="payments") @EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,unique=true,length=100) private String transactionId;
    @Column(nullable=false,precision=10,scale=2) private BigDecimal amount;
    @Column(length=10) @Builder.Default private String currency = "XOF";
    @Enumerated(EnumType.STRING) @Column(nullable=false) @Builder.Default private PaymentStatus status = PaymentStatus.PENDING;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private PaymentMethod method;
    @Column(length=255) private String paymentGatewayRef;
    @Column(columnDefinition="TEXT") private String gatewayResponse;
    private LocalDateTime paidAt;
    @OneToOne(fetch=FetchType.LAZY) @JoinColumn(name="order_id",nullable=false) private Order order;
    @CreatedDate @Column(nullable=false,updatable=false) private LocalDateTime createdAt;
    @LastModifiedDate private LocalDateTime updatedAt;
}
