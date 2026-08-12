package com.kayares.ticketing.repository;

import com.kayares.ticketing.domain.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    List<ShowSeat> findByShowingId(Long showingId);
}
