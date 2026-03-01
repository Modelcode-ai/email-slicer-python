package com.emailslicer;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 *
 * <p>Prompts the user for an email address, parses it into username and domain
 * using {@link EmailSlicer}, and prints the result. Mirrors the behavior of the
 * original Python email slicer script.</p>
 */
public final class Main {

    static final String PROMPT = "Please enter your Email Id:";
    static final String USERNAME_FORMAT = "Your username is: %s";
    static final String DOMAIN_FORMAT = "Your domain is: %s";
    static final String INVALID_MESSAGE = "Please enter a valid Email Id.";

    private Main() {
        // Prevent instantiation
    }

    /**
     * Application entry point. Reads from stdin and writes to stdout.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        int exitCode = run(System.in, System.out);
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }

    /**
     * Core application logic, extracted for testability.
     *
     * <p>Prompts for an email address on the given output stream, reads a line
     * from the given input stream, and prints the parsed result or an error message.</p>
     *
     * @param in  the input stream to read from
     * @param out the output stream to write to
     * @return 0 for valid email input, 1 for invalid input
     */
    static int run(InputStream in, PrintStream out) {
        out.println(PROMPT);

        Scanner scanner = new Scanner(in);
        String input = scanner.nextLine();

        EmailSlicer slicer = new EmailSlicer();
        EmailSlicer.Result result = slicer.slice(input);

        if (result != null) {
            out.println(String.format(USERNAME_FORMAT, result.username()));
            out.println(String.format(DOMAIN_FORMAT, result.domain()));
            return 0;
        } else {
            out.println(INVALID_MESSAGE);
            return 1;
        }
    }
}
