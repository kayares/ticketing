package com.kayares.ticketing.repository;

import com.kayares.ticketing.domain.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}
