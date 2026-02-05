package com.hyphentae.lms.repository;

import com.hyphentae.lms.db.DatabaseConnection;
import com.hyphentae.lms.model.Loan;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanRepository {

    public Loan findActiveLoanByBook(long bookId) throws SQLException {
        String sql = "select * from loans where book_id = ? and return_date is null";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapLoan(rs);
                return null;
            }
        }
    }

    public List<Loan> findActiveLoansByMember(long memberId) throws SQLException {
        String sql = "select * from loans where member_id = ? and return_date is null";
        List<Loan> result = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, memberId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) result.add(mapLoan(rs));
            }
        }
        return result;
    }

    public void save(Loan loan) throws SQLException {
        String sql = "insert into loans (book_id, member_id, loan_date, due_date) values (?, ?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, loan.getBookId());
            ps.setLong(2, loan.getMemberId());
            ps.setObject(3, loan.getLoanDate());
            ps.setObject(4, loan.getDueDate());
            ps.executeUpdate();
        }
    }

    public void update(Loan loan) throws SQLException {
        String sql = "update loans set return_date = ? where id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, loan.getReturnDate());
            ps.setLong(2, loan.getId());
            ps.executeUpdate();
        }
    }

    private Loan mapLoan(ResultSet rs) throws SQLException {
        Loan l = new Loan();
        l.setId(rs.getLong("id"));
        l.setBookId(rs.getLong("book_id"));
        l.setMemberId(rs.getLong("member_id"));
        l.setLoanDate(rs.getObject("loan_date", LocalDate.class));
        l.setDueDate(rs.getObject("due_date", LocalDate.class));
        Date ret = rs.getDate("return_date");
        if (ret != null) l.setReturnDate(ret.toLocalDate());
        return l;
    }
}
