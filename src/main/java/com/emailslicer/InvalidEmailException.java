package com.emailslicer;

/**
 * Thrown when an email string fails validation in {@link EmailSlicer#parse(String)}.
 *
 * <p>This is an unchecked exception; callers are not required to catch it but
 * should handle it at the CLI boundary to present a user-friendly error message.</p>
 */
public class InvalidEmailException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidEmailException} with the specified detail message.
     *
     * @param message a descriptive message about the validation failure
     */
    public InvalidEmailException(String message) {
        super(message);
    }
}
