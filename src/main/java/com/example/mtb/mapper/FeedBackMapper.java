package com.example.mtb.mapper;

import com.example.mtb.dto.FeedbackResponse;
import com.example.mtb.entity.FeedBack;
import org.springframework.stereotype.Component;

@Component
public class FeedBackMapper {

    public FeedbackResponse feedbackResponseMapper(FeedBack feedBack) {
        if (feedBack == null)
            return null;

        return new FeedbackResponse(
                feedBack.getFeedBackId(),
                feedBack.getRating(),
                feedBack.getReview()
        );
    }
}
