package com.shaoume.dto.request;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductRequest {
    @NotBlank @Size(max=255) private String name;
    private String description;
    @NotNull @DecimalMin(value="0.0",inclusive=false) private BigDecimal price;
    @DecimalMin("0.0") private BigDecimal discountPrice;
    @NotNull @Min(0) private Integer stock;
    private String sku;
    private String brand;
    private String imageUrl;
    private List<String> images;
    private boolean featured;
    @NotNull private Long categoryId;
}
