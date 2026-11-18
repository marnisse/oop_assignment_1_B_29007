package com.auca.exceptions;

/**
 * Thrown when input or business-rule validation fails.
 */
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
