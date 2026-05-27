package com.shaoume.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.shaoume.entity.enums.OrderStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal shippingCost;
    private String shippingAddress;
    private String shippingCity;
    private String shippingCountry;
    private String shippingPostalCode;
    private String notes;
    private LocalDateTime deliveredAt;
    private UserResponse user;
    private List<OrderItemResponse> orderItems;
    private PaymentResponse payment;
    private LocalDateTime createdAt;
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class OrderItemResponse {
        private Long id;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
        private ProductResponse product;
    }
}
