package com.auca.bookloan;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.student.Student;
import com.auca.librarybook.LibraryBook;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;


public class BookLoan implements Identifiable, Registrable {
    private String loanId;
    private Student student;
    private LibraryBook book;
    private LocalDate borrowedDate;
    private LocalDate dueDate;
    private LocalDate returnedDate; // null until returned

    public BookLoan(String loanId, Student student, LibraryBook book,
                    LocalDate borrowedDate, LocalDate dueDate, LocalDate returnedDate) {
        this.loanId = loanId;
        this.student = student;
        this.book = book;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
    }

    @Override
    public String getId() { return loanId; }

    @Override
    public String getDisplayName() {
        return (student != null ? student.getDisplayName() : "N/A") +
               " borrowed " + (book != null ? book.getTitle() : "N/A");
    }

    @Override
    public boolean isActive() {
        return returnedDate == null;
    }

    public String getLoanId() { return loanId; }
    public void setLoanId(String loanId) { this.loanId = loanId; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public LibraryBook getBook() { return book; }
    public void setBook(LibraryBook book) { this.book = book; }

    public LocalDate getBorrowedDate() { return borrowedDate; }
    public void setBorrowedDate(LocalDate borrowedDate) { this.borrowedDate = borrowedDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getReturnedDate() { return returnedDate; }
    public void setReturnedDate(LocalDate returnedDate) { this.returnedDate = returnedDate; }

    @Override
    public void validate() throws ValidationException {
        if (loanId == null || loanId.trim().isEmpty()) {
            throw new ValidationException("Loan ID cannot be blank.");
        }
        if (student == null) {
            throw new ValidationException("Student is required.");
        }
        if (book == null) {
            throw new ValidationException("Book is required.");
        }
        if (borrowedDate == null) {
            throw new ValidationException("Borrowed date is required.");
        }
        if (dueDate == null || !dueDate.isAfter(borrowedDate)) {
            throw new ValidationException("Due date must be after borrowed date.");
        }
        if (returnedDate != null && returnedDate.isBefore(borrowedDate)) {
            throw new ValidationException("Returned date cannot be before borrowed date.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println("===== BOOK LOAN DETAILS =====");
        System.out.println("Loan ID          : " + loanId);
        System.out.println("Student          : " + (student != null ? student.getDisplayName() : "N/A"));
        System.out.println("Book             : " + (book != null ? book.getDisplayName() : "N/A"));
        System.out.println("Borrowed Date    : " + borrowedDate);
        System.out.println("Due Date         : " + dueDate);
        System.out.println("Returned Date    : " + (returnedDate != null ? returnedDate : "Not returned"));
        System.out.println("Status           : " + (isActive() ? "ON LOAN" : "RETURNED"));
    }

    @Override
    public String getRegistrationSummary() {
        return "[LOAN] " + loanId + " - " + getDisplayName() +
               (isActive() ? " [ON LOAN]" : " [RETURNED]");
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
