package com.example.mtb.controller;

import com.example.mtb.dto.TheaterRequest;
import com.example.mtb.dto.TheaterResponse;
import com.example.mtb.service.TheaterService;
import com.example.mtb.util.ResponseStructure;
import com.example.mtb.util.RestResponseBuilder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TheaterController {
    private final TheaterService theaterService;
    private final RestResponseBuilder responseBuilder;

    @PostMapping("/theaters")
    @PreAuthorize("hashAuthority('THEATER_OWNER')")
    public ResponseEntity<ResponseStructure<TheaterResponse>> addTheater(String email ,@Valid @RequestBody TheaterRequest theaterRegisterationRequest) {
        TheaterResponse theaterResponse = theaterService.addTheater(email, theaterRegisterationRequest);
        return responseBuilder.sucess(HttpStatus.OK , "Theater has been succesfull created",theaterResponse);
    }

    @GetMapping("theaters/{theaterId}")
    public ResponseEntity<ResponseStructure<TheaterResponse>> findTheater(@PathVariable String theaterId) {
        TheaterResponse theaterResponse = theaterService.findtheater(theaterId);
        return responseBuilder.sucess(HttpStatus.OK , "Theater has been succesfull fetched",theaterResponse);
    }

    @PutMapping("/theaters/{theaterId}")
    @PreAuthorize("hashAuthority('THEATER_OWNER')")
    public ResponseEntity<ResponseStructure<TheaterResponse>> updateTheater(@PathVariable String theaterId, @Valid @RequestBody TheaterRequest registerationRequest){
        TheaterResponse theaterResponse = theaterService.updateTheater(theaterId, registerationRequest);
        return responseBuilder.sucess(HttpStatus.OK, "Theater has been sucessfully Updated", theaterResponse);
    }
}
