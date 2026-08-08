package com.kayares.ticketing.repository;

import com.kayares.ticketing.domain.Showing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowingRepository extends JpaRepository<Showing, Long> {
}
