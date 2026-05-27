package com.shaoume.service.impl;
import com.shaoume.dto.request.OrderRequest;
import com.shaoume.dto.response.*;
import com.shaoume.entity.*;
import com.shaoume.entity.enums.OrderStatus;
import com.shaoume.entity.enums.PaymentStatus;
import com.shaoume.exception.BadRequestException;
import com.shaoume.exception.ResourceNotFoundException;
import com.shaoume.repository.*;
import com.shaoume.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
@Service @RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;
    @Override @Transactional
    public OrderResponse createOrder(Long userId, OrderRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", userId));
        Order order = Order.builder()
            .orderNumber("SGB-" + System.currentTimeMillis())
            .status(OrderStatus.PENDING)
            .shippingAddress(request.getShippingAddress())
            .shippingCity(request.getShippingCity())
            .shippingCountry(request.getShippingCountry())
            .shippingPostalCode(request.getShippingPostalCode())
            .notes(request.getNotes())
            .user(user).build();
        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (OrderRequest.OrderItemRequest ir : request.getItems()) {
            Product p = productRepository.findById(ir.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Produit", ir.getProductId()));
            if (!p.isActive()) throw new BadRequestException("Produit non disponible: " + p.getName());
            if (p.getStock() < ir.getQuantity()) throw new BadRequestException("Stock insuffisant: " + p.getName());
            BigDecimal unitPrice = p.getDiscountPrice() != null ? p.getDiscountPrice() : p.getPrice();
            BigDecimal itemTotal = unitPrice.multiply(BigDecimal.valueOf(ir.getQuantity()));
            items.add(OrderItem.builder().order(order).product(p)
                .quantity(ir.getQuantity()).unitPrice(unitPrice).totalPrice(itemTotal).build());
            total = total.add(itemTotal);
            p.setStock(p.getStock() - ir.getQuantity());
            productRepository.save(p);
        }
        order.setOrderItems(items);
        order.setTotalAmount(total);
        Order saved = orderRepository.save(order);
        paymentRepository.save(Payment.builder()
            .transactionId(UUID.randomUUID().toString())
            .amount(total).currency("XOF")
            .status(PaymentStatus.PENDING)
            .method(request.getPaymentMethod())
            .order(saved).build());
        return mapToResponse(saved);
    }
    @Override @Transactional(readOnly=true)
    public OrderResponse getOrderById(Long id, Long userId) {
        return mapToResponse(orderRepository.findByIdAndUserId(id, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Commande", id)));
    }
    @Override @Transactional(readOnly=true)
    public OrderResponse getOrderByNumber(String num) {
        return mapToResponse(orderRepository.findByOrderNumber(num)
            .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable: " + num)));
    }
    @Override @Transactional(readOnly=true)
    public Page<OrderResponse> getUserOrders(Long uid, Pageable p) {
        return orderRepository.findByUserId(uid, p).map(this::mapToResponse);
    }
    @Override @Transactional(readOnly=true)
    public Page<OrderResponse> getAllOrders(Pageable p) {
        return orderRepository.findAll(p).map(this::mapToResponse);
    }
    @Override @Transactional
    public OrderResponse updateOrderStatus(Long id, OrderStatus status) {
        Order o = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Commande", id));
        o.setStatus(status);
        if (status == OrderStatus.DELIVERED) o.setDeliveredAt(LocalDateTime.now());
        return mapToResponse(orderRepository.save(o));
    }
    @Override @Transactional
    public void cancelOrder(Long id, Long userId) {
        Order o = orderRepository.findByIdAndUserId(id, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Commande", id));
        if (o.getStatus() != OrderStatus.PENDING && o.getStatus() != OrderStatus.CONFIRMED)
            throw new BadRequestException("Commande non annulable");
        o.getOrderItems().forEach(item -> {
            Product p = item.getProduct();
            p.setStock(p.getStock() + item.getQuantity());
            productRepository.save(p);
        });
        o.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(o);
    }
    private OrderResponse mapToResponse(Order o) {
        List<OrderResponse.OrderItemResponse> items = o.getOrderItems().stream()
            .map(i -> OrderResponse.OrderItemResponse.builder()
                .id(i.getId()).quantity(i.getQuantity())
                .unitPrice(i.getUnitPrice()).totalPrice(i.getTotalPrice())
                .product(ProductResponse.builder()
                    .id(i.getProduct().getId())
                    .name(i.getProduct().getName())
                    .imageUrl(i.getProduct().getImageUrl())
                    .price(i.getProduct().getPrice()).build()).build())
            .collect(Collectors.toList());
        return OrderResponse.builder()
            .id(o.getId()).orderNumber(o.getOrderNumber()).status(o.getStatus())
            .totalAmount(o.getTotalAmount()).shippingAddress(o.getShippingAddress())
            .shippingCity(o.getShippingCity()).shippingCountry(o.getShippingCountry())
            .notes(o.getNotes()).deliveredAt(o.getDeliveredAt())
            .user(UserResponse.builder()
                .id(o.getUser().getId())
                .firstName(o.getUser().getFirstName())
                .lastName(o.getUser().getLastName())
                .email(o.getUser().getEmail()).build())
            .orderItems(items).createdAt(o.getCreatedAt()).build();
    }
}
