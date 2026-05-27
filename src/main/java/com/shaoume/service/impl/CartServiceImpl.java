package com.shaoume.service.impl;
import com.shaoume.dto.response.CartResponse;
import com.shaoume.dto.response.ProductResponse;
import com.shaoume.entity.Cart;
import com.shaoume.entity.Product;
import com.shaoume.entity.User;
import com.shaoume.exception.BadRequestException;
import com.shaoume.exception.ResourceNotFoundException;
import com.shaoume.repository.CartRepository;
import com.shaoume.repository.ProductRepository;
import com.shaoume.repository.UserRepository;
import com.shaoume.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
@Service @RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    @Override @Transactional
    public CartResponse addToCart(Long uid,Long pid,int qty) {
        User user=userRepository.findById(uid).orElseThrow(()->new ResourceNotFoundException("Utilisateur",uid));
        Product p=productRepository.findById(pid).orElseThrow(()->new ResourceNotFoundException("Produit",pid));
        if(!p.isActive()) throw new BadRequestException("Produit non disponible");
        if(p.getStock()<qty) throw new BadRequestException("Stock insuffisant: "+p.getStock());
        Cart cart=cartRepository.findByUserIdAndProductId(uid,pid)
            .map(c->{c.setQuantity(c.getQuantity()+qty);return c;})
            .orElseGet(()->Cart.builder().user(user).product(p).quantity(qty).build());
        return mapToResponse(cartRepository.save(cart));
    }
    @Override @Transactional
    public CartResponse updateCartItem(Long uid,Long cid,int qty) {
        Cart cart=cartRepository.findById(cid).filter(c->c.getUser().getId().equals(uid)).orElseThrow(()->new ResourceNotFoundException("Article panier",cid));
        if(qty<=0){cartRepository.delete(cart);return null;}
        if(cart.getProduct().getStock()<qty) throw new BadRequestException("Stock insuffisant");
        cart.setQuantity(qty);
        return mapToResponse(cartRepository.save(cart));
    }
    @Override @Transactional
    public void removeFromCart(Long uid,Long cid) {
        Cart c=cartRepository.findById(cid).filter(x->x.getUser().getId().equals(uid)).orElseThrow(()->new ResourceNotFoundException("Article panier",cid));
        cartRepository.delete(c);
    }
    @Override @Transactional(readOnly=true)
    public List<CartResponse> getUserCart(Long uid) { return cartRepository.findByUserId(uid).stream().map(this::mapToResponse).collect(Collectors.toList()); }
    @Override @Transactional
    public void clearCart(Long uid) { cartRepository.deleteByUserId(uid); }
    @Override
    public long countCartItems(Long uid) { return cartRepository.countByUserId(uid); }
    private CartResponse mapToResponse(Cart cart) {
        Product p=cart.getProduct();
        BigDecimal price=p.getDiscountPrice()!=null?p.getDiscountPrice():p.getPrice();
        return CartResponse.builder().id(cart.getId()).quantity(cart.getQuantity())
            .subtotal(price.multiply(BigDecimal.valueOf(cart.getQuantity())))
            .product(ProductResponse.builder().id(p.getId()).name(p.getName()).price(p.getPrice())
                .discountPrice(p.getDiscountPrice()).imageUrl(p.getImageUrl()).stock(p.getStock()).build())
            .createdAt(cart.getCreatedAt()).build();
    }
}
