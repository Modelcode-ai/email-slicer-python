package com.emailslicer;

/**
 * Holds the result of slicing an email address into its username and domain parts.
 *
 * @param username the part of the email before the {@code @} symbol
 * @param domain   the part of the email after the {@code @} symbol
 */
public record EmailParts(String username, String domain) {}
