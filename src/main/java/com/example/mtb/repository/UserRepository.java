package com.example.mtb.repository;

import com.example.mtb.entity.User;

import com.example.mtb.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, String> {
    boolean existsByEmail(String email);
    UserDetails findByEmail(String email);

}
