package com.shaoume.service;
import com.shaoume.dto.response.CartResponse;
import java.util.List;
public interface CartService {
    CartResponse addToCart(Long uid, Long pid, int qty);
    CartResponse updateCartItem(Long uid, Long cid, int qty);
    void removeFromCart(Long uid, Long cid);
    List<CartResponse> getUserCart(Long uid);
    void clearCart(Long uid);
    long countCartItems(Long uid);
}
