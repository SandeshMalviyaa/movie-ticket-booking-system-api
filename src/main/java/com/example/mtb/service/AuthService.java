package com.example.mtb.service;

import com.example.mtb.dto.AuthResponse;
import com.example.mtb.dto.LoginRequest;

public interface AuthService  {

    AuthResponse login(LoginRequest loginRequest);
}
