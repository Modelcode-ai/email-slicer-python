package com.emailslicer;

import java.util.Objects;
import java.util.Optional;

/**
 * Core email parsing logic that extracts the username and domain
 * from a raw email address string.
 *
 * <p>This class encapsulates the parsing rules originally implemented
 * in the Python {@code emailSlicer.py} script, with stricter validation
 * for empty username and domain parts.</p>
 */
public class EmailSlicer {

    /**
     * Immutable value object holding the parsed username and domain
     * components of an email address.
     */
    public static final class EmailParts {
        private final String username;
        private final String domain;

        public EmailParts(String username, String domain) {
            this.username = Objects.requireNonNull(username, "username must not be null");
            this.domain = Objects.requireNonNull(domain, "domain must not be null");
        }

        public String getUsername() {
            return username;
        }

        public String getDomain() {
            return domain;
        }
    }

    /**
     * Parses the given raw email string into its username and domain parts.
     *
     * <p>The input is trimmed of leading and trailing whitespace before parsing.
     * The email is split on the <b>first</b> {@code @} character; any additional
     * {@code @} characters are considered part of the domain.</p>
     *
     * <p>Returns {@link Optional#empty()} if the input is {@code null}, empty,
     * whitespace-only, missing an {@code @} separator, or has an empty username
     * or domain part.</p>
     *
     * @param rawEmail the raw email address string (may be {@code null})
     * @return an {@code Optional} containing the parsed {@link EmailParts},
     *         or empty if the input is invalid
     */
    public Optional<EmailParts> slice(String rawEmail) {
        if (rawEmail == null) {
            return Optional.empty();
        }

        String email = rawEmail.trim();
        if (email.isEmpty()) {
            return Optional.empty();
        }

        int atIndex = email.indexOf('@');
        if (atIndex <= 0 || atIndex == email.length() - 1) {
            // No '@', or '@' is first/last character -> invalid
            return Optional.empty();
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return Optional.of(new EmailParts(username, domain));
    }
}
