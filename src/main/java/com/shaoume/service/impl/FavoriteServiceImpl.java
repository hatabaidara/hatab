package com.shaoume.service.impl;
import com.shaoume.dto.response.ProductResponse;
import com.shaoume.entity.Favorite;
import com.shaoume.entity.Product;
import com.shaoume.entity.User;
import com.shaoume.exception.ResourceNotFoundException;
import com.shaoume.repository.FavoriteRepository;
import com.shaoume.repository.ProductRepository;
import com.shaoume.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service @RequiredArgsConstructor
public class FavoriteServiceImpl {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    @Transactional
    public String toggleFavorite(Long uid,Long pid) {
        if(favoriteRepository.existsByUserIdAndProductId(uid,pid)){
            favoriteRepository.deleteByUserIdAndProductId(uid,pid);
            return "Retiré des favoris";
        }
        User u=userRepository.findById(uid).orElseThrow(()->new ResourceNotFoundException("Utilisateur",uid));
        Product p=productRepository.findById(pid).orElseThrow(()->new ResourceNotFoundException("Produit",pid));
        favoriteRepository.save(Favorite.builder().user(u).product(p).build());
        return "Ajouté aux favoris";
    }
    @Transactional(readOnly=true)
    public Page<ProductResponse> getUserFavorites(Long uid,Pageable pageable) {
        return favoriteRepository.findByUserId(uid,pageable).map(f->{
            Product p=f.getProduct();
            return ProductResponse.builder().id(p.getId()).name(p.getName()).price(p.getPrice())
                .discountPrice(p.getDiscountPrice()).imageUrl(p.getImageUrl()).averageRating(p.getAverageRating()).build();
        });
    }
    public boolean isFavorite(Long uid,Long pid) { return favoriteRepository.existsByUserIdAndProductId(uid,pid); }
}
