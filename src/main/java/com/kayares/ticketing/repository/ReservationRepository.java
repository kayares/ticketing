package com.kayares.ticketing.repository;

import com.kayares.ticketing.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
