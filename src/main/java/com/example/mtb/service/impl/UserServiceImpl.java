package com.example.mtb.service.impl;

import com.example.mtb.dto.UserRegistrationRequest;
import com.example.mtb.dto.UserResponse;
import com.example.mtb.dto.UserUpdationRequest;
import com.example.mtb.entity.TheaterOwner;
import com.example.mtb.entity.User;
import com.example.mtb.entity.UserDetails;
import com.example.mtb.exceptions.UserExistByEmailException;
import com.example.mtb.exceptions.UserNotFoundByEmailException;
import com.example.mtb.mapper.UserDetailsMapper;
import com.example.mtb.repository.UserRepository;
import com.example.mtb.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailsMapper userMapper;

    @Override
    public UserResponse addUser(UserRegistrationRequest user) {
        if(userRepository.existsByEmail(user.email()))
            throw new UserExistByEmailException("User with the Email is already exists");

        UserDetails userDetails = switch (user.userRole()){
            case USER ->copy(new User(),user);
            case THEATER_OWNER ->copy(new TheaterOwner(),user);
        };
        System.out.println(user);
        return userMapper.userDetailsResponseMapper(userDetails);

    }

    @Override
    public UserResponse editUser(UserUpdationRequest userRequest, String email) {
        if(userRepository.existsByEmail(email)) {
            UserDetails user = userRepository.findByEmail(email);
            if (!user.getEmail().equals(userRequest.email()) && userRepository.existsByEmail(userRequest.email()))
                throw new UserExistByEmailException("User with the email already exists");
            user = copy(user, userRequest);
            return userMapper.userDetailsResponseMapper(user);
        }
        throw new UserNotFoundByEmailException("Email not found in the Database");
    }

    @Override
    public UserResponse softDeleteUser(String email) {
        if (userRepository.existsByEmail(email)) {
            UserDetails user = userRepository.findByEmail(email);
            user.setDeleted(true);
            user.setDeletedAt(Instant.now());
            userRepository.save(user);
            return userMapper.userDetailsResponseMapper(user);
        }
        throw new UserNotFoundByEmailException("Email not found in the Database");
    }
    private UserDetails copy(UserDetails userRole, UserRegistrationRequest user )
    {
        userRole.setUserRole(user.userRole());
        userRole.setEmail(user.email());
        userRole.setPassword(user.password());
        userRole.setDateOfBirth(user.dateOfBirth());
        userRole.setPhoneNumber(user.phoneNumber());
        userRole.setUserName(user.username());
        userRepository.save(userRole);
        return userRole;
    }
    private UserDetails copy(UserDetails userRole, UserUpdationRequest user) {
        userRole.setDateOfBirth(user.dateOfBirth());
        userRole.setPhoneNumber(user.phoneNumber());
        userRole.setEmail(user.email());
        userRole.setUserName(user.username());
        userRepository.save(userRole);
        return userRole;
    }
}
