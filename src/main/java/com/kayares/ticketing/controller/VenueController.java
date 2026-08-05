package com.kayares.ticketing.controller;

import com.kayares.ticketing.dto.VenueCreateRequest;
import com.kayares.ticketing.dto.VenueResponse;
import com.kayares.ticketing.service.VenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;

    @PostMapping
    public ResponseEntity<VenueResponse> create(@Valid @RequestBody VenueCreateRequest request) {
        VenueResponse response = venueService.create(
                request.name(), request.address(), request.rowCount(), request.colCount()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<VenueResponse>> findAll() {
        List<VenueResponse> responses = venueService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueResponse> findById(@PathVariable Long id) {
        VenueResponse response = venueService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
