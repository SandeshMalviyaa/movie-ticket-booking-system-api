package com.example.mtb.service;

import com.example.mtb.dto.FeedbackRequest;
import com.example.mtb.dto.FeedbackResponse;

public interface FeedbackService {
    FeedbackResponse createFeedback(String movieId, FeedbackRequest feedbackRequest, String email);
}
