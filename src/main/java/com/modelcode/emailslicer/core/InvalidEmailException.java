package com.modelcode.emailslicer.core;

/**
 * Exception thrown when email validation fails.
 * This is a checked exception that signals validation failures in the
 * email parsing logic.
 *
 * <p>This exception is used to indicate user input errors such as:</p>
 * <ul>
 *   <li>Empty or whitespace-only input</li>
 *   <li>Missing '@' character</li>
 *   <li>Multiple '@' characters</li>
 *   <li>Empty username or domain parts</li>
 * </ul>
 */
public class InvalidEmailException extends Exception {

    /**
     * Constructs a new InvalidEmailException with the specified detail
     * message.
     *
     * @param message the detail message explaining why the email is
     *                invalid
     */
    public InvalidEmailException(final String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidEmailException with the specified detail
     * message and cause.
     * This constructor is useful for exception chaining when wrapping
     * other exceptions.
     *
     * @param message the detail message explaining why the email is
     *                invalid
     * @param cause the underlying cause of this exception
     */
    public InvalidEmailException(final String message,
                                  final Throwable cause) {
        super(message, cause);
    }
}
