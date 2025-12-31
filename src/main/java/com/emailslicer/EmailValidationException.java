package com.emailslicer;

/**
 * Unchecked exception thrown when email address validation fails.
 *
 * <p>This exception is used to signal invalid email inputs during parsing,
 * such as null values, empty strings, incorrect @ symbol count, or
 * empty username/domain parts. It extends RuntimeException for simplicity,
 * making it appropriate for a small CLI tool where checked exceptions
 * would add unnecessary complexity.
 *
 * <p>Each exception instance should provide a clear, user-friendly message
 * indicating what went wrong and, ideally, how to correct the input.
 */
public class EmailValidationException extends RuntimeException {

    /**
     * Constructs a new EmailValidationException with the specified
     * detail message.
     *
     * @param message the detail message explaining the validation failure
     */
    public EmailValidationException(final String message) {
        super(message);
    }

    /**
     * Constructs a new EmailValidationException with the specified
     * detail message and cause.
     *
     * <p>This constructor is useful for wrapping unexpected exceptions
     * that occur during validation or parsing.
     *
     * @param message the detail message explaining the validation failure
     * @param cause the underlying cause of the exception
     */
    public EmailValidationException(final String message,
                                    final Throwable cause) {
        super(message, cause);
    }
}
