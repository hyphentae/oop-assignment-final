package com.hyphentae.lms.loan;

public class LoanOverdueException extends RuntimeException {
    public LoanOverdueException(String message) {
        super(message);
    }
}
