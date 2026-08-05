package com.kayares.ticketing.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record VenueCreateRequest(
        @NotBlank String name,
        @NotBlank String address,
        @NotNull @Positive @Max(100) Integer rowCount,
        @NotNull @Positive @Max(100) Integer colCount
) {
}