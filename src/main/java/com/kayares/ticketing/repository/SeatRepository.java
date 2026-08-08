package com.kayares.ticketing.repository;

import com.kayares.ticketing.domain.Seat;
import com.kayares.ticketing.domain.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByVenue(Venue venue);
}
