package com.example.mtb.service.impl;

import com.example.mtb.dto.FeedbackRequest;
import com.example.mtb.dto.FeedbackResponse;
import com.example.mtb.entity.FeedBack;
import com.example.mtb.entity.User;
import com.example.mtb.exceptions.MovieNotFoundByIdException;
import com.example.mtb.mapper.FeedBackMapper;
import com.example.mtb.repository.FeedBackRepository;
import com.example.mtb.repository.MovieRepository;
import com.example.mtb.repository.UserRepository;
import com.example.mtb.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedBackRepository feedBackRepository;
    private final FeedBackMapper feedbackMapper;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;


    @Override
    public FeedbackResponse createFeedback(String movieId, FeedbackRequest feedbackRequest, String email) {
        if (movieRepository.existsById(movieId)) {

            FeedBack feedBack = copy(feedbackRequest, new FeedBack(), movieId, email);

            return feedbackMapper.feedbackResponseMapper(feedBack);
        }
        throw new MovieNotFoundByIdException("No movie found in database");
    }

    private FeedBack copy(FeedbackRequest feedbackRequest, FeedBack feedBack, String movieId, String email) {
        feedBack.setRating(feedbackRequest.ratings());
        feedBack.setReview(feedbackRequest.review());
        feedBack.setMovie(movieRepository.findById(movieId).get());
        feedBack.setUser((User) userRepository.findByEmail(email));
        feedBackRepository.save(feedBack);

        return feedBack;
    }
}
