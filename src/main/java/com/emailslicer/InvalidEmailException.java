package com.emailslicer;

/**
 * Thrown when an input string does not contain a valid email address
 * (i.e., it lacks an {@code @} character after trimming).
 */
public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String message) {
        super(message);
    }
}
