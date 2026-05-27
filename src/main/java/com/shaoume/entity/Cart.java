package com.shaoume.entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
@Entity @Table(name="cart") @EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cart {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private Integer quantity;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id",nullable=false) private User user;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="product_id",nullable=false) private Product product;
    @CreatedDate @Column(nullable=false,updatable=false) private LocalDateTime createdAt;
    @LastModifiedDate private LocalDateTime updatedAt;
}
