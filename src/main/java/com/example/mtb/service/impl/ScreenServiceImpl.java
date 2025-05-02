package com.example.mtb.service.impl;

import com.example.mtb.dto.ScreenRequest;
import com.example.mtb.dto.ScreenResponse;
import com.example.mtb.entity.*;
import com.example.mtb.exceptions.NoOfRowsExceedCapacityException;
import com.example.mtb.mapper.ScreenMapper;
import com.example.mtb.repository.ScreenRepository;
import com.example.mtb.repository.SeatRepository;
import com.example.mtb.repository.TheaterRepository;
import com.example.mtb.service.ScreenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class ScreenServiceImpl implements ScreenService {

    private final TheaterRepository theaterRepository;
    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;
    private final ScreenMapper screenMapper;


    @Override
    public ScreenResponse addScreen(ScreenRequest screenRequest, String theaterId) {
        if (theaterRepository.existsById(theaterId)) {
            Theater theater = theaterRepository.findById(theaterId).get();
            Screen screen = copy(screenRequest, new Screen(), theater);
            return screenMapper.screenResponseMapper(screen);
        }
        return null;
    }

    private Screen copy(ScreenRequest screenRequest, Screen screen, Theater theater) {
        screen.setScreenType(screenRequest.screenType());
        screen.setCapacity(screenRequest.capacity());

        if (screenRequest.noOfRows() > screenRequest.capacity())
            throw new NoOfRowsExceedCapacityException("The no.of rows exceed the capacity");
        screen.setNoOfRows(screenRequest.noOfRows());
        screen.setTheater(theater);
        screenRepository.save(screen);
        screen.setSeats(createSeats(screen, screenRequest.capacity()));
        return screen;
    }

    private List<Seat> createSeats(Screen screen, Integer capacity) {
        List<Seat> seats = new LinkedList<>();
        int noOfSeatsPerRow = screen.getCapacity() / screen.getNoOfRows();
        char row = 'A';
        for (int i = 1, j = 1; i <= capacity; i++, j++) {
            Seat seat = new Seat();
            seat.setScreen(screen);
            seat.setIsDelete(false);
            seat.setName(row + "" + j);
            seatRepository.save(seat);
            seats.add(seat);
            if (j == noOfSeatsPerRow) {
                j = 0;
                row++;
            }
        }
        return seats;
    }
}
