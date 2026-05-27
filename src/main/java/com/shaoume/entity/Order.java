package com.shaoume.entity;
import com.shaoume.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity @Table(name="orders") @EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Order {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,unique=true,length=50) private String orderNumber;
    @Enumerated(EnumType.STRING) @Column(nullable=false) @Builder.Default private OrderStatus status = OrderStatus.PENDING;
    @Column(nullable=false,precision=10,scale=2) @Builder.Default private BigDecimal totalAmount = BigDecimal.ZERO;
    @Column(precision=10,scale=2) @Builder.Default private BigDecimal discountAmount = BigDecimal.ZERO;
    @Column(precision=10,scale=2) @Builder.Default private BigDecimal shippingCost = BigDecimal.ZERO;
    @Column(length=255) private String shippingAddress;
    @Column(length=100) private String shippingCity;
    @Column(length=100) private String shippingCountry;
    @Column(length=10) private String shippingPostalCode;
    @Column(length=255) private String notes;
    private LocalDateTime deliveredAt;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id",nullable=false) private User user;
    @OneToMany(mappedBy="order",cascade=CascadeType.ALL,fetch=FetchType.LAZY) @Builder.Default private List<OrderItem> orderItems = new ArrayList<>();
    @OneToOne(mappedBy="order",cascade=CascadeType.ALL,fetch=FetchType.LAZY) private Payment payment;
    @CreatedDate @Column(nullable=false,updatable=false) private LocalDateTime createdAt;
    @LastModifiedDate private LocalDateTime updatedAt;
}
