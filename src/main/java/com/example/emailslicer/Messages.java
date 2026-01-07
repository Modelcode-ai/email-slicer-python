package com.example.emailslicer;

/**
 * Centralized constants for all user-facing messages.
 */
public final class Messages {

    /** Prompt message for email input. */
    public static final String PROMPT_EMAIL = "Please enter your Email Id:";

    /** Prefix for username output. */
    public static final String OUTPUT_USERNAME = "Your username is: ";

    /** Prefix for domain output. */
    public static final String OUTPUT_DOMAIN = "Your domain is: ";

    /** Error message for invalid email. */
    public static final String ERROR_INVALID_EMAIL = "Please enter a valid Email Id.";

    private Messages() {
        // Prevent instantiation
    }
}
