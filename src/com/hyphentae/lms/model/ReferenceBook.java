package com.hyphentae.lms.model;

public class ReferenceBook extends Book {
    @Override
    public BookType getType() {
        return BookType.REFERENCE;
    }
}
