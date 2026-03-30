package com.emailslicer;

/**
 * Centralised store for user-facing messages.
 * <p>
 * Keeping all messages in one place makes them easy to find, review,
 * and (in the future) localise.
 */
public final class Messages {

    private Messages() {
        // Constants-only class — prevent instantiation
    }

    /** Prompt shown before reading the email address from stdin. */
    public static final String PROMPT_EMAIL = "Please enter your Email Id:";

    /** Error message when the supplied email is invalid. */
    public static final String INVALID_EMAIL = "Please enter a valid Email Id.";

    /** Format string for displaying the username. */
    public static final String USERNAME_FORMAT = "Your username is:  %s";

    /** Format string for displaying the domain. */
    public static final String DOMAIN_FORMAT = "Your domain is:  %s";
}
