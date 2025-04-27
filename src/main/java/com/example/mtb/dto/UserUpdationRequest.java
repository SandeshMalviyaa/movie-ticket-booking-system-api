package com.example.mtb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserUpdationRequest(

        @NotNull
        String username,
        @NotNull
        String email,
        @NotNull
        String phoneNumber,
        @NotNull
        LocalDate dateOfBirth) {
}
