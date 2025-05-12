package com.example.mtb.controller;

import com.example.mtb.dto.ShowResponse;
import com.example.mtb.util.ResponseStructure;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.mtb.service.ShowService;
import com.example.mtb.util.RestResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Validated
public class ShowController {

    private final ShowService showService;
    private final RestResponseBuilder responseBuilder;

    @PostMapping("theaters/{theaterId}/screens/{screenId}/shows")
    @PreAuthorize("hasAuthority('THEATER_OWNER')")
    public ResponseEntity<ResponseStructure<ShowResponse>> addShow(@PathVariable  String theaterId, @PathVariable String screenId, String movieId , @NotNull Long startTime ){
        ShowResponse showResponse = showService.addShow(theaterId, screenId, movieId, startTime);
        return responseBuilder.sucess(HttpStatus.OK, "Show sucessfully created", showResponse);
    }
}
