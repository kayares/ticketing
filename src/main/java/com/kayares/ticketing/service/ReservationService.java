package com.kayares.ticketing.service;

import com.kayares.ticketing.domain.Reservation;
import com.kayares.ticketing.domain.ShowSeatStatus;
import com.kayares.ticketing.domain.ShowSeat;
import com.kayares.ticketing.domain.User;
import com.kayares.ticketing.dto.ReservationResponse;
import com.kayares.ticketing.exception.ShowSeatNotAvailableException;
import com.kayares.ticketing.exception.ShowSeatNotFoundException;
import com.kayares.ticketing.exception.UserNotFoundException;
import com.kayares.ticketing.repository.ReservationRepository;
import com.kayares.ticketing.repository.ShowSeatRepository;
import com.kayares.ticketing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final UserRepository userRepository;
    private final ShowSeatRepository showSeatRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservationResponse create(Long userId, Long showSeatId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        ShowSeat showSeat = showSeatRepository.findById(showSeatId)
                .orElseThrow(() -> new ShowSeatNotFoundException(showSeatId));

        if (showSeat.getStatus() != ShowSeatStatus.AVAILABLE) {
            throw new ShowSeatNotAvailableException(showSeatId);
        }

        showSeat.markAsSold();

        return ReservationResponse.from(reservationRepository.save(new Reservation(user, showSeat)));
    }
}
