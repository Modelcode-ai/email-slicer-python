package com.emailslicer;

/**
 * Centralised store for user-facing messages.
 */
public final class Messages {

    public static final String PROMPT_MESSAGE = "Please enter your Email Id:";
    public static final String INVALID_EMAIL_MESSAGE = "Please enter a valid Email Id.";
    public static final String USERNAME_PREFIX = "Your username is:  ";
    public static final String DOMAIN_PREFIX = "Your domain is:  ";

    private Messages() {
        // Utility class — not instantiable
    }
}
