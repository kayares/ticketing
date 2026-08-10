package com.kayares.ticketing.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("UserId not found: " + userId);
    }
}
