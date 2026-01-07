package com.example.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for Email Slicer application.
 * Supports both interactive mode (prompts for input) and
 * non-interactive mode (reads from command-line arguments).
 */
public final class EmailSlicerApp {

    private EmailSlicerApp() {
        // Prevent instantiation
    }

    /**
     * Main entry point for the Email Slicer application.
     * If command-line arguments are provided, treats args[0] as the email address (non-interactive mode).
     * Otherwise, prompts the user for input (interactive mode).
     *
     * @param args command line arguments (optional email address)
     */
    public static void main(final String[] args) {
        final String email;

        if (args.length >= 1) {
            // Non-interactive mode: use command-line argument
            email = args[0];
        } else {
            // Interactive mode: prompt for input
            System.out.println(Messages.PROMPT_EMAIL);
            try (Scanner scanner = new Scanner(System.in)) {
                email = scanner.nextLine();
            }
        }

        processEmail(email);
    }

    /**
     * Processes the email address: validates it and prints the username and domain,
     * or prints an error message if invalid.
     *
     * @param email the email address to process
     */
    private static void processEmail(final String email) {
        try {
            final String[] parts = EmailParser.splitEmail(email);
            final String username = parts[0];
            final String domain = parts[1];

            System.out.println(Messages.OUTPUT_USERNAME + username);
            System.out.println(Messages.OUTPUT_DOMAIN + domain);
            System.exit(0);
        } catch (IllegalArgumentException e) {
            System.out.println(Messages.ERROR_INVALID_EMAIL);
            System.exit(1);
        }
    }
}
