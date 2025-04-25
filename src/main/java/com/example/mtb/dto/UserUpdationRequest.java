package com.example.mtb.dto;

import java.time.LocalDate;

public record UserUpdationRequest (String username,
                                   String email,
                                   String phoneNumber,
                                   LocalDate dateOfBirth)
{}
