package com.example.mtb.controller;

import com.example.mtb.dto.UserRegistrationRequest;
import com.example.mtb.dto.UserResponse;
import com.example.mtb.dto.UserUpdationRequest;
import com.example.mtb.entity.UserDetails;
import com.example.mtb.service.UserService;
import com.example.mtb.util.ResponseStructure;
import com.example.mtb.util.RestResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final RestResponseBuilder responseBuilder;

    @PostMapping("/register")
    public ResponseEntity<ResponseStructure<UserResponse >> addUser(@RequestBody UserRegistrationRequest user) {
        UserResponse userDetails  = userService.addUser(user);

        return responseBuilder.sucess(HttpStatus.OK,"User Object Created Successfully",  userDetails) ;
    }
    @PutMapping("/users/{email}")
    public ResponseEntity<ResponseStructure<UserResponse>> editUser(@PathVariable String email, @RequestBody UserUpdationRequest user){
        UserResponse userDetails = userService.editUser(user, email);
        return responseBuilder.sucess(HttpStatus.OK,"User Details has been updated", userDetails);
    }
}
