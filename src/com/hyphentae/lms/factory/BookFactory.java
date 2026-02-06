package com.hyphentae.lms.factory;

import com.hyphentae.lms.model.*;

public class BookFactory {

    private BookFactory() {}

    public static Book create(BookType type, String title, String author) {
        Book b = createEmpty(type);
        b.setTitle(title);
        b.setAuthor(author);
        b.setAvailable(true);
        return b;
    }

    public static Book createEmpty(BookType type) {
        return switch (type) {
            case PRINTED -> new PrintedBook();
            case EBOOK -> new Ebook();
            case REFERENCE -> new ReferenceBook();
        };
    }

    public static Book fromDb(BookType type, long id, String title, String author, boolean available) {
        Book b = createEmpty(type);
        b.setId(id);
        b.setTitle(title);
        b.setAuthor(author);
        b.setAvailable(available);
        return b;
    }
}
