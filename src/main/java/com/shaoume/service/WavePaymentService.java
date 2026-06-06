package com.shaoume.service;

import com.shaoume.entity.PublicationPayment;
import com.shaoume.entity.User;
import com.shaoume.repository.PublicationPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WavePaymentService {

    private final PublicationPaymentRepository paymentRepository;
    private final RestTemplate restTemplate;

    @Value("${wave.api.key:YOUR_WAVE_API_KEY}")
    private String waveApiKey;

    @Value("${wave.publication.fee:2000}")
    private BigDecimal publicationFee;

    @Value("${app.base-url:http://localhost:8081}")
    private String baseUrl;

    public Map<String, Object> initiatePayment(User seller) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(waveApiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("currency", "XOF");
        body.put("amount", publicationFee.toString());
        body.put("error_url", baseUrl + "/pages/seller-publish.html?payment=error");
        body.put("success_url", baseUrl + "/pages/seller-publish.html?payment=success");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
            "https://api.wave.com/v1/checkout/sessions",
            request, Map.class
        );

        Map<String, Object> result = response.getBody();
        String checkoutId = (String) result.get("id");
        String checkoutUrl = (String) result.get("wave_launch_url");

        PublicationPayment payment = PublicationPayment.builder()
            .seller(seller)
            .waveCheckoutId(checkoutId)
            .amount(publicationFee)
            .status(PublicationPayment.PaymentStatus.PENDING)
            .build();
        paymentRepository.save(payment);

        Map<String, Object> out = new HashMap<>();
        out.put("checkoutUrl", checkoutUrl);
        out.put("checkoutId", checkoutId);
        return out;
    }

    public boolean verifyPayment(String checkoutId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(waveApiKey);
        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
            "https://api.wave.com/v1/checkout/sessions/" + checkoutId,
            HttpMethod.GET, request, Map.class
        );

        Map<String, Object> result = response.getBody();
        String status = (String) result.get("payment_status");

        paymentRepository.findByWaveCheckoutId(checkoutId).ifPresent(p -> {
            if ("complete".equals(status)) {
                p.setStatus(PublicationPayment.PaymentStatus.COMPLETED);
                p.setPaidAt(LocalDateTime.now());
            } else if ("expired".equals(status)) {
                p.setStatus(PublicationPayment.PaymentStatus.EXPIRED);
            }
            paymentRepository.save(p);
        });

        return "complete".equals(status);
    }

    public boolean hasValidPayment(User seller) {
        return paymentRepository.existsBySellerAndStatus(
            seller, PublicationPayment.PaymentStatus.COMPLETED
        );
    }
}
