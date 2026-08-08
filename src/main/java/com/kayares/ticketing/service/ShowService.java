    package com.kayares.ticketing.service;

    import com.kayares.ticketing.domain.Grade;
    import com.kayares.ticketing.domain.Show;
    import com.kayares.ticketing.dto.ShowResponse;
    import com.kayares.ticketing.dto.ShowingResponse;
    import com.kayares.ticketing.repository.ShowRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.time.LocalDateTime;
    import java.util.Map;

    @Service
    @Transactional(readOnly = true)
    @RequiredArgsConstructor
    public class ShowService {

        private final ShowRepository showRepository;
        private final ShowingService showingService;

        @Transactional
        public ShowResponse create(String title, Map<Grade, Integer> prices, Long venueId, LocalDateTime startAt) {
            Show show = showRepository.save(new Show(title, prices));
            ShowingResponse showingResponse = showingService.create(show, venueId, startAt);

            return ShowResponse.from(show, showingResponse);
        }
    }
