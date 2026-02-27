package com.emailslicer;

/**
 * Thrown when an email address cannot be parsed because it does not
 * contain an {@code @} character.
 */
public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String message) {
        super(message);
    }
}
