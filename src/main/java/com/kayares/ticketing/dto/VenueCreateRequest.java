package com.kayares.ticketing.dto;

import com.kayares.ticketing.domain.Grade;
import jakarta.validation.constraints.*;

public record VenueCreateRequest(
        @NotBlank String name,
        @NotBlank String address,
        @NotNull @Min(Grade.COUNT) @Max(100) Integer rowCount,
        @NotNull @Min(1) @Max(100) Integer colCount
) {
}