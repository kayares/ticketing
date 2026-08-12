package com.kayares.ticketing;

import com.kayares.ticketing.domain.Grade;
import com.kayares.ticketing.domain.ReservationStatus;
import com.kayares.ticketing.dto.ShowResponse;
import com.kayares.ticketing.dto.ShowingResponse;
import com.kayares.ticketing.repository.*;
import com.kayares.ticketing.service.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ReservationConcurrencyTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Autowired
    VenueRepository venueRepository;
    @Autowired
    VenueService venueService;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    ShowRepository showRepository;
    @Autowired
    ShowService showService;

    @Autowired
    ShowingRepository showingRepository;

    @Autowired
    ShowSeatRepository showSeatRepository;

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ReservationService reservationService;

    Long showSeatId;
    List<Long> userIds;

    @BeforeEach
    void setUp() {
        Long venueId = venueService.create("venue1", "address1", 4, 5).id();

        ShowResponse showResponse = showService.create(
                "show1",
                Map.of(
                        Grade.VIP, 150000,
                        Grade.R, 120000,
                        Grade.S, 90000,
                        Grade.A, 60000
                ),
                venueId,
                LocalDateTime.now().plusDays(1)
        );

        ShowingResponse showingResponse = showResponse.showing();

        showSeatId = showSeatRepository.findByShowingId(showingResponse.id()).getFirst().getId();

        userIds = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            userIds.add(userService.create("username" + i, "password" + i).id());
        }
    }

    @AfterEach
    void tearDown() {
        reservationRepository.deleteAll();
        showSeatRepository.deleteAll();
        showingRepository.deleteAll();
        showRepository.deleteAll();
        seatRepository.deleteAll();
        venueRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 동시에_여러_명이_같은_좌석을_예약해도_성공은_한_건이다() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);
        AtomicInteger success = new AtomicInteger();
        AtomicInteger fail = new AtomicInteger();
        List<String> exceptions = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < threadCount; i++) {
            Long userId = userIds.get(i);
            executor.submit(() -> {
                try {
                    startLatch.await();
                    reservationService.create(userId, showSeatId);
                    success.incrementAndGet();
                } catch (Exception e) {
                    fail.incrementAndGet();
                    exceptions.add(e.getClass().getSimpleName());
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        startLatch.countDown();
        doneLatch.await();
        executor.shutdown();

        long confirmedCount = reservationRepository
                .countByShowSeatIdAndStatus(showSeatId, ReservationStatus.CONFIRMED);

        System.out.println("성공: " + success.get());
        System.out.println("실패: " + fail.get());
        System.out.println("예외 종류: " + exceptions.stream().distinct().toList());

        assertThat(confirmedCount).isEqualTo(1L);
    }
}
