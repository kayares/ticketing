package com.kayares.ticketing.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_showing_venue_start_at",
                columnNames = {"venue_id", "start_at"}
        )
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Showing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @Column(nullable = false)
    private LocalDateTime startAt;

    public Showing(Show show, Venue venue, LocalDateTime startAt) {
        this.show = show;
        this.venue = venue;
        this.startAt = startAt;
    }
}
