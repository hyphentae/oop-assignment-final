package com.hyphentae.lms.member;

import java.sql.SQLException;

public interface MemberManagementComponent {
    Member getMember(long memberId) throws SQLException;
}
