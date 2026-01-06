package com.example.emailslicer;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Email Slicer CLI application.
 * Takes an email address as input and outputs the username and domain parts.
 */
public final class EmailSlicerApp {

    /**
     * Prompt message displayed to the user.
     */
    private static final String PROMPT_MESSAGE =
            "Please enter your Email Id:";

    /**
     * Error message displayed for invalid email input.
     */
    private static final String INVALID_EMAIL_MESSAGE =
            "Please enter a valid Email Id.";

    /**
     * Format string for username output.
     */
    private static final String USERNAME_OUTPUT_FORMAT =
            "Your username is:  %s";

    /**
     * Format string for domain output.
     */
    private static final String DOMAIN_OUTPUT_FORMAT =
            "Your domain is:  %s";

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private EmailSlicerApp() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Main entry point for the application.
     * Delegates to runOnce and exits with the returned exit code.
     *
     * @param args command line arguments (not used)
     */
    public static void main(final String[] args) {
        int exitCode = runOnce(System.in, System.out);
        System.exit(exitCode);
    }

    /**
     * Performs one read-parse-print cycle.
     * Package-private to enable testing without System.exit complications.
     *
     * @param in the input stream to read from
     * @param out the output stream to write to
     * @return 0 for success, 1 for invalid input
     */
    static int runOnce(final InputStream in, final PrintStream out) {
        try (var scanner = new Scanner(in)) {
            out.println(PROMPT_MESSAGE);
            String rawInput = scanner.nextLine();

            try {
                EmailResult result = parseEmail(rawInput);
                out.println(String.format(USERNAME_OUTPUT_FORMAT,
                        result.username()));
                out.println(String.format(DOMAIN_OUTPUT_FORMAT,
                        result.domain()));
                return 0;
            } catch (IllegalArgumentException e) {
                out.println(INVALID_EMAIL_MESSAGE);
                return 1;
            }
        }
    }

    /**
     * Parses an email address into username and domain components.
     *
     * @param rawInput the raw input string to parse
     * @return an EmailResult containing the username and domain
     * @throws IllegalArgumentException if the input is invalid (null, empty,
     *                                  missing @, empty username, or empty
     *                                  domain)
     */
    private static EmailResult parseEmail(final String rawInput) {
        // Handle null input by converting to empty string
        String email = rawInput == null ? "" : rawInput.strip();

        // Check for empty input
        if (email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        // Check for at least one @ symbol (matches Python behavior:
        // email.find("@") != -1)
        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException(
                    "Email must contain @ symbol");
        }

        // Extract username and domain (split on first @ occurrence)
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        // Validate that both username and domain are non-empty
        if (username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (domain.isEmpty()) {
            throw new IllegalArgumentException("Domain cannot be empty");
        }

        return new EmailResult(username, domain);
    }
}
