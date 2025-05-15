package com.example.mtb.repository;

import com.example.mtb.entity.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedBackRepository extends JpaRepository<FeedBack , String> {
}
