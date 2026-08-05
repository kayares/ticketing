package com.kayares.ticketing.repository;

import com.kayares.ticketing.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
