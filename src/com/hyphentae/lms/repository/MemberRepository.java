package com.hyphentae.lms.repository;

import com.hyphentae.lms.db.DatabaseConnection;
import com.hyphentae.lms.model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRepository {

    public Member findById(long id) throws SQLException {
        String sql = "select id, name from members where id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Member m = new Member();
                    m.setId(rs.getLong("id"));
                    m.setName(rs.getString("name"));
                    return m;
                }
                return null;
            }
        }
    }
}
