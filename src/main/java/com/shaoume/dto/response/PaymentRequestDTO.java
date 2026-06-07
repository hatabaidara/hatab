package com.shaoume.dto.response;
import com.shaoume.entity.enums.PaymentRequestStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentRequestDTO {
    private Long id;
    private Long merchantId;
    private String merchantName;
    private String merchantEmail;
    private BigDecimal montant;
    private String moyenPaiement;
    private String numeroDeTelephone;
    private String referenceTransaction;
    private String recuImageUrl;
    private PaymentRequestStatus statut;
    private String motifRefus;
    private LocalDateTime dateDemande;
    private LocalDateTime dateValidation;
}
