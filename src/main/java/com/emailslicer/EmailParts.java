package com.emailslicer;

/**
 * Immutable data carrier for the result of parsing an email address.
 *
 * @param username the part of the email before the {@code @} symbol
 * @param domain   the part of the email after the {@code @} symbol
 */
public record EmailParts(String username, String domain) {}
