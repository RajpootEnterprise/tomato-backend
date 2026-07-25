package com.tomato.backend.service;

import com.tomato.backend.dto.AuthResponse;
import com.tomato.backend.dto.LoginRequest;
import com.tomato.backend.dto.SignupRequest;

public interface AuthService {
    AuthResponse signup(SignupRequest request);
    AuthResponse login(LoginRequest request);
}
