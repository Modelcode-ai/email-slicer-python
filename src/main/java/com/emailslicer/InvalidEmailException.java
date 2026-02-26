package com.emailslicer;

/**
 * Checked exception thrown by {@link EmailSlicer#slice(String)} when the
 * provided input is not a valid email address (null, empty/whitespace-only,
 * or missing the {@code @} character).
 */
public class InvalidEmailException extends Exception {

    public InvalidEmailException(String message) {
        super(message);
    }
}
