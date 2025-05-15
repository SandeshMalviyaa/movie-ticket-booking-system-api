package com.example.mtb.controller;

import com.example.mtb.dto.FeedbackRequest;
import com.example.mtb.dto.FeedbackResponse;
import com.example.mtb.service.FeedbackService;
import com.example.mtb.util.ResponseStructure;
import com.example.mtb.util.RestResponseBuilder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class FeedBackController {

    private final RestResponseBuilder responseBuilder;
    private final FeedbackService feedbackService;

    @PostMapping("/movies/{movieId}/feedbacks")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ResponseStructure<FeedbackResponse>> createFeedBack (@PathVariable String movieId, @RequestBody @Valid FeedbackRequest feedbackRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        FeedbackResponse feedbackResponse = feedbackService.createFeedback(movieId, feedbackRequest, email);
        return responseBuilder.sucess(HttpStatus.OK ,  "FeedBack Created" , feedbackResponse);
    }
}
