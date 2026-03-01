package com.emailslicer;

/**
 * Thrown when an email address cannot be parsed because it is invalid.
 */
public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String message) {
        super(message);
    }
}
