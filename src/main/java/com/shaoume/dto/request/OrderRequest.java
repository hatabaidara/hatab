package com.shaoume.dto.request;
import com.shaoume.entity.enums.PaymentMethod;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderRequest {
    @NotEmpty private List<OrderItemRequest> items;
    @NotBlank private String shippingAddress;
    @NotBlank private String shippingCity;
    @NotBlank private String shippingCountry;
    private String shippingPostalCode;
    private String notes;
    @NotNull private PaymentMethod paymentMethod;
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class OrderItemRequest {
        @NotNull private Long productId;
        @NotNull private Integer quantity;
    }
}
