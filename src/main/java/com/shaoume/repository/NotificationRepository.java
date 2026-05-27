package com.shaoume.repository;
import com.shaoume.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    Page<Notification> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    Page<Notification> findByUserIdAndReadFalse(Long userId, Pageable pageable);
    long countByUserIdAndReadFalse(Long userId);
    @Modifying @Query("UPDATE Notification n SET n.read=true WHERE n.user.id=:uid")
    void markAllAsReadByUserId(@Param("uid") Long userId);
    @Modifying @Query("UPDATE Notification n SET n.read=true WHERE n.id=:id AND n.user.id=:uid")
    void markAsRead(@Param("id") Long id, @Param("uid") Long userId);
}
