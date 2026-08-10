package com.kayares.ticketing.dto;

import com.kayares.ticketing.domain.Reservation;

public record ReservationResponse(
        Long reservationId,
        Long showSeatId
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getShowSeat().getId());
    }
}
