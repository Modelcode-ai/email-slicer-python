package com.emailslicer;

/**
 * Immutable value type holding the username and domain components
 * extracted from an email address.
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailParts that = (EmailParts) o;
        return username.equals(that.username) && domain.equals(that.domain);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + domain.hashCode();
        return result;
    }
}
