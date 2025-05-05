package com.example.mtb.entity;

import com.example.mtb.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
@Entity
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String userId;

    @Column(name = "username")
    private String userName;

    @Column(unique = true, name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;

    @Column(name = "dob")
    private LocalDate dateOfBirth;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "is_delete")
    private boolean isDelete;

    @Column(name = "deleted_at")
    private Instant deletedAt;
}
