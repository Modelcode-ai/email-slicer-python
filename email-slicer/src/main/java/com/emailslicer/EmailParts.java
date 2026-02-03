package com.emailslicer;

/**
 * Immutable value object holding the parsed components of an email address.
 */
public final class EmailParts {
    private final String username;
    private final String domain;

    public EmailParts(String username, String domain) {
        this.username = username;
        this.domain = domain;
    }

    public String getUsername() {
        return username;
    }

    public String getDomain() {
        return domain;
    }
}
