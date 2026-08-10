package com.kayares.ticketing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VenueNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleVenueNotFound(VenueNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("VENUE_NOT_FOUND", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("INVALID_INPUT", message));
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUsername(DuplicateUsernameException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("DUPLICATE_USERNAME", e.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("USER_NOT_FOUND", e.getMessage()));
    }

    @ExceptionHandler(ShowSeatNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleShowSeatNotFound(ShowSeatNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("SHOW_SEAT_NOT_FOUND", e.getMessage()));
    }

    @ExceptionHandler(ShowSeatNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleShowSeatNotAvailable(ShowSeatNotAvailableException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("SHOW_SEAT_NOT_AVAILABLE", e.getMessage()));
    }
}
