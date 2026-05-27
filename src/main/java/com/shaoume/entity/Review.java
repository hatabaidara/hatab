package com.shaoume.entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
@Entity @Table(name="reviews",uniqueConstraints=@UniqueConstraint(columnNames={"user_id","product_id"}))
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Review {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private Integer rating;
    @Column(length=200) private String title;
    @Column(columnDefinition="TEXT") private String comment;
    @Column(nullable=false) @Builder.Default private boolean approved = false;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id",nullable=false) private User user;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="product_id",nullable=false) private Product product;
    @CreatedDate @Column(nullable=false,updatable=false) private LocalDateTime createdAt;
    @LastModifiedDate private LocalDateTime updatedAt;
}
