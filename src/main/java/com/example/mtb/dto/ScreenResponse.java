package com.example.mtb.dto;

import com.example.mtb.enums.ScreenType;
import lombok.Builder;

import java.util.List;

@Builder
public record ScreenResponse(
        String screenId,
        ScreenType screenType,
        Integer capacity,
        Integer noOfRows,
        List<SeatResponse> seats
) {
}
