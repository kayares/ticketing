package com.kayares.ticketing.controller;

import com.kayares.ticketing.dto.ShowCreateRequest;
import com.kayares.ticketing.dto.ShowResponse;
import com.kayares.ticketing.service.ShowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping
    public ResponseEntity<ShowResponse> create(@Valid @RequestBody ShowCreateRequest request) {
        ShowResponse response = showService.create(
                request.title(), request.prices(), request.venueId(), request.startAt()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
