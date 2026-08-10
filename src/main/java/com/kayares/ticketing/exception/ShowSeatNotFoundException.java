package com.kayares.ticketing.exception;

public class ShowSeatNotFoundException extends RuntimeException {
    public ShowSeatNotFoundException(Long showSeatId) {
        super("ShowSeatId not found: " + showSeatId);
    }
}
