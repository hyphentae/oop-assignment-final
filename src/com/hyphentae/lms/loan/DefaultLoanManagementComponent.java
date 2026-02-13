package com.hyphentae.lms.loan;

import com.hyphentae.lms.loan.LoanService;

import java.sql.SQLException;
import java.util.List;

public class DefaultLoanManagementComponent implements LoanManagementComponent {

    private final LoanService loanService;

    public DefaultLoanManagementComponent(LoanService loanService) {
        this.loanService = loanService;
    }

    @Override
    public void borrowBook(long memberId, long bookId) throws SQLException {
        loanService.borrowBook(memberId, bookId);
    }

    @Override
    public double returnBook(long memberId, long bookId) throws SQLException {
        return loanService.returnBook(memberId, bookId);
    }

    @Override
    public List<Loan> getCurrentLoansForMember(long memberId) throws SQLException {
        return loanService.getCurrentLoansForMember(memberId);
    }
}
