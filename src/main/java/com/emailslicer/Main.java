package com.emailslicer;

import java.util.Scanner;

/**
 * Main CLI entry point for the Email Slicer application.
 *
 * <p>This class provides a command-line interface for parsing email
 * addresses into username and domain components. It supports two modes:
 * <ul>
 *   <li>Command-line argument mode: Pass email as first argument</li>
 *   <li>Interactive mode: Prompt user for email input</li>
 * </ul>
 *
 * <p>The application outputs the parsed username and domain on success,
 * or displays an error message and exits with code 1 on validation failure.
 */
public final class Main {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private Main() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Main entry point for the Email Slicer CLI application.
     *
     * <p>Usage:
     * <ul>
     *   <li>Interactive mode: {@code java -jar email-slicer-java.jar}</li>
     *   <li>Command-line mode:
     *   {@code java -jar email-slicer-java.jar user@example.com}</li>
     * </ul>
     *
     * @param args command-line arguments; if provided, args[0] is treated
     *             as the email address to parse
     */
    public static void main(final String[] args) {
        String emailInput;

        // Determine input mode based on presence of command-line arguments
        if (args.length >= 1) {
            // Command-line argument mode
            emailInput = args[0];
        } else {
            // Interactive mode
            emailInput = promptForEmail();
        }

        // Parse the email and display results or error
        processEmail(emailInput);
    }

    /**
     * Prompts the user for email input in interactive mode.
     *
     * @return the email address entered by the user
     */
    private static String promptForEmail() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Please enter your Email Id:");
            return scanner.nextLine();
        }
    }

    /**
     * Processes the email input by parsing it and displaying the results.
     *
     * <p>On success, outputs the username and domain to standard output
     * and exits with code 0. On validation failure, outputs an error
     * message to standard error and exits with code 1.
     *
     * @param emailInput the email address to parse
     */
    private static void processEmail(final String emailInput) {
        EmailSlicer slicer = new EmailSlicer();

        try {
            // Parse the email address
            EmailAddress emailAddress = slicer.parse(emailInput);

            // Display successful results
            System.out.println("Username: " + emailAddress.getUsername());
            System.out.println("Domain: " + emailAddress.getDomain());

            // Exit with success code
            System.exit(0);
        } catch (EmailValidationException e) {
            // Display error message
            System.err.println("Error: " + e.getMessage());

            // Exit with failure code
            System.exit(1);
        }
    }
}
