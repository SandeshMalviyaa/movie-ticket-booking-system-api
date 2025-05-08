package com.example.mtb.security.jwt;

import java.time.Instant;
import java.util.Map;

public record TokenPayLoad(
        Map<String, Object> claims,
        String subject,
        Instant issuedAt,
        Instant expiration
) {
}
