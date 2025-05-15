package com.example.mtb.dto;

public record FeedbackResponse(
        String feedbackId,
        int rating,
        String review
) {
}
