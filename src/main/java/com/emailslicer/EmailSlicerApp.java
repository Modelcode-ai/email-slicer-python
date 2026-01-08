package com.emailslicer;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Command-line application for parsing email addresses.
 * This class serves as the entry point and orchestrates the CLI flow:
 * prompt user for input, parse the email, and display results or errors.
 */
public class EmailSlicerApp {

    /**
     * Runs the email slicer CLI application with configurable I/O streams.
     * This method handles the complete flow: prompt, read, parse, and output.
     *
     * @param in the input stream to read email from
     * @param out the output stream to write results to
     * @return exit code (0 for success, 1 for invalid email)
     */
    public int run(final InputStream in, final PrintStream out) {
        try (Scanner scanner = new Scanner(in)) {
            out.println(Messages.PROMPT_EMAIL);
            String input = scanner.nextLine();

            EmailParser parser = new EmailParser();
            try {
                EmailParts parts = parser.parseEmail(input);
                out.println(Messages.OUTPUT_USERNAME + parts.username());
                out.println(Messages.OUTPUT_DOMAIN + parts.domain());
                return 0;
            } catch (IllegalArgumentException e) {
                out.println(Messages.ERROR_INVALID_EMAIL);
                return 1;
            }
        }
    }

    /**
     * Main entry point for the CLI application.
     * Invokes the run method with standard I/O and exits with the
     * returned code.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(final String[] args) {
        int exitCode = new EmailSlicerApp().run(System.in, System.out);
        System.exit(exitCode);
    }
}
