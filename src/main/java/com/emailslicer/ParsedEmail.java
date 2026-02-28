package com.emailslicer;

/**
 * Immutable value type representing a parsed email address,
 * holding the username (before the first {@code @}) and the domain (after the first {@code @}).
 */
public record ParsedEmail(String username, String domain) { }
