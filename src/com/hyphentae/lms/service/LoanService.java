package com.hyphentae.lms.service;

import com.hyphentae.lms.exception.*;
import com.hyphentae.lms.model.*;
import com.hyphentae.lms.repository.*;
import com.hyphentae.lms.report.LoanReport;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class LoanService {

    private final BookRepository bookRepo;
    private final MemberRepository memberRepo;
    private final LoanRepository loanRepo;
    private final FineCalculator fineCalc;

    public LoanService(BookRepository b, MemberRepository m, LoanRepository l, FineCalculator f) {
        this.bookRepo = b;
        this.memberRepo = m;
        this.loanRepo = l;
        this.fineCalc = f;
    }

    public void borrowBook(long memberId, long bookId) throws SQLException {
        Member member = memberRepo.findById(memberId);
        if (member == null) throw new MemberNotFoundException("Member not found");

        Book book = bookRepo.findById(bookId);
        if (book == null) throw new IllegalArgumentException("Book not found");
        if (!book.isAvailable() || loanRepo.findActiveLoanByBook(bookId) != null) {
            throw new BookAlreadyOnLoanException("Book already on loan");
        }

        Loan loan = new Loan();
        loan.setBookId(bookId);
        loan.setMemberId(memberId);
        loan.setLoanDate(LocalDate.now());
        int days = com.hyphentae.lms.config.LibraryConfig.getInstance().getLoanDays();
        loan.setDueDate(LocalDate.now().plusDays(days));
        loanRepo.save(loan);

        book.setAvailable(false);
        bookRepo.update(book);
    }

    public double returnBook(long memberId, long bookId) throws SQLException {
        Loan loan = loanRepo.findActiveLoanByBook(bookId);
        if (loan == null) throw new IllegalArgumentException("No active loan for this book");
        if (loan.getMemberId() != memberId) {
            throw new IllegalArgumentException("This member did not borrow the book");
        }

        LocalDate today = LocalDate.now();
        loan.setReturnDate(today);
        loanRepo.update(loan);

        Book book = bookRepo.findById(bookId);
        book.setAvailable(true);
        bookRepo.update(book);

        if (today.isAfter(loan.getDueDate())) {
            long days = ChronoUnit.DAYS.between(loan.getDueDate(), today);
            double fine = fineCalc.calculate(days);
            throw new LoanOverdueException("Overdue " + days + " days. Fine: " + fine);
        }
        return 0;
    }

    public List<Loan> getCurrentLoansForMember(long memberId) throws SQLException {
        return loanRepo.findActiveLoansByMember(memberId);
    }

    public List<Book> listAvailableBooks() throws SQLException {
        return bookRepo.findAllAvailable();
    }

    public LoanReport buildLoanReport(long memberId) throws SQLException {
        Member member = memberRepo.findById(memberId);
        if (member == null) throw new MemberNotFoundException("Member not found");

        List<Loan> loans = loanRepo.findActiveLoansByMember(memberId);

        return LoanReport.builder()
                .memberId(member.getId())
                .memberName(member.getName())
                .activeLoans(loans)
                .build();
    }
}
