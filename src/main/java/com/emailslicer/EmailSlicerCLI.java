package com.emailslicer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Command-line interface for the Email Slicer application.
 *
 * <p>This class serves as the entry point for the Email Slicer tool,
 * providing a user-friendly CLI that supports multiple input modes:</p>
 * <ul>
 *   <li><b>Command-line argument:</b> Pass the email as the first
 *       argument (e.g., {@code java -jar emailslicer.jar
 *       user@example.com})</li>
 *   <li><b>Piped stdin:</b> Pipe email input without showing an
 *       interactive prompt (e.g., {@code echo "user@example.com" |
 *       java -jar emailslicer.jar})</li>
 *   <li><b>Interactive prompt:</b> Run without arguments to be
 *       prompted for input interactively</li>
 * </ul>
 *
 * <p><b>Output Format:</b></p>
 * <p>On successful parsing, the CLI prints two lines to stdout:</p>
 * <pre>
 * Your username is: &lt;username&gt;
 * Your domain is: &lt;domain&gt;
 * </pre>
 *
 * <p><b>Error Handling:</b></p>
 * <ul>
 *   <li>Invalid email addresses print an error message to stderr and
 *       exit with code 1</li>
 *   <li>Unexpected errors (I/O errors, etc.) print a generic error to
 *       stderr and exit with code 2</li>
 * </ul>
 *
 * <p>This CLI design separates I/O concerns from the core parsing
 * logic in {@link EmailSlicer}, making the application testable and
 * maintaining clear separation of responsibilities.</p>
 *
 * @since 1.0.0
 */
public final class EmailSlicerCLI {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private EmailSlicerCLI() {
        throw new UnsupportedOperationException(
                "Utility class should not be instantiated");
    }

    /**
     * Application entry point.
     *
     * <p>This method handles three input modes in the following
     * priority order:</p>
     * <ol>
     *   <li>If command-line arguments are provided, uses the first
     *       argument as the email address</li>
     *   <li>If stdin has data available (piped input), reads one line
     *       without displaying a prompt</li>
     *   <li>Otherwise, displays an interactive prompt and reads from
     *       stdin</li>
     * </ol>
     *
     * <p>The method delegates all parsing and validation logic to
     * {@link EmailSlicer} and focuses solely on user interaction and
     * error reporting.</p>
     *
     * @param args command-line arguments; if provided, args[0] is used
     *             as the email address
     */
    public static void main(final String[] args) {
        try {
            // Get email input from one of the three supported modes
            final String emailInput = getEmailInput(args);

            // Parse the email using the core EmailSlicer service
            final EmailSlicer slicer = new EmailSlicer();
            final EmailResult result = slicer.parse(emailInput);

            // Print the results to stdout
            System.out.println("Your username is: " + result.username());
            System.out.println("Your domain is: " + result.domain());

        } catch (InvalidEmailException e) {
            // Handle validation errors with a user-friendly message
            System.err.println("Invalid email address: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            // Handle unexpected I/O errors
            System.err.println("An unexpected error occurred.");
            System.exit(2);
        } catch (Exception e) {
            // Catch any other unexpected runtime errors
            System.err.println("An unexpected error occurred.");
            System.exit(2);
        }
    }

    /**
     * Retrieves the email input based on the current invocation mode.
     *
     * <p>This helper method implements the input mode detection
     * logic:</p>
     * <ol>
     *   <li>If args.length &gt; 0, return args[0] (command-line
     *       argument mode)</li>
     *   <li>If System.in.available() &gt; 0, read from stdin without
     *       a prompt (piped input mode)</li>
     *   <li>Otherwise, print the interactive prompt and read from
     *       stdin (interactive mode)</li>
     * </ol>
     *
     * @param args the command-line arguments passed to main
     * @return the email address string provided by the user
     * @throws IOException if an I/O error occurs while reading from
     *                     stdin
     */
    private static String getEmailInput(final String[] args)
            throws IOException {
        // Mode 1: Command-line argument provided
        if (args.length > 0) {
            return args[0];
        }

        // Create a BufferedReader for reading from stdin
        final BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // Mode 2: Check if stdin has data available (piped input)
        // Note: available() returns > 0 when data is piped to stdin
        if (System.in.available() > 0) {
            // Read one line without printing a prompt
            return reader.readLine();
        }

        // Mode 3: Interactive mode - print prompt and read from stdin
        System.out.print("Please enter your Email Id: ");
        return reader.readLine();
    }
}
