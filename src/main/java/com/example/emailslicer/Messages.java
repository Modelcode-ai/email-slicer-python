package com.example.emailslicer;

/**
 * Centralized constants for all user-facing messages.
 *
 * <p>This class holds all prompts, outputs, and error messages displayed
 * to the user. Message text exactly matches the Python version to ensure
 * behavioral parity.
 */
public final class Messages {

    /**
     * Prompt message displayed to request user's email input.
     */
    public static final String PROMPT_EMAIL = "Please enter your Email Id:";

    /**
     * Output label for username (includes two trailing spaces to match
     * Python's print behavior with comma-separated arguments).
     */
    public static final String OUTPUT_USERNAME = "Your username is:  ";

    /**
     * Output label for domain (includes two trailing spaces to match
     * Python's print behavior with comma-separated arguments).
     */
    public static final String OUTPUT_DOMAIN = "Your domain is:  ";

    /**
     * Error message displayed for invalid email input.
     */
    public static final String ERROR_INVALID_EMAIL =
            "Please enter a valid Email Id.";

    /**
     * Private constructor to prevent instantiation.
     */
    private Messages() {
        throw new AssertionError("Messages class should not be instantiated");
    }
}
