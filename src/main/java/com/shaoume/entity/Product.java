package com.shaoume.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 2000)
    private String description;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Integer stock;

    private String imageUrl;

    private String brand;

    private String sku;

    @Builder.Default private boolean active = true;

    @Builder.Default private boolean featured = false;

    private double averageRating = 0.0;

    private int reviewCount = 0;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @Builder.Default
    private boolean publishedPaid = false;
}