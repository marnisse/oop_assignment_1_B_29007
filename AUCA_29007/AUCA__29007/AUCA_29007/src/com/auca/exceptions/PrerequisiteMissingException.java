package com.auca.exceptions;

/**
 * Thrown when a required related entity is missing.
 */
public class PrerequisiteMissingException extends Exception {
    public PrerequisiteMissingException(String message) {
        super(message);
    }
}
