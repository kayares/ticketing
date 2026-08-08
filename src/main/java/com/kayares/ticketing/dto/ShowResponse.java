package com.kayares.ticketing.dto;

import com.kayares.ticketing.domain.Show;

public record ShowResponse(
        Long id,
        String title,
        ShowingResponse showing
) {
    public static ShowResponse from(Show show, ShowingResponse showing) {
        return new ShowResponse(show.getId(), show.getTitle(), showing);
    }
}
