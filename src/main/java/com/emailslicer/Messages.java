package com.emailslicer;

/**
 * Holds user-visible string constants for the Email Slicer CLI application.
 */
public final class Messages {

    private Messages() {
        // Utility class - prevent instantiation
    }

    /** Prompt message asking user to enter their email. */
    public static final String PROMPT_EMAIL = "Please enter your Email Id:";

    /** Prefix for displaying the username output. */
    public static final String OUTPUT_USERNAME_PREFIX = "Your username is:  ";

    /** Prefix for displaying the domain output. */
    public static final String OUTPUT_DOMAIN_PREFIX = "Your domain is:  ";

    /** Error message for invalid email input. */
    public static final String ERROR_INVALID_EMAIL =
            "Please enter a valid Email Id.";
}
