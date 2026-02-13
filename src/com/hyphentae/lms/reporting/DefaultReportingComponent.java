package com.hyphentae.lms.reporting;

import com.hyphentae.lms.loan.LoanService;

import java.sql.SQLException;

public class DefaultReportingComponent implements ReportingComponent {

    private final LoanService loanService;

    public DefaultReportingComponent(LoanService loanService) {
        this.loanService = loanService;
    }

    @Override
    public LoanReport memberLoanReport(long memberId) throws SQLException {
        return loanService.buildLoanReport(memberId);
    }
}
