package com.hyphentae.lms.loan;

public class BookAlreadyOnLoanException extends RuntimeException {
    public BookAlreadyOnLoanException(String message) {
        super(message);
    }
}
