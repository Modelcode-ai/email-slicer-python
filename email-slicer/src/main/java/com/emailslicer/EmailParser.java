package com.emailslicer;

import java.util.Optional;

/**
 * Parses and validates email addresses.
 *
 * Validation rules (stricter than original Python implementation):
 * - Input must contain exactly one '@' character
 * - Username (substring before '@') must be non-empty
 * - Domain (substring after '@') must be non-empty
 */
public class EmailParser {

    /**
     * Parses a raw email string into its username and domain components.
     *
     * @param rawEmail the email string to parse (may be null)
     * @return Optional containing EmailParts if valid, empty Optional if invalid
     */
    public Optional<EmailParts> parse(String rawEmail) {
        if (rawEmail == null) {
            return Optional.empty();
        }

        String email = rawEmail.trim();

        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            return Optional.empty();
        }

        // Check for exactly one '@' by verifying no second '@' exists after the first
        if (email.indexOf('@', atIndex + 1) != -1) {
            return Optional.empty();
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        // Validate non-empty username and domain
        if (username.isEmpty() || domain.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new EmailParts(username, domain));
    }
}
