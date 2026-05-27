package com.shaoume.entity;
import com.shaoume.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity @Table(name="users") @EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=100) private String firstName;
    @Column(nullable=false,length=100) private String lastName;
    @Column(nullable=false,unique=true,length=150) private String email;
    @Column(nullable=false) private String password;
    @Column(length=20) private String phone;
    @Column(name="shop_name") private String shopName;
    @Column(length=255) private String address;
    @Column(length=100) private String city;
    @Column(length=100) private String country;
    @Column(length=10) private String postalCode;
    @Column(length=255) private String avatarUrl;
    @Enumerated(EnumType.STRING) @Column(nullable=false) @Builder.Default private Role role = Role.USER;
    @Column(nullable=false) @Builder.Default private boolean enabled = true;
    @Column(nullable=false) @Builder.Default private boolean emailVerified = false;
    private String refreshToken;
    @CreatedDate @Column(nullable=false,updatable=false) private LocalDateTime createdAt;
    @LastModifiedDate private LocalDateTime updatedAt;
    @OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY) @Builder.Default private List<Order> orders = new ArrayList<>();
    @OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY) @Builder.Default private List<Cart> cartItems = new ArrayList<>();
    @OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY) @Builder.Default private List<Favorite> favorites = new ArrayList<>();
    @OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY) @Builder.Default private List<Review> reviews = new ArrayList<>();
    @OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY) @Builder.Default private List<Notification> notifications = new ArrayList<>();
}
