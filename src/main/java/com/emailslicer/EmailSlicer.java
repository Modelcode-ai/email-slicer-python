package com.emailslicer;

import java.util.Optional;
import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 *
 * This class handles user interaction, prompting for email input and
 * displaying parsed results or error messages.
 */
public final class EmailSlicer {

    /** Prompt message for user input. */
    private static final String PROMPT = "Please enter your Email Id:";
    /** Output prefix for username display. */
    private static final String USERNAME_OUTPUT = "Your username is: ";
    /** Output prefix for domain display. */
    private static final String DOMAIN_OUTPUT = "Your domain is: ";
    /** Error message for invalid email input. */
    private static final String INVALID_EMAIL =
            "Please enter a valid Email Id.";

    /**
     * Private constructor to prevent instantiation.
     */
    private EmailSlicer() {
        throw new UnsupportedOperationException(
                "Utility class cannot be instantiated");
    }

    /**
     * Main entry point for the Email Slicer application.
     *
     * @param args command line arguments (currently unused)
     */
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user
        System.out.println(PROMPT);

        // Read input
        String emailInput = scanner.nextLine();

        // Trim the input
        String trimmedEmail = emailInput.trim();

        // Parse the email
        Optional<EmailParser.EmailParts> result =
                EmailParser.parse(trimmedEmail);

        // Display results
        if (result.isPresent()) {
            EmailParser.EmailParts parts = result.get();
            System.out.println(USERNAME_OUTPUT + parts.getUsername());
            System.out.println(DOMAIN_OUTPUT + parts.getDomain());
        } else {
            System.out.println(INVALID_EMAIL);
        }

        scanner.close();
    }
}
