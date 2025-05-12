package com.example.mtb.service;

import com.example.mtb.dto.ShowResponse;
import jakarta.validation.constraints.NotNull;

public interface ShowService {
    ShowResponse addShow(String theaterId, String screenId, String movieId, @NotNull Long startTime);
}
