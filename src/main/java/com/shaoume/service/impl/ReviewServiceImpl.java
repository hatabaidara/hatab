package com.shaoume.service.impl;
import com.shaoume.dto.request.ReviewRequest;
import com.shaoume.dto.response.ReviewResponse;
import com.shaoume.dto.response.UserResponse;
import com.shaoume.entity.Product;
import com.shaoume.entity.Review;
import com.shaoume.entity.User;
import com.shaoume.exception.ConflictException;
import com.shaoume.exception.ResourceNotFoundException;
import com.shaoume.repository.ProductRepository;
import com.shaoume.repository.ReviewRepository;
import com.shaoume.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service @RequiredArgsConstructor
public class ReviewServiceImpl {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    @Transactional
    public ReviewResponse createReview(Long uid,Long pid,ReviewRequest r) {
        if(reviewRepository.existsByUserIdAndProductId(uid,pid)) throw new ConflictException("Avis déjà soumis");
        User u=userRepository.findById(uid).orElseThrow(()->new ResourceNotFoundException("Utilisateur",uid));
        Product p=productRepository.findById(pid).orElseThrow(()->new ResourceNotFoundException("Produit",pid));
        Review saved=reviewRepository.save(Review.builder().rating(r.getRating()).title(r.getTitle())
            .comment(r.getComment()).user(u).product(p).approved(false).build());
        updateRating(p);
        return map(saved);
    }
    @Transactional
    public ReviewResponse approveReview(Long id) {
        Review r=reviewRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Avis",id));
        r.setApproved(true);
        Review saved=reviewRepository.save(r);
        updateRating(saved.getProduct());
        return map(saved);
    }
    @Transactional(readOnly=true)
    public Page<ReviewResponse> getProductReviews(Long pid,Pageable p) { return reviewRepository.findByProductIdAndApprovedTrue(pid,p).map(this::map); }
    @Transactional(readOnly=true)
    public Page<ReviewResponse> getPendingReviews(Pageable p) { return reviewRepository.findByApprovedFalse(p).map(this::map); }
    private void updateRating(Product p) {
        Double avg=reviewRepository.calculateAverageRating(p.getId());
        long cnt=reviewRepository.countApprovedByProductId(p.getId());
        p.setAverageRating(avg!=null?avg:0.0);
        p.setReviewCount((int)cnt);
        productRepository.save(p);
    }
    private ReviewResponse map(Review r) {
        return ReviewResponse.builder().id(r.getId()).rating(r.getRating()).title(r.getTitle())
            .comment(r.getComment()).approved(r.isApproved()).productId(r.getProduct().getId())
            .user(UserResponse.builder().id(r.getUser().getId()).firstName(r.getUser().getFirstName())
                .lastName(r.getUser().getLastName()).build())
            .createdAt(r.getCreatedAt()).build();
    }
}
