package com.shaoume.service;
import com.shaoume.dto.request.OrderRequest;
import com.shaoume.dto.response.OrderResponse;
import com.shaoume.entity.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface OrderService {
    OrderResponse createOrder(Long userId, OrderRequest r);
    OrderResponse getOrderById(Long id, Long userId);
    OrderResponse getOrderByNumber(String num);
    Page<OrderResponse> getUserOrders(Long userId, Pageable p);
    Page<OrderResponse> getAllOrders(Pageable p);
    OrderResponse updateOrderStatus(Long id, OrderStatus s);
    void cancelOrder(Long id, Long userId);
}
