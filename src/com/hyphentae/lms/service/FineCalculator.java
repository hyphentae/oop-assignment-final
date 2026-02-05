package com.hyphentae.lms.service;

public class FineCalculator {
    private final double perDay = 100.0;

    public double calculate(long daysOverdue) {
        return daysOverdue * perDay;
    }
}
