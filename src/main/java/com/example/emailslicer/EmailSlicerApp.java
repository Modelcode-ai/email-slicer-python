package com.example.emailslicer;

import java.util.Scanner;

/**
 * Command-line interface for the Email Slicer application.
 *
 * <p>This class provides the main entry point for the email parsing CLI tool.
 * It reads an email address from standard input, validates it, and displays
 * the parsed username and domain components.
 */
public final class EmailSlicerApp {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private EmailSlicerApp() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Main entry point for the Email Slicer CLI application.
     *
     * <p>The application flow:
     * <ol>
     *   <li>Displays a prompt for the user to enter their email</li>
     *   <li>Reads input from standard input</li>
     *   <li>Validates the email (checking for '@' presence)</li>
     *   <li>If valid: parses and displays username and domain</li>
     *   <li>If invalid: displays error message and exits with code 1</li>
     * </ol>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(Messages.PROMPT_EMAIL);

        String input = scanner.nextLine();

        EmailSlicer slicer = new EmailSlicer();
        if (!slicer.isValidEmail(input)) {
            System.out.println(Messages.ERROR_INVALID_EMAIL);
            System.exit(1);
        }

        EmailResult result = slicer.parseEmail(input);

        System.out.println(Messages.OUTPUT_USERNAME + result.username());
        System.out.println(Messages.OUTPUT_DOMAIN + result.domain());
    }
}
