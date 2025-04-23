package com.example.mtb.entity;

import com.example.mtb.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
@Entity
@Setter
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
public class UserDetails {

    @Id
    private String userId;
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private LocalDate dateOfBirth;
    private Instant createdAt;
    private Instant updatedAt;

}
