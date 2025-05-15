package com.example.mtb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User extends UserDetails {

    @OneToMany(mappedBy = "user")
    private List<FeedBack> feedbacks;
}
