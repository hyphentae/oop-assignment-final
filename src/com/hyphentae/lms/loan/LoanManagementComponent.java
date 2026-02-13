package com.hyphentae.lms.loan;

import java.sql.SQLException;
import java.util.List;

public interface LoanManagementComponent {
    void borrowBook(long memberId, long bookId) throws SQLException;
    double returnBook(long memberId, long bookId) throws SQLException;

    List<Loan> getCurrentLoansForMember(long memberId) throws SQLException;
}
