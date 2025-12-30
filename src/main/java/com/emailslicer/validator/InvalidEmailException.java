package com.emailslicer.validator;

/**
 * Custom runtime exception thrown when email validation fails.
 * Provides specific error messages for different validation failure scenarios.
 */
public class InvalidEmailException extends RuntimeException {

    /**
     * Constructs a new InvalidEmailException with the specified
     * detail message.
     *
     * @param message the detail message explaining why validation failed
     */
    public InvalidEmailException(final String message) {
        super(message);
    }
}
