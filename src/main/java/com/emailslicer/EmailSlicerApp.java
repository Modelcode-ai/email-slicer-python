package com.emailslicer;

import java.util.Scanner;

/**
 * Main CLI application for the Email Slicer tool.
 * <p>
 * Prompts the user for an email address, parses it into username and domain
 * components, and displays the results.
 */
public final class EmailSlicerApp {

    private EmailSlicerApp() {
        // Utility class - prevent instantiation
    }

    /**
     * Main entry point for the Email Slicer CLI application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(final String[] args) {
        System.out.println(Messages.PROMPT_EMAIL);

        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine().trim();

            try {
                EmailParser.ParsedEmail parsed = EmailParser.parse(input);
                System.out.println(
                        Messages.OUTPUT_USERNAME_PREFIX + parsed.getUsername());
                System.out.println(
                        Messages.OUTPUT_DOMAIN_PREFIX + parsed.getDomain());
            } catch (IllegalArgumentException e) {
                System.out.println(Messages.ERROR_INVALID_EMAIL);
                System.exit(1);
            }
        }
    }
}
