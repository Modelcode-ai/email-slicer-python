package com.emailslicer;

/**
 * Immutable data transfer object representing the parsed components of an email address.
 *
 * @param username the portion of the email address before the '@' symbol (can be empty string)
 * @param domain the portion of the email address after the '@' symbol (can be empty string)
 */
public record EmailParts(String username, String domain) {

    /**
     * Compact constructor that validates non-null fields.
     *
     * @throws NullPointerException if username or domain is null
     */
    public EmailParts {
        if (username == null || domain == null) {
            throw new NullPointerException("Username and domain must not be null");
        }
    }
}
