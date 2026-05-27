package com.shaoume.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.shaoume.entity.enums.PaymentMethod;
import com.shaoume.entity.enums.PaymentStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentResponse {
    private Long id;
    private String transactionId;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private PaymentMethod method;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;
}
