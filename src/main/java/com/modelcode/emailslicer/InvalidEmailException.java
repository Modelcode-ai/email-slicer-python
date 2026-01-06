package com.modelcode.emailslicer;

/**
 * Exception thrown when an email address fails validation.
 *
 * <p>This unchecked exception is raised by {@link EmailParser}
 * when it encounters an email address that does not meet basic
 * structural requirements, such as:
 * <ul>
 *   <li>Missing '@' symbol</li>
 *   <li>Multiple '@' symbols</li>
 *   <li>Empty username (e.g., "@domain.com")</li>
 *   <li>Empty domain (e.g., "user@")</li>
 *   <li>Null or empty input</li>
 * </ul>
 *
 * <p>The exception message provides user-friendly feedback about
 * what validation rule failed.
 */
public class InvalidEmailException extends RuntimeException {

    /**
     * Constructs a new InvalidEmailException with the specified
     * detail message.
     *
     * @param message a user-friendly description of why the email
     *                is invalid
     */
    public InvalidEmailException(final String message) {
        super(message);
    }
}
