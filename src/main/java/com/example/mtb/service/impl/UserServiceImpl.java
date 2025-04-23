package com.example.mtb.service.impl;

import com.example.mtb.entity.User;
import com.example.mtb.entity.UserDetails;
import com.example.mtb.exceptions.UserExistByEmailException;
import com.example.mtb.repository.UserRepository;
import com.example.mtb.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails addUser(UserDetails user) {
        if(userRepository.existsEmail(user.getEmail()))
            throw new UserExistByEmailException("User with the Email is already exists");

        return userRepository.save(user);
    }
}
