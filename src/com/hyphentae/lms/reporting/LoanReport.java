package com.hyphentae.lms.reporting;

import com.hyphentae.lms.loan.Loan;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoanReport {
    private final long memberId;
    private final String memberName;
    private final LocalDateTime generatedAt;
    private final List<Loan> activeLoans;

    private LoanReport(Builder b) {
        this.memberId = b.memberId;
        this.memberName = b.memberName;
        this.generatedAt = b.generatedAt;
        this.activeLoans = Collections.unmodifiableList(new ArrayList<>(b.activeLoans));
    }

    public long getMemberId() { return memberId; }
    public String getMemberName() { return memberName; }
    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public List<Loan> getActiveLoans() { return activeLoans; }

    //builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private long memberId;
        private String memberName;
        private LocalDateTime generatedAt = LocalDateTime.now();
        private List<Loan> activeLoans = new ArrayList<>();

        public Builder memberId(long memberId) { this.memberId = memberId; return this; }
        public Builder memberName(String memberName) { this.memberName = memberName; return this; }
        public Builder generatedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; return this; }
        public Builder activeLoans(List<Loan> loans) { this.activeLoans = loans; return this; }

        public LoanReport build() { return new LoanReport(this); }
    }
}