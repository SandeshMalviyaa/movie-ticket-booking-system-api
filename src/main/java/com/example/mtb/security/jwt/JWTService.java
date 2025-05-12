package com.example.mtb.security.jwt;

import com.example.mtb.config.AppEnv;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@AllArgsConstructor
@Service
public class JWTService {

    // private String secret =  "ttQOcGb/maehubZ9hGptxifQvgonTlgo9HBUR2RACWU=";
    private final AppEnv appEnv;

    public String createJwtToken(TokenPayLoad tokenPayload) {
        return Jwts.builder()
                .setHeaderParam("type", tokenPayload.tokenType().name().toLowerCase())
                .setClaims(tokenPayload.claims())
                .setSubject(tokenPayload.subject())
                .setIssuedAt(new Date(tokenPayload.issuedAt().toEpochMilli()))
                .setExpiration(new Date(tokenPayload.expiration().toEpochMilli()))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public ExtractedToken parseToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getSignatureKey())
                    .build()
                    .parseClaimsJws(token);

            JwsHeader header = claimsJws.getHeader();

            Claims claimsBody= claimsJws.getBody();

            return new ExtractedToken(header, claimsBody);
        } catch (JwtException e) {
            return null;
        }
    }

    private Key getSignatureKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(appEnv.getToken().getSecret()));
    }
}
