package com.emailslicer;

/**
 * Custom unchecked exception thrown when email validation fails.
 * This exception is used to signal various email validation errors such as:
 * - Missing '@' symbol
 * - Multiple '@' symbols
 * - Empty username or domain parts
 * - Null or empty input
 */
public class EmailValidationException extends RuntimeException {

    /**
     * Constructs a new EmailValidationException with the specified detail message.
     *
     * @param message the detail message explaining the validation failure
     */
    public EmailValidationException(String message) {
        super(message);
    }
}
