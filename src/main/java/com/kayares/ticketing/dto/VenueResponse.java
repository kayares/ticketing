package com.kayares.ticketing.dto;

import com.kayares.ticketing.domain.Venue;

public record VenueResponse(
        Long id,
        String name,
        String address
) {
    public static VenueResponse from(Venue venue) {
        return new VenueResponse(venue.getId(), venue.getName(), venue.getAddress());
    }
}