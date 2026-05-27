package com.shaoume.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Integer stock;
    private String sku;
    private String brand;
    private String imageUrl;
    private List<String> images;
    private boolean active;
    private boolean featured;
    private Double averageRating;
    private Integer reviewCount;
    private CategoryResponse category;
    private LocalDateTime createdAt;
}
