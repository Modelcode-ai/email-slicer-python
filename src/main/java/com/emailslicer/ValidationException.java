package com.emailslicer;

/**
 * Exception thrown when email validation fails.
 * This is an unchecked exception that carries a human-readable error message
 * describing the specific validation failure.
 */
public class ValidationException extends RuntimeException {

    /**
     * Constructs a new ValidationException with the specified detail
     * message.
     *
     * @param message the detail message describing the validation failure
     */
    public ValidationException(final String message) {
        super(message);
    }

    /**
     * Constructs a new ValidationException with the specified detail
     * message and cause.
     *
     * @param message the detail message describing the validation failure
     * @param cause the cause of the validation failure
     */
    public ValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
