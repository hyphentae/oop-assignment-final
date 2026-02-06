package com.hyphentae.lms.config;

public class LibraryConfig {

    private final int loanDays;
    private final double finePerDay;

    private LibraryConfig() {
        this.loanDays = 14;
        this.finePerDay = 100.0;
    }

    private static class Holder {
        private static final LibraryConfig INSTANCE = new LibraryConfig();
    }

    public static LibraryConfig getInstance() {
        return Holder.INSTANCE;
    }

    public int getLoanDays() { return loanDays; }
    public double getFinePerDay() { return finePerDay; }
}
