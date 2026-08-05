package com.kayares.ticketing.service;

import com.kayares.ticketing.domain.Grade;
import com.kayares.ticketing.domain.Seat;
import com.kayares.ticketing.domain.Venue;
import com.kayares.ticketing.dto.VenueResponse;
import com.kayares.ticketing.exception.VenueNotFoundException;
import com.kayares.ticketing.repository.SeatRepository;
import com.kayares.ticketing.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public VenueResponse create(String name, String address, int rowCount, int colCount) {
        Venue venue = venueRepository.save(new Venue(name, address));
        
        List<Seat> seats = new ArrayList<>();
        for (int rowNo = 1; rowNo <= rowCount; rowNo++) {
            for (int colNo = 1; colNo <= colCount; colNo++) {
                seats.add(new Seat(venue, rowNo, colNo, Grade.defaultGrade()));
            }
        }
        seatRepository.saveAll(seats);

        return VenueResponse.from(venue);
    }

    public List<VenueResponse> findAll() {
        return venueRepository.findAll().stream().map(VenueResponse::from).toList();
    }

    public VenueResponse findById(Long id) {
        return VenueResponse.from(
                venueRepository.findById(id).orElseThrow(() -> new VenueNotFoundException(id))
        );
    }
}
