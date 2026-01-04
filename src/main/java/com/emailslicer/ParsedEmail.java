package com.emailslicer;

/**
 * An immutable data carrier for parsed email results.
 * Contains the username and domain components extracted from a valid email.
 *
 * @param username the part of the email before the @ symbol
 * @param domain the part of the email after the @ symbol
 */
public record ParsedEmail(String username, String domain) { }
