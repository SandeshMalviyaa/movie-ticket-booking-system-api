package com.example.mtb.service.impl;

import com.example.mtb.dto.AuthResponse;
import com.example.mtb.dto.LoginRequest;
import com.example.mtb.entity.UserDetails;
import com.example.mtb.exceptions.UsernameNotFoundException;
import com.example.mtb.mapper.AuthMapper;
import com.example.mtb.repository.UserRepository;
import com.example.mtb.security.SecurityConfig;
import com.example.mtb.security.jwt.JWTService;
import com.example.mtb.security.jwt.TokenPayLoad;
import com.example.mtb.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SecurityConfig securityConfig;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final AuthMapper authMapper;
    private final UserRepository userRepository;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());

        Authentication authentication = authenticationManager.authenticate(token);

        if (!authentication.isAuthenticated())
            throw new UsernameNotFoundException("Invalid Login Details");

        com.example.mtb.entity.UserDetails userDetails = userRepository.findByEmail(authentication.getName());

        TokenPayLoad access = tokenMapper(userDetails, 5);
        TokenPayLoad refresh = tokenMapper(userDetails, 24 * 60);

        String accessToken = jwtService.createJwtToken(access);
        String refreshToken = jwtService.createJwtToken(refresh);

        return authMapper.authResponseMapper(userDetails, access, refresh, accessToken, refreshToken);
    }

    private TokenPayLoad tokenMapper(UserDetails userDetails, int minutesForExpiration) {
        Map<String, Object> claims = new HashMap<>();

        String role = userDetails.getUserRole().toString();
        claims.put("role", role);

        return new TokenPayLoad(
                claims,
                userDetails.getEmail(),
                Instant.now(),
                Instant.now().plusSeconds(minutesForExpiration * 60));
    }
}
