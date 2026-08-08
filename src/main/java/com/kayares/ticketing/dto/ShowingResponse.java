package com.kayares.ticketing.dto;

import com.kayares.ticketing.domain.Showing;

import java.time.LocalDateTime;

public record ShowingResponse(
        Long id,
        Long showId,
        Long venueId,
        LocalDateTime startAt
) {
    public static ShowingResponse from(Showing showing) {
        return new ShowingResponse(
                showing.getId(),
                showing.getShow().getId(),
                showing.getVenue().getId(),
                showing.getStartAt());
    }
}
