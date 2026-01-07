package com.example.emailslicer;

/**
 * A record representing the parsed components of an email address.
 *
 * @param username the username part (before the '@' symbol)
 * @param domain the domain part (after the '@' symbol)
 */
public record EmailResult(String username, String domain) { }
