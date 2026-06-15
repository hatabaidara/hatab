package com.shaoume.controller;
import com.shaoume.dto.request.OrderRequest;
import com.shaoume.dto.response.ApiResponse;
import com.shaoume.dto.response.OrderResponse;
import com.shaoume.entity.enums.OrderStatus;
import com.shaoume.repository.UserRepository;
import com.shaoume.repository.OrderRepository;
import com.shaoume.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
@RestController @RequestMapping("/orders") @RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    @GetMapping
    public ResponseEntity<ApiResponse<Page<OrderResponse>>> getOrders(@AuthenticationPrincipal UserDetails ud,@PageableDefault(size=10) Pageable p) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getUserOrders(uid(ud),p)));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> create(@Valid @RequestBody OrderRequest r,@AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created("Commande créée",orderService.createOrder(uid(ud),r)));
    }
    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponse<Page<OrderResponse>>> myOrders(@AuthenticationPrincipal UserDetails ud,@PageableDefault(size=10) Pageable p) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getUserOrders(uid(ud),p)));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOne(@PathVariable Long id,@AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrderById(id,uid(ud))));
    }
    @GetMapping("/number/{num}")
    public ResponseEntity<ApiResponse<OrderResponse>> getByNum(@PathVariable String num) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrderByNumber(num)));
    }
    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancel(@PathVariable Long id,@AuthenticationPrincipal UserDetails ud) {
        orderService.cancelOrder(id,uid(ud));
        return ResponseEntity.ok(ApiResponse.success("Commande annulée",null));
    }
    @GetMapping("/admin/all") @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<OrderResponse>>> adminAll(@PageableDefault(size=20) Pageable p) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getAllOrders(p)));
    }
    @PutMapping("/admin/{id}/status") @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<OrderResponse>> updateStatus(@PathVariable Long id,@RequestParam OrderStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Statut mis à jour",orderService.updateOrderStatus(id,status)));
    }
    @GetMapping("/admin/stats") @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminStats() {
        Map<String,Object> stats = new HashMap<>();
        stats.put("pending",    orderRepository.countByStatus(OrderStatus.PENDING));
        stats.put("processing", orderRepository.countByStatus(OrderStatus.PROCESSING));
        stats.put("confirmed",  orderRepository.countByStatus(OrderStatus.CONFIRMED));
        stats.put("delivered",  orderRepository.countByStatus(OrderStatus.DELIVERED));
        stats.put("cancelled",  orderRepository.countByStatus(OrderStatus.CANCELLED));
        stats.put("total",      orderRepository.count());
        BigDecimal revenue = orderRepository.calculateTotalRevenue();
        stats.put("revenue", revenue != null ? revenue : BigDecimal.ZERO);
        return ResponseEntity.ok(stats);
    }
    private Long uid(UserDetails ud) { return userRepository.findByEmail(ud.getUsername()).orElseThrow().getId(); }
}
