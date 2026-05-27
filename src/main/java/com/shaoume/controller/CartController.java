package com.shaoume.controller;
import com.shaoume.dto.response.ApiResponse;
import com.shaoume.dto.response.CartResponse;
import com.shaoume.repository.UserRepository;
import com.shaoume.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController @RequestMapping("/cart") @RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserRepository userRepository;
    @GetMapping
    public ResponseEntity<ApiResponse<List<CartResponse>>> get(@AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(ApiResponse.success(cartService.getUserCart(uid(ud))));
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CartResponse>> add(@RequestParam Long productId,@RequestParam(defaultValue="1") int quantity,@AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(ApiResponse.success("Ajouté au panier",cartService.addToCart(uid(ud),productId,quantity)));
    }
    @PutMapping("/{cid}")
    public ResponseEntity<ApiResponse<CartResponse>> update(@PathVariable Long cid,@RequestParam int quantity,@AuthenticationPrincipal UserDetails ud) {
        CartResponse r=cartService.updateCartItem(uid(ud),cid,quantity);
        return ResponseEntity.ok(r==null?ApiResponse.success("Supprimé",null):ApiResponse.success("Mis à jour",r));
    }
    @DeleteMapping("/{cid}")
    public ResponseEntity<ApiResponse<Void>> remove(@PathVariable Long cid,@AuthenticationPrincipal UserDetails ud) {
        cartService.removeFromCart(uid(ud),cid);
        return ResponseEntity.ok(ApiResponse.success("Retiré",null));
    }
    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<Void>> clear(@AuthenticationPrincipal UserDetails ud) {
        cartService.clearCart(uid(ud));
        return ResponseEntity.ok(ApiResponse.success("Panier vidé",null));
    }
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Map<String,Long>>> count(@AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("count",cartService.countCartItems(uid(ud)))));
    }
    private Long uid(UserDetails ud) { return userRepository.findByEmail(ud.getUsername()).orElseThrow().getId(); }
}
