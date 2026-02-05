package com.hyphentae.lms.model;

public class PrintedBook extends Book {
    @Override
    public BookType getType() {
        return BookType.PRINTED;
    }
}
