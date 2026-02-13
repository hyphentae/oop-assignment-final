package com.hyphentae.lms;

import com.hyphentae.lms.catalog.*;
import com.hyphentae.lms.model.Book;

import com.hyphentae.lms.loan.*;
import com.hyphentae.lms.loan.service.FineCalculator;
import com.hyphentae.lms.loan.LoanService;

import com.hyphentae.lms.member.MemberNotFoundException;
import com.hyphentae.lms.member.MemberRepository;

import com.hyphentae.lms.reporting.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Low-level objects (repositories/services)
        BookRepository bookRepo = new BookRepository();
        MemberRepository memberRepo = new MemberRepository();
        LoanRepository loanRepo = new LoanRepository();
        FineCalculator fineCalc = new FineCalculator();
        LoanService loanService = new LoanService(bookRepo, memberRepo, loanRepo, fineCalc);

        // Components (facades)
        CatalogComponent catalog = new DefaultCatalogComponent(bookRepo);
        LoanManagementComponent loans = new DefaultLoanManagementComponent(loanService);
        ReportingComponent reports = new DefaultReportingComponent(loanService);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Borrow book");
            System.out.println("2. Return book");
            System.out.println("3. View member loans");
            System.out.println("4. List available books");
            System.out.println("5. Generate loan report");
            System.out.println("0. Exit");

            int choice = Integer.parseInt(sc.nextLine());

            try {
                if (choice == 1) {
                    System.out.print("Member id: ");
                    long m = Long.parseLong(sc.nextLine());
                    System.out.print("Book id: ");
                    long b = Long.parseLong(sc.nextLine());

                    loans.borrowBook(m, b);
                    System.out.println("Book borrowed.");
                } else if (choice == 2) {
                    System.out.print("Member id: ");
                    long m = Long.parseLong(sc.nextLine());
                    System.out.print("Book id: ");
                    long b = Long.parseLong(sc.nextLine());

                    loans.returnBook(m, b);
                    System.out.println("Book returned.");
                } else if (choice == 3) {
                    System.out.print("Member id: ");
                    long m = Long.parseLong(sc.nextLine());

                    List<Loan> memberLoans = loans.getCurrentLoansForMember(m);
                    for (Loan l : memberLoans) {
                        System.out.println("Loan " + l.getId() +
                                " bookId=" + l.getBookId() +
                                " due=" + l.getDueDate());
                    }
                } else if (choice == 4) {
                    List<Book> books = catalog.listAvailableBooks();
                    for (Book b : books) {
                        System.out.println(b.getId() + " - " + b.getTitle()
                                + " by " + b.getAuthor()
                                + " (" + b.getType() + ")");
                    }
                } else if (choice == 5) {
                    System.out.print("Member id: ");
                    long m = Long.parseLong(sc.nextLine());

                    LoanReport report = reports.memberLoanReport(m);
                    System.out.println("Report for member " + report.getMemberId()
                            + " (" + report.getMemberName() + ")");
                    System.out.println("Generated: " + report.getGeneratedAt());
                    System.out.println("Active loans: " + report.getActiveLoans().size());
                } else if (choice == 0) {
                    break;
                }
            } catch (BookAlreadyOnLoanException | LoanOverdueException | MemberNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
