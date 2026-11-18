package com.auca.librarybook;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;

/**
 * LibraryBook entity.
 */
public class LibraryBook implements Identifiable, Registrable {
    private String bookId;
    private String isbn;
    private String title;
    private String author;
    private int totalCopies;
    private int availableCopies;

    public LibraryBook(String bookId, String isbn, String title, String author,
                       int totalCopies, int availableCopies) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    @Override
    public String getId() { return bookId; }

    @Override
    public String getDisplayName() {
        return title + " by " + author;
    }

    @Override
    public boolean isActive() {
        return availableCopies > 0;
    }

    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }

    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }

    public void decrementAvailable() {
        if (availableCopies > 0) availableCopies--;
    }

    public void incrementAvailable() {
        if (availableCopies < totalCopies) availableCopies++;
    }

    @Override
    public void validate() throws ValidationException {
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new ValidationException("Book ID cannot be blank.");
        }
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new ValidationException("ISBN is required.");
        }
        if (title == null || title.trim().length() < MIN_NAME_LENGTH) {
            throw new ValidationException("Title is required.");
        }
        if (author == null || author.trim().length() < MIN_NAME_LENGTH) {
            throw new ValidationException("Author is required.");
        }
        if (totalCopies < 0) {
            throw new ValidationException("Total copies cannot be negative.");
        }
        if (availableCopies < 0 || availableCopies > totalCopies) {
            throw new ValidationException("Available copies must be between 0 and total copies.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println("===== LIBRARY BOOK DETAILS =====");
        System.out.println("Book ID          : " + bookId);
        System.out.println("ISBN             : " + isbn);
        System.out.println("Title            : " + title);
        System.out.println("Author           : " + author);
        System.out.println("Total Copies     : " + totalCopies);
        System.out.println("Available Copies : " + availableCopies);
    }

    @Override
    public String getRegistrationSummary() {
        return "[BK] " + bookId + " - " + title + " (" + availableCopies + "/" + totalCopies + " avail)";
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
