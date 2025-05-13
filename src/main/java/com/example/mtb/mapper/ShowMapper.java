package com.example.mtb.mapper;

import com.example.mtb.dto.ShowResponse;
import com.example.mtb.entity.Show;
import org.springframework.stereotype.Component;

@Component
public class ShowMapper {
    public ShowResponse showResponseMapper(Show show) {
        if (show == null)
            return null;
        return new ShowResponse(
                show.getShowId(),
                show.getStartsAt(),
                show.getEndsAt()
        );
    }
}
