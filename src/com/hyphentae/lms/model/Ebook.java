package com.hyphentae.lms.model;

import com.hyphentae.lms.catalog.BookType;

public class Ebook extends Book {
    @Override
    public BookType getType() {
        return BookType.EBOOK;
    }
}
