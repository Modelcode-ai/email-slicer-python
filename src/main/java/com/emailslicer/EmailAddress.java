package com.emailslicer;

import java.util.Objects;

/**
 * Immutable value object representing a parsed email address.
 *
 * <p>An EmailAddress consists of two parts:
 * <ul>
 *   <li>username (local part) - the portion before the @ symbol</li>
 *   <li>domain - the portion after the @ symbol</li>
 * </ul>
 *
 * <p>This class assumes the input has already been validated by
 * {@link EmailSlicer}. It does not perform any validation itself.
 */
public class EmailAddress {

    /** The username (local part) of the email address. */
    private final String username;
    /** The domain part of the email address. */
    private final String domain;

    /**
     * Constructs an EmailAddress with the specified username and domain.
     *
     * @param username the local part of the email address (before @)
     * @param domain the domain part of the email address (after @)
     */
    public EmailAddress(final String username, final String domain) {
        this.username = username;
        this.domain = domain;
    }

    /**
     * Returns the username (local part) of the email address.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the domain part of the email address.
     *
     * @return the domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Returns the full email address in the format "username@domain".
     *
     * @return the email address string
     */
    @Override
    public String toString() {
        return username + "@" + domain;
    }

    /**
     * Compares this EmailAddress with another object for equality.
     * Two EmailAddress objects are equal if both their username
     * and domain are equal.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmailAddress that = (EmailAddress) o;
        return Objects.equals(username, that.username)
               && Objects.equals(domain, that.domain);
    }

    /**
     * Returns a hash code value for this EmailAddress.
     * The hash code is computed based on both username and domain fields.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, domain);
    }
}
