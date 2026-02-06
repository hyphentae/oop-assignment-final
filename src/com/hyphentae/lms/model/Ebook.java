package com.hyphentae.lms.model;

public class Ebook extends Book {
    @Override
    public BookType getType() {
        return BookType.EBOOK;
    }
}
