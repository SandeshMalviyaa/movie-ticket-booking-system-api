package com.example.mtb.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        String userId,
        String userName,
        String email,
        String role,
        Long accessExpiration,
        Long refreshExpiration,
        String accessToken,
        String refreshToken
) {
}
