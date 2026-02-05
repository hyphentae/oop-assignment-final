package com.hyphentae.lms.exception;

public class BookAlreadyOnLoanException extends RuntimeException {
    public BookAlreadyOnLoanException(String message) {
        super(message);
    }
}
