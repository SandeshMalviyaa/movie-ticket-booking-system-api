package com.example.mtb.security.filters;

import com.example.mtb.enums.TokenType;
import com.example.mtb.security.jwt.AunthenticatedTokenDetails;
import com.example.mtb.security.jwt.ExtractedToken;
import com.example.mtb.security.jwt.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final TokenType tokenType;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isValid(header)) {
            if (header.contains("Bearer ")) {

                String token = header.substring(7);
                var extractedToken = jwtService.parseToken(token);
                Claims claims = Optional.ofNullable(extractedToken)
                        .map(ExtractedToken::claims)
                        .orElse(null);

                JwsHeader headers = Optional.ofNullable(extractedToken)
                        .map(ExtractedToken::headers)
                        .orElse(null);

                boolean correctType = Optional.ofNullable(headers)
                        .map(h -> (String) h.get("type"))
                        .map(String::toUpperCase)
                        .map(TokenType::valueOf)
                        .map(t -> {
                            var result = t.equals(tokenType);
                            log.debug("The token is of type: {}, validation result: {}", t.name(), result);
                            return result;
                        })
                        .orElse(false);


                if (!correctType || claims == null) filterChain.doFilter(request, response);

                String role = (String) claims.get("role", String.class);
                String email = claims.getSubject();
                log.debug("Token found, extracted claims, email/username; {}, role: {}", email, role);


                if (isValid(email) && isValid(role) && SecurityContextHolder.getContext().getAuthentication() == null) {

                    var authorities = List.of(new SimpleGrantedAuthority(role));
                    var authToken = new UsernamePasswordAuthenticationToken(email,
                            null,
                            List.of(new SimpleGrantedAuthority(role)));
                    authToken.setDetails(request);

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    AunthenticatedTokenDetails tokenDetails = new AunthenticatedTokenDetails(
                            email,
                            role,
                            claims.getExpiration().toInstant(),
                            token);

                    request.setAttribute("tokenDetails", tokenDetails);
                }
            }

        }
        filterChain.doFilter(request, response);

    }

    private static boolean isValid(String s) {
        return s != null && !s.isBlank();
    }
}
