package com.shaoume.repository;

import com.shaoume.entity.Merchant;
import com.shaoume.entity.User;
import com.shaoume.entity.enums.MerchantStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Optional<Merchant> findByUser(User user);
    Optional<Merchant> findByEmail(String email);
    Page<Merchant> findByStatut(MerchantStatus statut, Pageable p);
    boolean existsByUser(User user);
}
