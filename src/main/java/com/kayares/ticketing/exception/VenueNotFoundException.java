package com.kayares.ticketing.exception;

public class VenueNotFoundException extends RuntimeException {
    public VenueNotFoundException(Long id) {
        super("Venue not found: " + id);
    }
}
