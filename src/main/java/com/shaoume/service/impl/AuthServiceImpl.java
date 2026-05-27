package com.shaoume.service.impl;
import com.shaoume.dto.request.LoginRequest;
import com.shaoume.dto.request.RegisterRequest;
import com.shaoume.dto.response.AuthResponse;
import com.shaoume.dto.response.UserResponse;
import com.shaoume.entity.User;
import com.shaoume.entity.enums.Role;
import com.shaoume.exception.BadRequestException;
import com.shaoume.exception.ConflictException;
import com.shaoume.exception.ResourceNotFoundException;
import com.shaoume.repository.UserRepository;
import com.shaoume.security.jwt.JwtUtils;
import com.shaoume.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
@Service @RequiredArgsConstructor @Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    @Value("${app.jwt.expiration}") private long jwtExpiration;
    @Override @Transactional
    public AuthResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail()))
            throw new ConflictException("Email déjà utilisé : "+request.getEmail());
        User user=userRepository.save(User.builder()
            .firstName(request.getFirstName()).lastName(request.getLastName())
            .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
            .phone(request.getPhone()).shopName(request.getShopName()).role(request.getRole() != null && request.getRole().equals("SELLER") ? Role.SELLER : Role.USER).enabled(true).build());
        return buildAuth(user);
    }
    @Override @Transactional
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        User user=userRepository.findByEmail(request.getEmail()).orElseThrow(()->new ResourceNotFoundException("Utilisateur introuvable"));
        if(!user.isEnabled()) throw new BadRequestException("Compte désactivé");
        return buildAuth(user);
    }
    @Override @Transactional
    public AuthResponse refreshToken(String refreshToken) {
        User user=userRepository.findByRefreshToken(refreshToken).orElseThrow(()->new BadRequestException("Refresh token invalide"));
        if(jwtUtils.isTokenExpired(refreshToken)) throw new BadRequestException("Refresh token expiré");
        return buildAuth(user);
    }
    @Override @Transactional
    public void logout(String email) {
        userRepository.findByEmail(email).ifPresent(u->{u.setRefreshToken(null);userRepository.save(u);});
    }
    private AuthResponse buildAuth(User user) {
        UserDetails ud=userDetailsService.loadUserByUsername(user.getEmail());
        Map<String,Object> claims=new HashMap<>();
        claims.put("role",user.getRole().name());
        claims.put("userId",user.getId());
        String access=jwtUtils.generateToken(ud,claims);
        String refresh=jwtUtils.generateRefreshToken(ud);
        user.setRefreshToken(refresh);
        userRepository.save(user);
        return AuthResponse.builder()
            .accessToken(access).refreshToken(refresh).tokenType("Bearer").expiresIn(jwtExpiration)
            .user(UserResponse.builder().id(user.getId()).firstName(user.getFirstName()).lastName(user.getLastName())
                .email(user.getEmail()).phone(user.getPhone()).role(user.getRole()).enabled(user.isEnabled()).createdAt(user.getCreatedAt()).build())
            .build();
    }
}
