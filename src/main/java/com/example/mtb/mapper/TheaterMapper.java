package com.example.mtb.mapper;

import org.springframework.stereotype.Component;
import com.example.mtb.dto.TheaterResponse;
import com.example.mtb.entity.Theater;

@Component
public class TheaterMapper {
    public TheaterResponse theaterResponseMapper(Theater theater) {
        if (theater == null)
            return null;
        return new TheaterResponse(
                theater.getTheaterId(),
                theater.getName(),
                theater.getAddress(),
                theater.getCity(),
                theater.getLandmark()
        );
    }
}
