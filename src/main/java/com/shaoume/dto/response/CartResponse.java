package com.shaoume.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartResponse {
    private Long id;
    private Integer quantity;
    private ProductResponse product;
    private BigDecimal subtotal;
    private LocalDateTime createdAt;
}
