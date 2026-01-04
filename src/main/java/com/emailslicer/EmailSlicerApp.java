package com.emailslicer;

import java.util.Scanner;

/**
 * Main entry point for the Email Slicer CLI application.
 * Handles stdin/stdout interaction and delegates to EmailParser.
 *
 * <p>Usage:</p>
 * <pre>
 *   java -jar email-slicer-1.0.0.jar
 * </pre>
 *
 * <p>The application reads one email address from stdin and outputs:</p>
 * <ul>
 *   <li>The username and domain on separate lines (for valid email), or</li>
 *   <li>An error message (for invalid email)</li>
 * </ul>
 *
 * <p>Exit codes:</p>
 * <ul>
 *   <li>0 - Success (valid email parsed)</li>
 *   <li>1 - Failure (invalid email input)</li>
 * </ul>
 */
public final class EmailSlicerApp {

    private EmailSlicerApp() {
        // Prevent instantiation - use main method
    }

    /**
     * Main entry point for the application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(final String[] args) {
        System.out.println(Messages.PROMPT_EMAIL);

        try (Scanner scanner = new Scanner(System.in)) {
            String emailInput = scanner.nextLine();

            ParsedEmail result = EmailParser.parse(emailInput);

            if (result != null) {
                System.out.println(
                    Messages.USERNAME_PREFIX + result.username());
                System.out.println(Messages.DOMAIN_PREFIX + result.domain());
                System.exit(0);
            } else {
                System.out.println(Messages.INVALID_EMAIL);
                System.exit(1);
            }
        }
    }
}
