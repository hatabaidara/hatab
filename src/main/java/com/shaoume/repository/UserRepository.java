package com.shaoume.repository;
import com.shaoume.entity.User;
import com.shaoume.entity.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByRefreshToken(String token);
    Page<User> findByRole(Role role, Pageable pageable);
    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%',:kw,'%')) OR LOWER(u.lastName) LIKE LOWER(CONCAT('%',:kw,'%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%',:kw,'%'))")
    Page<User> searchUsers(@Param("kw") String keyword, Pageable pageable);
    long countByRole(Role role);
}
