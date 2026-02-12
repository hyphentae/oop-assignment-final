package com.hyphentae.lms;

import com.hyphentae.lms.catalog.BookRepository;
import com.hyphentae.lms.exception.*;
import com.hyphentae.lms.loan.LoanService;
import com.hyphentae.lms.member.MemberNotFoundException;
import com.hyphentae.lms.member.MemberRepository;
import com.hyphentae.lms.model.Book;
import com.hyphentae.lms.loan.Loan;
import com.hyphentae.lms.repository.*;
import com.hyphentae.lms.service.*;
import com.hyphentae.lms.reporting.LoanReport;


import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

//

public class Main {
    public static void main(String[] args) {
        BookRepository bookRepo = new BookRepository();
        MemberRepository memberRepo = new MemberRepository();
        LoanRepository loanRepo = new LoanRepository();
        FineCalculator fineCalc = new FineCalculator();
        LoanService loanService = new LoanService(bookRepo, memberRepo, loanRepo, fineCalc);

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
                    loanService.borrowBook(m, b);
                    System.out.println("Book borrowed.");
                } else if (choice == 2) {
                    System.out.print("Member id: ");
                    long m = Long.parseLong(sc.nextLine());
                    System.out.print("Book id: ");
                    long b = Long.parseLong(sc.nextLine());
                    loanService.returnBook(m, b);
                    System.out.println("Book returned.");
                } else if (choice == 3) {
                    System.out.print("Member id: ");
                    long m = Long.parseLong(sc.nextLine());
                    List<Loan> loans = loanService.getCurrentLoansForMember(m);
                    for (Loan l : loans) {
                        System.out.println("Loan " + l.getId() +
                                " bookId=" + l.getBookId() +
                                " due=" + l.getDueDate());
                    }
                } else if (choice == 4) {
                    List<Book> books = loanService.listAvailableBooks();
                    for (Book b : books) {
                        System.out.println(b.getId() + " - " + b.getTitle() + " by " + b.getAuthor());
                }
                } else if (choice == 5) {
                    System.out.print("Member id: ");
                    long m = Long.parseLong(sc.nextLine());
                    LoanReport report = loanService.buildLoanReport(m);
                    System.out.println("Report for member " + report.getMemberId() + " (" + report.getMemberName() + ")");
                    System.out.println("Generated: " + report.getGeneratedAt());
                    System.out.println("Active loans: " + report.getActiveLoans().size());
                }
                else if (choice == 0) {
                    break;
                }
            } catch (BookAlreadyOnLoanException |
                     LoanOverdueException |
                     MemberNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
