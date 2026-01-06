package com.example.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 * <p>
 * This class provides the interactive command-line interface for
 * parsing email addresses. It prompts the user for an email address,
 * delegates parsing to {@link EmailParser}, and displays the results
 * or error messages.
 * </p>
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Utility class, prevent instantiation
    }

    /**
     * Main entry point for the Email Slicer application.
     * <p>
     * Behavior:
     * <ul>
     *   <li>Prompts user for email input via stdin</li>
     *   <li>Reads a single line of input</li>
     *   <li>Attempts to parse the email using {@link EmailParser}</li>
     *   <li>On success: prints username and domain, exits with code 0</li>
     *   <li>On failure: prints error message, exits with code 1</li>
     * </ul>
     *
     * @param args command-line arguments (currently unused)
     */
    public static void main(final String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(Messages.PROMPT);
            String input = scanner.nextLine();

            try {
                EmailResult result = EmailParser.parse(input);
                System.out.println(
                        Messages.USERNAME_OUTPUT + result.username());
                System.out.println(
                        Messages.DOMAIN_OUTPUT + result.domain());
            } catch (IllegalArgumentException ex) {
                System.out.println(Messages.INVALID_EMAIL);
                System.exit(1);
            }
        }
        // Normal termination uses exit code 0 implicitly
    }
}
