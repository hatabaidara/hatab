package com.shaoume.service;

import com.shaoume.entity.Notification;
import com.shaoume.entity.User;
import com.shaoume.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void send(User user, String title, String message, String type, String actionUrl) {
        notificationRepository.save(Notification.builder()
            .user(user)
            .title(title)
            .message(message)
            .type(type)
            .actionUrl(actionUrl)
            .build());
    }

    public void notifyNewOrder(User seller, String orderNumber, String productName, int quantity) {
        send(seller,
            "Nouvelle commande recue !",
            "Votre produit " + productName + " a ete commande (x" + quantity + "). Commande : " + orderNumber,
            "ORDER",
            "/pages/seller-dashboard.html"
        );
    }

    public void notifyOrderStatus(User buyer, String orderNumber, String status) {
        send(buyer,
            "Statut de votre commande",
            "Votre commande " + orderNumber + " est maintenant : " + status,
            "ORDER_STATUS",
            "/pages/my-orders.html"
        );
    }
}
