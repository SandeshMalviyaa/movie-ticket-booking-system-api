package com.example.mtb.security;

import com.example.mtb.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.example.mtb.entity.UserDetails user = userRepository.findByEmail(username);
        if (user == null) {
            log.error("Failed to find user by username: {}", username);
            throw new UsernameNotFoundException("Email Not found in the DataBase");
        }

        return User.builder().
                username(user.getEmail()).
                password(user.getPassword()).
                authorities(user.getUserRole().name()).
                build();
    }
}
