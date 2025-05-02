package com.example.mtb.dto;

import lombok.Builder;

@Builder
public record SeatResponse(
        String seatId,
        String name
) {
}
