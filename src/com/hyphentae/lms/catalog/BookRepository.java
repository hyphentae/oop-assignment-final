package com.hyphentae.lms.catalog;

import com.hyphentae.lms.model.Book;
import com.hyphentae.lms.shared.CrudRepository;
import com.hyphentae.lms.shared.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository implements CrudRepository<Book, Long> {

    @Override
    public Book findById(Long id) throws SQLException {
        String sql = "select id, title, author, available, book_type from books where id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return map(rs);
            }
        }
    }

    @Override
    public List<Book> findAll() throws SQLException {
        String sql = "select id, title, author, available, book_type from books order by id";
        List<Book> result = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) result.add(map(rs));
        }
        return result;
    }

    public List<Book> findAllAvailable() throws SQLException {
        String sql = "select id, title, author, available, book_type from books where available = true order by id";
        List<Book> result = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) result.add(map(rs));
        }
        return result;
    }

    @Override
    public void save(Book book) throws SQLException {
        String sql = "insert into books (title, author, available, book_type) values (?, ?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setBoolean(3, book.isAvailable());
            ps.setString(4, book.getType().name());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Book book) throws SQLException {
        String sql = "update books set title = ?, author = ?, available = ?, book_type = ? where id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setBoolean(3, book.isAvailable());
            ps.setString(4, book.getType().name());
            ps.setLong(5, book.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        String sql = "delete from books where id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private Book map(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String title = rs.getString("title");
        String author = rs.getString("author");
        boolean available = rs.getBoolean("available");
        BookType type = BookType.valueOf(rs.getString("book_type"));
        return BookFactory.fromDb(type, id, title, author, available);
    }
}
