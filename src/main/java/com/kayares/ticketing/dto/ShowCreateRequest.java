package com.kayares.ticketing.dto;

import com.kayares.ticketing.domain.Grade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Map;

public record ShowCreateRequest(
        @NotBlank String title,
        @NotNull Map<Grade, Integer> prices,
        @NotNull Long venueId,
        @NotNull LocalDateTime startAt
) {
}
