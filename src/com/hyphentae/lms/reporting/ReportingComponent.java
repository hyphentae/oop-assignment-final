package com.hyphentae.lms.reporting;

import java.sql.SQLException;

public interface ReportingComponent {
    LoanReport memberLoanReport(long memberId) throws SQLException;
}
