package com.shaoume.service;

import com.shaoume.entity.*;
import com.shaoume.dto.response.PaymentRequestDTO;

import com.shaoume.entity.enums.MerchantStatus;
import com.shaoume.entity.enums.PaymentRequestStatus;
import com.shaoume.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import com.shaoume.service.NotificationService;
@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;
    private final MerchantPaymentRequestRepository paymentRequestRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Transactional
    public Merchant createMerchant(User user) {
        if (merchantRepository.existsByUser(user)) {
            return merchantRepository.findByUser(user).orElseThrow();
        }
        return merchantRepository.save(Merchant.builder()
            .user(user)
            .nom(user.getFirstName() + " " + user.getLastName())
            .email(user.getEmail())
            .telephone(user.getPhone())
            .shopName(user.getShopName())
            .statut(MerchantStatus.EN_ATTENTE)
            .build());
    }

    @Transactional
    public MerchantPaymentRequest submitPaymentRequest(
            Merchant merchant, String moyenPaiement,
            String numeroDeTelephone, String referenceTransaction,
            String recuImageUrl) {
        return paymentRequestRepository.save(MerchantPaymentRequest.builder()
            .merchant(merchant)
            .moyenPaiement(moyenPaiement)
            .numeroDeTelephone(numeroDeTelephone)
            .referenceTransaction(referenceTransaction)
            .recuImageUrl(recuImageUrl)
            .statut(PaymentRequestStatus.EN_ATTENTE)
            .build());
    }

    @Transactional
    public void validatePayment(Long requestId) {
        MerchantPaymentRequest req = paymentRequestRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Demande introuvable"));
        req.setStatut(PaymentRequestStatus.VALIDE);
        req.setDateValidation(LocalDateTime.now());
        paymentRequestRepository.save(req);
        Merchant merchant = req.getMerchant();
        merchant.setStatut(MerchantStatus.ACTIF);
        merchantRepository.save(merchant);
        User user = merchant.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        notificationService.send(user, "Compte active !", "Felicitations, votre compte marchand est maintenant actif. Vous pouvez publier vos produits.", "MERCHANT_VALIDATED", "seller-dashboard.html");
    }

    @Transactional
    public void refusePayment(Long requestId, String motif) {
        MerchantPaymentRequest req = paymentRequestRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Demande introuvable"));
        req.setStatut(PaymentRequestStatus.REFUSE);
        req.setMotifRefus(motif);
        req.setDateValidation(LocalDateTime.now());
        paymentRequestRepository.save(req);
        notificationService.send(req.getMerchant().getUser(), "Demande refusee", "Votre demande d'activation a ete refusee. Motif: " + motif, "MERCHANT_REFUSED", "merchant-activation.html");
    }

    public Merchant getMerchantByUser(User user) {
        return merchantRepository.findByUser(user).orElse(null);
    }

    public Page<MerchantPaymentRequest> getAllRequests(Pageable p) {
        return paymentRequestRepository.findAllByOrderByDateDemandeDesc(p);
    }

    public Page<MerchantPaymentRequest> getPendingRequests(Pageable p) {
        return paymentRequestRepository.findByStatut(PaymentRequestStatus.EN_ATTENTE, p);
    }

    public boolean isMerchantActive(User user) {
        Merchant m = merchantRepository.findByUser(user).orElse(null);
        return m != null && m.getStatut() == MerchantStatus.ACTIF;
    }

    public MerchantStatus getMerchantStatus(User user) {
        Merchant m = merchantRepository.findByUser(user).orElse(null);
        return m != null ? m.getStatut() : null;
    }

    public List<MerchantPaymentRequest> getMerchantRequests(Merchant merchant) {
        return paymentRequestRepository.findByMerchantOrderByDateDemandeDesc(merchant);
    }
    public Page<PaymentRequestDTO> getAllRequestsDTO(Pageable p) {
        return paymentRequestRepository.findAllByOrderByDateDemandeDesc(p).map(this::toDTO);
    }

    public Page<PaymentRequestDTO> getPendingRequestsDTO(Pageable p) {
        return paymentRequestRepository.findByStatut(PaymentRequestStatus.EN_ATTENTE, p).map(this::toDTO);
    }

    private PaymentRequestDTO toDTO(MerchantPaymentRequest r) {
        return PaymentRequestDTO.builder()
            .id(r.getId())
            .merchantId(r.getMerchant().getId())
            .merchantName(r.getMerchant().getNom())
            .merchantEmail(r.getMerchant().getEmail())
            .montant(r.getMontant())
            .moyenPaiement(r.getMoyenPaiement())
            .numeroDeTelephone(r.getNumeroDeTelephone())
            .referenceTransaction(r.getReferenceTransaction())
            .recuImageUrl(r.getRecuImageUrl())
            .statut(r.getStatut())
            .motifRefus(r.getMotifRefus())
            .dateDemande(r.getDateDemande())
            .dateValidation(r.getDateValidation())
            .build();
    }
}
