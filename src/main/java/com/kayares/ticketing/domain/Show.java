package com.kayares.ticketing.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "shows")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ElementCollection
    @CollectionTable(name = "show_price", joinColumns = @JoinColumn(name = "show_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "grade")
    @Column(name = "price")
    private Map<Grade, Integer> prices = new HashMap<>();

    public Show(String title, Map<Grade, Integer> prices) {
        this.title = title;
        this.prices = prices;
    }
}
