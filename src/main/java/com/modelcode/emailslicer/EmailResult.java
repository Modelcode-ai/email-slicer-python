package com.modelcode.emailslicer;

/**
 * Immutable data holder for parsed email components.
 *
 * @param username the substring before the @ symbol
 * @param domain the substring after the @ symbol
 */
public record EmailResult(String username, String domain) {}
