package com.kayares.ticketing.controller;

import com.kayares.ticketing.dto.ReservationCreateRequest;
import com.kayares.ticketing.dto.ReservationResponse;
import com.kayares.ticketing.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> create(
            @RequestHeader("X-USER-ID") Long userId,
            @Valid @RequestBody ReservationCreateRequest request) {
        ReservationResponse response = reservationService.create(userId, request.showSeatId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
