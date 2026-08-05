package com.kayares.ticketing.domain;

public enum Grade {
    VIP, R, S, A;

    public static Grade defaultGrade() {
        return A;
    }
}
