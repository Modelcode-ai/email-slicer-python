package com.emailslicer;

/**
 * Constants for user-facing messages in the Email Slicer CLI application.
 */
public final class Messages {

    private Messages() {
        // Utility class - prevent instantiation
    }

    /**
     * Prompt displayed to the user when requesting email input.
     */
    public static final String PROMPT_EMAIL = "Please enter your Email Id:";

    /**
     * Prefix for the username output line.
     */
    public static final String OUTPUT_USERNAME_PREFIX = "Your username is:  ";

    /**
     * Prefix for the domain output line.
     */
    public static final String OUTPUT_DOMAIN_PREFIX = "Your domain is:  ";

    /**
     * Error message displayed when the user enters an invalid email.
     */
    public static final String ERROR_INVALID_EMAIL =
        "Please enter a valid Email Id.";
}
