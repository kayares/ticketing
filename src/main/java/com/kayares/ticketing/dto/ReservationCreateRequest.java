package com.kayares.ticketing.dto;

import jakarta.validation.constraints.NotNull;

public record ReservationCreateRequest(
        @NotNull Long showSeatId
) {
}
