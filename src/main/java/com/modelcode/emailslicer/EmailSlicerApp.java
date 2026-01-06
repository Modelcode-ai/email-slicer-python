package com.modelcode.emailslicer;

import java.util.Scanner;

/**
 * Command-line interface entry point for the Email Slicer application.
 *
 * <p>This application accepts an email address either as a command-line
 * argument or through interactive stdin input, parses it into username
 * and domain components, and displays the results.
 *
 * <p>Usage examples:
 * <pre>{@code
 * // Interactive mode
 * java EmailSlicerApp
 * Enter your email address: user@example.com
 * Username: user
 * Domain: example.com
 *
 * // Argument mode
 * java EmailSlicerApp user@example.com
 * Username: user
 * Domain: example.com
 * }</pre>
 *
 * <p>Exit codes:
 * <ul>
 *   <li>0 - Successful parsing and output</li>
 *   <li>2 - Input validation error (invalid email format)</li>
 *   <li>1 - Unexpected internal failure</li>
 * </ul>
 *
 * <p><b>Design Decision #1 - Output Message Compatibility:</b>
 * This implementation uses modernized, standardized wording
 * ("Enter your email address:", "Username:", "Domain:") rather than
 * preserving the exact Python messages ("Please enter your Email Id:",
 * "Your username is:", "Your domain is:"). This decision prioritizes
 * clarity and establishes the Java version as the canonical
 * implementation going forward. Differences are documented in the
 * README.
 *
 * <p><b>Design Decision #2 - Exit Code Strategy:</b>
 * This implementation uses distinct exit codes: 0 for success, 2 for
 * validation errors (InvalidEmailException), and 1 for unexpected
 * failures. This allows shell scripts and test harnesses to distinguish
 * between validation failures and bugs, while error messages to stderr
 * provide context for users.
 */
public final class EmailSlicerApp {

    /** Prompt message displayed in interactive mode. */
    private static final String PROMPT_MESSAGE =
            "Enter your email address: ";

    /** Format string for displaying the username. */
    private static final String USERNAME_FORMAT = "Username: %s%n";

    /** Format string for displaying the domain. */
    private static final String DOMAIN_FORMAT = "Domain: %s%n";

    /** Format string for validation error messages. */
    private static final String INVALID_EMAIL_FORMAT =
            "Invalid email address: %s%n";

    /** Error message for unexpected failures. */
    private static final String UNEXPECTED_ERROR_MESSAGE =
            "An unexpected error occurred.%n";

    /** Exit code for successful execution. */
    private static final int EXIT_SUCCESS = 0;

    /** Exit code for unexpected internal failures. */
    private static final int EXIT_UNEXPECTED_FAILURE = 1;

    /** Exit code for input validation errors. */
    private static final int EXIT_VALIDATION_ERROR = 2;

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private EmailSlicerApp() {
        throw new UnsupportedOperationException(
                "Utility class should not be instantiated");
    }

    /**
     * Main entry point for the Email Slicer CLI application.
     *
     * <p>Accepts an email address either as the first command-line
     * argument or prompts for interactive input via stdin. Parses
     * the email using {@link EmailParser} and displays the username
     * and domain, or an error message if validation fails.
     *
     * @param args command-line arguments; if provided, args[0] is
     *             treated as the email address to parse
     */
    public static void main(final String[] args) {
        // Create parser instance
        final EmailParser parser = new EmailParser();

        try {
            // Determine input mode and get email address
            final String email;
            if (args.length >= 1) {
                // Argument mode: use first argument
                email = args[0].trim();
            } else {
                // Interactive mode: prompt user
                email = promptForEmail();
            }

            // Parse the email
            final EmailParts parts = parser.parse(email);

            // Display results to stdout
            System.out.printf(USERNAME_FORMAT, parts.username());
            System.out.printf(DOMAIN_FORMAT, parts.domain());

            // Exit successfully
            System.exit(EXIT_SUCCESS);

        } catch (InvalidEmailException e) {
            // Validation error: display error message to stderr
            System.err.printf(INVALID_EMAIL_FORMAT, e.getMessage());
            System.exit(EXIT_VALIDATION_ERROR);

        } catch (Exception e) {
            // Unexpected error: display generic message to stderr
            System.err.printf(UNEXPECTED_ERROR_MESSAGE);
            System.exit(EXIT_UNEXPECTED_FAILURE);
        }
    }

    /**
     * Prompts the user to enter an email address via stdin and
     * reads one line of input.
     *
     * <p>This method displays the prompt to stdout, then reads and
     * trims the user's input. It's used when the application is run
     * without command-line arguments.
     *
     * @return the trimmed email address entered by the user
     */
    private static String promptForEmail() {
        System.out.print(PROMPT_MESSAGE);
        try (Scanner scanner = new Scanner(System.in)) {
            return scanner.nextLine().trim();
        }
    }
}
