package com.hyphentae.lms.service;

import com.hyphentae.lms.config.LibraryConfig;

public class FineCalculator {
    public double calculate(long daysOverdue) {
        return daysOverdue * LibraryConfig.getInstance().getFinePerDay();
    }
}
