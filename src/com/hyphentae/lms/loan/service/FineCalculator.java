package com.hyphentae.lms.loan.service;

import com.hyphentae.lms.shared.LibraryConfig;

public class FineCalculator {
    public double calculate(long daysOverdue) {
        return daysOverdue * LibraryConfig.getInstance().getFinePerDay();
    }
}
