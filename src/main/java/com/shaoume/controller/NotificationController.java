package com.shaoume.controller;
import com.shaoume.dto.response.ApiResponse;
import com.shaoume.dto.response.NotificationResponse;
import com.shaoume.entity.Notification;
import com.shaoume.repository.NotificationRepository;
import com.shaoume.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/notifications") @RequiredArgsConstructor
public class NotificationController {
    private final NotificationRepository notifRepo;
    private final UserRepository userRepository;
    @GetMapping
    public ResponseEntity<ApiResponse<Page<NotificationResponse>>> get(@AuthenticationPrincipal UserDetails ud,@PageableDefault(size=20) Pageable p) {
        return ResponseEntity.ok(ApiResponse.success(notifRepo.findByUserIdOrderByCreatedAtDesc(uid(ud),p).map(this::map)));
    }
    @GetMapping("/unread-count")
    public ResponseEntity<ApiResponse<Map<String,Long>>> count(@AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("unreadCount",notifRepo.countByUserIdAndReadFalse(uid(ud)))));
    }
    @PutMapping("/{id}/read") @Transactional
    public ResponseEntity<ApiResponse<Void>> read(@PathVariable Long id,@AuthenticationPrincipal UserDetails ud) {
        notifRepo.markAsRead(id,uid(ud));
        return ResponseEntity.ok(ApiResponse.success("Lue",null));
    }
    @PutMapping("/read-all") @Transactional
    public ResponseEntity<ApiResponse<Void>> readAll(@AuthenticationPrincipal UserDetails ud) {
        notifRepo.markAllAsReadByUserId(uid(ud));
        return ResponseEntity.ok(ApiResponse.success("Toutes lues",null));
    }
    private Long uid(UserDetails ud) { return userRepository.findByEmail(ud.getUsername()).orElseThrow().getId(); }
    private NotificationResponse map(Notification n) {
        return NotificationResponse.builder().id(n.getId()).title(n.getTitle()).message(n.getMessage())
            .type(n.getType()).read(n.isRead()).actionUrl(n.getActionUrl()).createdAt(n.getCreatedAt()).build();
    }
}
