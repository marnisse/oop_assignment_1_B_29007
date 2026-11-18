package com.auca.exceptions;

/**
 * Thrown when a library book has no available copies.
 */
public class BookUnavailableException extends Exception {
    public BookUnavailableException(String message) {
        super(message);
    }
}
