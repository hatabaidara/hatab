package com.shaoume.service;
import com.shaoume.dto.request.LoginRequest;
import com.shaoume.dto.request.RegisterRequest;
import com.shaoume.dto.response.AuthResponse;
public interface AuthService {
    AuthResponse register(RegisterRequest r);
    AuthResponse login(LoginRequest r);
    AuthResponse refreshToken(String token);
    void logout(String email);
}
