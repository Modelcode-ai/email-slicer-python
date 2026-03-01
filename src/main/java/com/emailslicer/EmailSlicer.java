package com.emailslicer;

/**
 * Parses an email address string into its username and domain components.
 * <p>
 * The slicing logic mirrors the original Python Email Slicer:
 * <ul>
 *   <li>Leading and trailing whitespace is trimmed.</li>
 *   <li>The input is split on the first {@code @} character.</li>
 *   <li>Both the username (before {@code @}) and domain (after {@code @}) must be non-empty.</li>
 * </ul>
 */
public class EmailSlicer {

    /**
     * Slices the given email string into username and domain parts.
     *
     * @param email the email address to parse
     * @return an {@link EmailParts} record containing the username and domain
     * @throws InvalidEmailException if the input is null, empty, missing {@code @},
     *                               or has an empty username or domain
     */
    public EmailParts slice(String email) {
        if (email == null) {
            throw new InvalidEmailException("Email must not be null.");
        }

        String trimmed = email.strip();

        if (trimmed.isEmpty()) {
            throw new InvalidEmailException("Email must not be empty.");
        }

        int atIndex = trimmed.indexOf('@');

        if (atIndex == -1) {
            throw new InvalidEmailException("Please enter a valid Email Id.");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        if (username.isEmpty()) {
            throw new InvalidEmailException("Username must not be empty.");
        }

        if (domain.isEmpty()) {
            throw new InvalidEmailException("Domain must not be empty.");
        }

        return new EmailParts(username, domain);
    }
}
