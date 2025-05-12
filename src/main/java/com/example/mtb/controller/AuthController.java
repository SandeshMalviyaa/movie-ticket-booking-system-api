package com.example.mtb.controller;

import com.example.mtb.dto.AuthResponse;
import com.example.mtb.dto.LoginRequest;
import com.example.mtb.security.jwt.AunthenticatedTokenDetails;
import com.example.mtb.service.AuthService;
import com.example.mtb.util.ResponseStructure;
import com.example.mtb.util.RestResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private AuthService authService;
    private final RestResponseBuilder responseBuilder;

    @PostMapping("/login")
    ResponseEntity<ResponseStructure<AuthResponse>> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        return responseBuilder.sucess(HttpStatus.OK, "Login successfully", authResponse);
    }

    @PostMapping("/refresh")
    ResponseEntity<ResponseStructure<AuthResponse>> refresh(HttpServletRequest request) {
        AunthenticatedTokenDetails tokenDetails = (AunthenticatedTokenDetails) request.getAttribute("tokenDetails");
        AuthResponse authResponse = authService.refresh(tokenDetails);
        return responseBuilder.sucess(HttpStatus.OK, "Token refreshed successfully", authResponse);
    }
}
