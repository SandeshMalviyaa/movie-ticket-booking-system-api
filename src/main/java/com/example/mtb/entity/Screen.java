package com.example.mtb.entity;

import com.example.mtb.enums.ScreenType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@ToString
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "screen_id")
    private String screenId;

    @Column(name = "screen_type")
    private ScreenType screenType;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "no_of_rows")
    private Integer noOfRows;

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @OneToMany(mappedBy = "screen", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @OrderBy(value = "name")
    @JsonIgnore
    private List<Seat> seats;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;
}
