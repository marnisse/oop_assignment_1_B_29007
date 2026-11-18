package com.auca.exceptions;

/**
 * Thrown when a requested record cannot be found.
 */
public class RecordNotFoundException extends Exception {
    public RecordNotFoundException(String message) {
        super(message);
    }
}
