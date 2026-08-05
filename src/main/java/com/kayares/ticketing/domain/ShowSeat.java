package com.kayares.ticketing.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_show_seat_showing_id_seat_id",
                columnNames = {"showing_id", "seat_id"}
        )
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showing_id", nullable = false)
    private Showing showing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    @Column(nullable = false, precision = 10, scale = 0)
    private BigDecimal price;

    @Version
    private int version;

    public ShowSeat(Showing showing, Seat seat, BigDecimal price) {
        this.showing = showing;
        this.seat = seat;
        this.price = price;
        this.status = SeatStatus.AVAILABLE;
    }
}
