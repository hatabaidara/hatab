package com.shaoume.service.impl;
import com.shaoume.dto.request.ProductRequest;
import com.shaoume.dto.response.CategoryResponse;
import com.shaoume.dto.response.ProductResponse;
import com.shaoume.entity.Category;
import com.shaoume.entity.Product;
import com.shaoume.exception.ResourceNotFoundException;
import com.shaoume.repository.CategoryRepository;
import com.shaoume.repository.ProductRepository;
import com.shaoume.repository.UserRepository;
import com.shaoume.entity.User;
import com.shaoume.entity.PublicationPayment;
import com.shaoume.repository.PublicationPaymentRepository;
import com.shaoume.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
@Service @RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PublicationPaymentRepository publicationPaymentRepository;
    @Override @Transactional
    public ProductResponse createProduct(ProductRequest r) {
        Category cat=categoryRepository.findById(r.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Catégorie",r.getCategoryId()));
        return map(productRepository.save(Product.builder().name(r.getName()).description(r.getDescription())
            .price(r.getPrice()).discountPrice(r.getDiscountPrice()).stock(r.getStock())
            .sku(r.getSku()).brand(r.getBrand()).imageUrl(r.getImageUrl()).images(r.getImages()).featured(r.isFeatured()).category(cat).build()));
    }
    @Override @Transactional
    public ProductResponse updateProduct(Long id,ProductRequest r) {
        Product p=productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Produit",id));
        Category cat=categoryRepository.findById(r.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Catégorie",r.getCategoryId()));
        p.setName(r.getName()); p.setDescription(r.getDescription()); p.setPrice(r.getPrice());
        p.setDiscountPrice(r.getDiscountPrice()); p.setStock(r.getStock()); p.setSku(r.getSku());
        p.setBrand(r.getBrand()); p.setImageUrl(r.getImageUrl()); p.setImages(r.getImages());
        p.setFeatured(r.isFeatured()); p.setCategory(cat);
        return map(productRepository.save(p));
    }
    @Override @Transactional
    public void deleteProduct(Long id) {
        Product p=productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Produit",id));
        p.setActive(false); productRepository.save(p);
    }
    @Override @Transactional(readOnly=true)
    public ProductResponse getProductById(Long id) {
        return map(productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Produit",id)));
    }
    @Override @Transactional(readOnly=true)
    public Page<ProductResponse> getAllProducts(Pageable p) { return productRepository.findByActiveTrue(p).map(this::map); }
    @Override @Transactional(readOnly=true)
    public Page<ProductResponse> getProductsByCategory(Long cid,Pageable p) { return productRepository.findByCategoryIdAndActiveTrue(cid,p).map(this::map); }
    @Override @Transactional(readOnly=true)
    public Page<ProductResponse> getFeaturedProducts(Pageable p) { return productRepository.findByFeaturedTrueAndActiveTrue(p).map(this::map); }
    @Override @Transactional(readOnly=true)
    public Page<ProductResponse> searchProducts(String kw,Pageable p) { return productRepository.searchProducts(kw,p).map(this::map); }
    @Override @Transactional(readOnly=true)
    public Page<ProductResponse> filterProducts(Long cid,BigDecimal min,BigDecimal max,String brand,Pageable p) { return productRepository.filterProducts(cid,min,max,brand,p).map(this::map); }
    @Override @Transactional(readOnly=true)
    public Page<ProductResponse> getSellerProducts(Long sellerId, Pageable p) {
        return productRepository.findBySellerIdAndActiveTrue(sellerId, p).map(this::map);
    }

    @Override @Transactional
    public ProductResponse createProductAsSeller(ProductRequest r, String sellerEmail) {
        User seller = userRepository.findByEmail(sellerEmail)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", 0L));
        boolean hasPaid = publicationPaymentRepository.existsBySellerAndStatus(
            seller, PublicationPayment.PaymentStatus.COMPLETED);
        if (!hasPaid) {
            throw new RuntimeException("Paiement requis pour publier un produit");
        }
        Category cat = categoryRepository.findById(r.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Categorie", r.getCategoryId()));
        Product product = Product.builder()
            .name(r.getName()).description(r.getDescription())
            .price(r.getPrice()).discountPrice(r.getDiscountPrice())
            .stock(r.getStock()).brand(r.getBrand())
            .imageUrl(r.getImageUrl()).images(r.getImages())
            .category(cat).seller(seller).publishedPaid(true)
            .active(true).build();
        return map(productRepository.save(product));
    }

    private ProductResponse map(Product p) {
        return ProductResponse.builder().id(p.getId()).name(p.getName()).description(p.getDescription())
            .price(p.getPrice()).discountPrice(p.getDiscountPrice()).stock(p.getStock()).sku(p.getSku())
            .brand(p.getBrand()).imageUrl(p.getImageUrl()).images(p.getImages()).active(p.isActive())
            .featured(p.isFeatured()).averageRating(p.getAverageRating()).reviewCount(p.getReviewCount())
            .category(CategoryResponse.builder().id(p.getCategory().getId()).name(p.getCategory().getName()).build())
            .createdAt(p.getCreatedAt()).build();
    }
}
