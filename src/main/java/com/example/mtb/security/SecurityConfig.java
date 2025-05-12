package com.example.mtb.security;

import com.example.mtb.enums.TokenType;
import com.example.mtb.security.filters.AuthFilter;
import com.example.mtb.security.jwt.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JWTService jwtService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(2)
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.securityMatcher("/**");
        //Specify public % private routes
        http.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/register", "/login").
                permitAll().
                anyRequest().
                authenticated());
        setDefault(new AuthFilter(jwtService, TokenType.ACCESS), http);

//          type of authentication to be done
//        http.formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    @Order(1)
    SecurityFilterChain refreshFilterChain(HttpSecurity http) throws Exception {

        http.securityMatcher("/refresh/**");

        //Specify public % private routes
        http.authorizeHttpRequests(auth -> auth.
                anyRequest().
                authenticated());
        setDefault(new AuthFilter(jwtService, TokenType.REFRESH), http);

        return http.build();
    }

    private HttpSecurity setDefault(AuthFilter authFilter, HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        return http;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
