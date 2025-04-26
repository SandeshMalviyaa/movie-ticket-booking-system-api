package com.example.mtb.dto;

import com.example.mtb.enums.UserRole;

public record UserResponse(String userId,
                           String username,
                           String email,
                           String phoneNumber,
                           UserRole userRole) {
}
