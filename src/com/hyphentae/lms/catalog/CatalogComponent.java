package com.hyphentae.lms.catalog;

import com.hyphentae.lms.model.Book;

import java.sql.SQLException;
import java.util.List;

public interface CatalogComponent {
    Book getBook(long bookId) throws SQLException;
    List<Book> listAvailableBooks() throws SQLException;
    List<Book> listAllBooks() throws SQLException;
    void setAvailability(long bookId, boolean available) throws SQLException;
}