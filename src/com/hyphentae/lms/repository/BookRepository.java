package com.hyphentae.lms.repository;

import com.hyphentae.lms.db.DatabaseConnection;
import com.hyphentae.lms.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    public Book findById(long id) throws SQLException {
        String sql = "select id, title, author, available from books where id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Book b = new Book();
                    b.setId(rs.getLong("id"));
                    b.setTitle(rs.getString("title"));
                    b.setAuthor(rs.getString("author"));
                    b.setAvailable(rs.getBoolean("available"));
                    return b;
                }
                return null;
            }
        }
    }

    public List<Book> findAllAvailable() throws SQLException {
        String sql = "select id, title, author, available from books where available = true";
        List<Book> result = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getLong("id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setAvailable(rs.getBoolean("available"));
                result.add(b);
            }
        }
        return result;
    }

    public void update(Book book) throws SQLException {
        String sql = "update books set title = ?, author = ?, available = ? where id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setBoolean(3, book.isAvailable());
            ps.setLong(4, book.getId());
            ps.executeUpdate();
        }
    }
}
