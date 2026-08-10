package com.kayares.ticketing.exception;

public class ShowSeatNotAvailableException extends RuntimeException {
    public ShowSeatNotAvailableException(Long showSeatId) {
        super("ShowSeatId not available: " + showSeatId);
    }
}
