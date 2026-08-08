package com.kayares.ticketing.repository;

import com.kayares.ticketing.domain.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long> {
}
