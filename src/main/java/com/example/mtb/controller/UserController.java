package com.example.mtb.controller;

import com.example.mtb.dto.UserRegistrationRequest;
import com.example.mtb.entity.UserDetails;
import com.example.mtb.service.UserService;
import com.example.mtb.util.ResponseStructure;
import com.example.mtb.util.RestResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final RestResponseBuilder responseBuilder;

    @PostMapping("/register")
    public ResponseEntity<ResponseStructure<UserDetails>> addUser(@RequestBody UserRegistrationRequest user) {
        UserDetails userDetails  = userService.addUser(user);

        return responseBuilder.sucess(HttpStatus.CREATED,"User Object Created Successfully",  userDetails) ;
    }
}
