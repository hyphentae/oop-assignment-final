package com.hyphentae.lms.model;

import com.hyphentae.lms.catalog.BookType;

public class PrintedBook extends Book {
    @Override
    public BookType getType() {
        return BookType.PRINTED;
    }
}
