package com.emailslicer;

/**
 * Immutable data carrier for parsed email components.
 *
 * @param username the part of the email address before the first {@code @}
 * @param domain   the part of the email address after the first {@code @}
 */
public record EmailParts(String username, String domain) {}
