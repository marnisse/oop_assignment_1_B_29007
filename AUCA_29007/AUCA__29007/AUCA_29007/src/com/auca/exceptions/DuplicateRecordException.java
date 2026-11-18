package com.auca.exceptions;

/**
 * Thrown when a unique ID / code / receipt already exists.
 */
public class DuplicateRecordException extends Exception {
    public DuplicateRecordException(String message) {
        super(message);
    }
}
