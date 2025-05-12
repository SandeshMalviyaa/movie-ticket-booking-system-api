package com.example.mtb.controller;

import com.example.mtb.util.ResponseStructure;
import org.springframework.http.ResponseEntity;
import com.example.mtb.service.ShowService;
import com.example.mtb.util.RestResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Validated
public class ShowController {

    private final ShowService showService;
    private final RestResponseBuilder responseBuilder;

//    public ResponseEntity<ResponseStructure<ShowRe>>
}
