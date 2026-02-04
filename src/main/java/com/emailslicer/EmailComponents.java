package com.emailslicer;

/**
 * Represents the parsed components of an email address.
 *
 * @param username the portion of the email before the @ symbol
 * @param domain the portion of the email after the @ symbol
 */
public record EmailComponents(String username, String domain) {}
