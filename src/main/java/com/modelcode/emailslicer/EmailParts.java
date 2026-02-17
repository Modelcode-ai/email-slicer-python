package com.modelcode.emailslicer;

/**
 * Immutable value object holding the parsed username and domain of an email address.
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

    @Override
    public String toString() {
        return "EmailParts{username='" + username + "', domain='" + domain + "'}";
    }
}
