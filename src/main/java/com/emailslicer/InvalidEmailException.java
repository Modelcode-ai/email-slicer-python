package com.emailslicer;

/**
 * A checked exception thrown when an email address fails validation.
 *
 * <p>This exception is thrown by {@link EmailSlicer#parse(String)}
 * when the provided email address does not meet the required format
 * criteria, such as:</p>
 * <ul>
 *   <li>The input is null or empty</li>
 *   <li>The input does not contain an '@' symbol</li>
 *   <li>The input contains multiple '@' symbols</li>
 *   <li>The '@' symbol is at the beginning (empty username)</li>
 *   <li>The '@' symbol is at the end (empty domain)</li>
 * </ul>
 *
 * <p>As a checked exception, this forces callers to explicitly handle
 * validation failures, promoting clear error handling patterns in
 * client code.</p>
 *
 * @since 1.0.0
 */
public class InvalidEmailException extends Exception {

    /**
     * Constructs a new InvalidEmailException with the specified
     * detail message.
     *
     * @param message the detail message describing why the email
     *                is invalid
     */
    public InvalidEmailException(final String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidEmailException with the specified
     * detail message and cause.
     *
     * <p>This constructor is provided for future extensibility,
     * allowing the exception to wrap underlying causes if needed.</p>
     *
     * @param message the detail message describing why the email
     *                is invalid
     * @param cause the underlying cause of this exception (or null
     *              if none)
     */
    public InvalidEmailException(final String message,
                                  final Throwable cause) {
        super(message, cause);
    }
}
