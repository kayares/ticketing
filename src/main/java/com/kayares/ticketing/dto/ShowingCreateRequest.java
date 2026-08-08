package com.kayares.ticketing.dto;

import com.kayares.ticketing.domain.Show;
import com.kayares.ticketing.domain.Venue;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ShowingCreateRequest(
        @NotBlank Show show,
        @NotBlank Venue venue,
        @NotBlank LocalDateTime startAt
) {
}
