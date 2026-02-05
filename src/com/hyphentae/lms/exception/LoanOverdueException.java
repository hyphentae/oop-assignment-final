package com.hyphentae.lms.exception;

public class LoanOverdueException extends RuntimeException {
    public LoanOverdueException(String message) {
        super(message);
    }
}
