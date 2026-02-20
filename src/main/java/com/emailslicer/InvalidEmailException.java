package com.emailslicer;

/**
 * Unchecked exception thrown by {@link EmailSlicer} when an email address fails
 * validation. This covers both null input and input that does not contain an
 * {@code @} character.
 */
public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String message) {
        super(message);
    }
}
