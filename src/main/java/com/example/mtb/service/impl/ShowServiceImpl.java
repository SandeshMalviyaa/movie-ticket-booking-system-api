package com.example.mtb.service.impl;

import com.example.mtb.dto.ShowResponse;
import com.example.mtb.entity.Movie;
import com.example.mtb.entity.Screen;
import com.example.mtb.entity.Show;
import com.example.mtb.exceptions.ShowTimeConflictException;
import com.example.mtb.repository.MovieRepository;
import com.example.mtb.repository.ScreenRepository;
import com.example.mtb.repository.TheaterRepository;
import com.example.mtb.service.ShowService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
@AllArgsConstructor
public class ShowServiceImpl implements ShowService {
    TheaterRepository theaterRepository;
    ScreenRepository screenRepository;
    MovieRepository movieRepository;

    @Override
    public ShowResponse addShow(String theaterId, String screenId, String movieId, Long startTime) {
        if(theaterRepository.existsById(theaterId)){

           if(screenRepository.existsById(screenId)) {

               if(movieRepository.existsById(movieId)) {

                   Screen screen = screenRepository.findById(screenId).get();
                   Set<Show> shows = screen.getShows();

                   Movie movie = movieRepository.findById(movieId).get();
                   Instant instantStarttime= Instant.ofEpochMilli(startTime);

                   for(Show s: shows){
                       Instant showStartTime = s.getStartsAt();
                       Instant showEndTime = s.getEndsAt();
                       Instant movieCompletionTime = instantStarttime.plus(movie.getRuntime());

                       if(!(movieCompletionTime.isBefore(showStartTime) || instantStarttime.isAfter(showEndTime))) {
                           throw new ShowTimeConflictException("Another Show is been Booked");
                       }
                   }
               }

           }
        }
        return null;
    }
}