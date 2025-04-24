package com.example.mtb.service;

import com.example.mtb.dto.UserRegistrationRequest;
import com.example.mtb.entity.User;
import com.example.mtb.entity.UserDetails;

public interface UserService {
   public UserDetails addUser(UserRegistrationRequest user);
}
