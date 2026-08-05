package com.kayares.ticketing.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_seat_venue_row_col",
                columnNames = {"venue_id", "row_no", "col_no"}
        )
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @Column(nullable = false)
    private int rowNo;

    @Column(nullable = false)
    private int colNo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Grade grade;

    public Seat(Venue venue, int rowNo, int colNo, Grade grade) {
        this.venue = venue;
        this.rowNo = rowNo;
        this.colNo = colNo;
        this.grade = grade;
    }
}

