package com.kayares.ticketing.service;

import com.kayares.ticketing.domain.Show;
import com.kayares.ticketing.domain.ShowSeat;
import com.kayares.ticketing.domain.Showing;
import com.kayares.ticketing.domain.Venue;
import com.kayares.ticketing.dto.ShowingResponse;
import com.kayares.ticketing.exception.VenueNotFoundException;
import com.kayares.ticketing.repository.SeatRepository;
import com.kayares.ticketing.repository.ShowSeatRepository;
import com.kayares.ticketing.repository.ShowingRepository;
import com.kayares.ticketing.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShowingService {

    private final VenueRepository venueRepository;
    private final SeatRepository seatRepository;
    private final ShowingRepository showingRepository;
    private final ShowSeatRepository showSeatRepository;

    @Transactional
    public ShowingResponse create(Show show, Long venueId, LocalDateTime startAt) {
        Venue venue = venueRepository.findById(venueId).orElseThrow(
                () -> new VenueNotFoundException(venueId)
        );

        Showing showing = showingRepository.save(new Showing(show, venue, startAt));

        List<ShowSeat> showSeats = seatRepository.findByVenue(venue).stream()
                .map(seat -> new ShowSeat(showing, seat))
                .toList();
        showSeatRepository.saveAll(showSeats);

        return ShowingResponse.from(showing);
    }
}
