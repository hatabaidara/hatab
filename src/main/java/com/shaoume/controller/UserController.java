package com.shaoume.controller;
import com.shaoume.dto.response.ApiResponse;
import com.shaoume.dto.response.UserResponse;
import com.shaoume.entity.User;
import com.shaoume.exception.ResourceNotFoundException;
import com.shaoume.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/users") @RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> me(@AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(ApiResponse.success(map(userRepository.findByEmail(ud.getUsername())
            .orElseThrow(()->new ResourceNotFoundException("Utilisateur introuvable")))));
    }
    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> update(@RequestBody Map<String,String> body,@AuthenticationPrincipal UserDetails ud) {
        User u=userRepository.findByEmail(ud.getUsername()).orElseThrow(()->new ResourceNotFoundException("Utilisateur introuvable"));
        if(body.containsKey("firstName")) u.setFirstName(body.get("firstName"));
        if(body.containsKey("lastName")) u.setLastName(body.get("lastName"));
        if(body.containsKey("phone")) u.setPhone(body.get("phone"));
        if(body.containsKey("address")) u.setAddress(body.get("address"));
        if(body.containsKey("city")) u.setCity(body.get("city"));
        if(body.containsKey("country")) u.setCountry(body.get("country"));
        return ResponseEntity.ok(ApiResponse.success("Profil mis à jour",map(userRepository.save(u))));
    }
    @GetMapping("/admin/all") @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<UserResponse>>> all(@PageableDefault(size=20) Pageable p) {
        return ResponseEntity.ok(ApiResponse.success(userRepository.findAll(p).map(this::map)));
    }
    @GetMapping("/admin/{id}") @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(map(userRepository.findById(id)
            .orElseThrow(()->new ResourceNotFoundException("Utilisateur",id)))));
    }
    @PutMapping("/admin/{id}/toggle-status") @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> toggle(@PathVariable Long id) {
        User u=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Utilisateur",id));
        u.setEnabled(!u.isEnabled()); userRepository.save(u);
        return ResponseEntity.ok(ApiResponse.success(u.isEnabled()?"Compte activé":"Compte désactivé",null));
    }
    @GetMapping("/admin/search") @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<UserResponse>>> search(@RequestParam String keyword,@PageableDefault(size=20) Pageable p) {
        return ResponseEntity.ok(ApiResponse.success(userRepository.searchUsers(keyword,p).map(this::map)));
    }
    private UserResponse map(User u) {
        return UserResponse.builder().id(u.getId()).firstName(u.getFirstName()).lastName(u.getLastName())
            .email(u.getEmail()).phone(u.getPhone()).address(u.getAddress()).city(u.getCity())
            .country(u.getCountry()).postalCode(u.getPostalCode()).avatarUrl(u.getAvatarUrl())
            .role(u.getRole()).enabled(u.isEnabled()).createdAt(u.getCreatedAt()).build();
    }
}
