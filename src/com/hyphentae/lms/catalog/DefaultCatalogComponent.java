package com.hyphentae.lms.catalog;

import com.hyphentae.lms.model.Book;
import com.hyphentae.lms.catalog.BookRepository;

import java.sql.SQLException;
import java.util.List;

public class DefaultCatalogComponent implements CatalogComponent {

    private final BookRepository bookRepo;

    public DefaultCatalogComponent(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public Book getBook(long bookId) throws SQLException {
        Book b = bookRepo.findById(bookId);
        if (b == null) throw new IllegalArgumentException("Book not found");
        return b;
    }

    @Override
    public List<Book> listAvailableBooks() throws SQLException {
        return bookRepo.findAllAvailable();
    }

    @Override
    public List<Book> listAllBooks() throws SQLException {
        return bookRepo.findAll();
    }

    @Override
    public void setAvailability(long bookId, boolean available) throws SQLException {
        Book b = getBook(bookId);
        b.setAvailable(available);
        bookRepo.update(b);
    }
}
