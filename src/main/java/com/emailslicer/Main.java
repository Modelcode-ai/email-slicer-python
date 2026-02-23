package com.emailslicer;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer tool.
 * <p>
 * Prompts the user for an email address, parses it into username and domain
 * components using {@link EmailSlicer}, and prints the results. Mirrors the
 * behavior of the original Python {@code emailSlicer.py} script.
 */
public class Main {

    /**
     * Application entry point.
     * <p>
     * Delegates to {@link #run(InputStream, PrintStream)} and calls
     * {@code System.exit} with a non-zero code on failure. The exit call
     * is separated from the core logic to allow integration testing without
     * terminating the test JVM.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        int exitCode = run(System.in, System.out);
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }

    /**
     * Runs the email slicer CLI logic using the provided I/O streams.
     *
     * @param in  the input stream to read the email address from
     * @param out the output stream to print results and errors to
     * @return 0 on success, 1 on invalid input
     */
    static int run(InputStream in, PrintStream out) {
        out.println("Please enter your Email Id:");

        Scanner scanner = new Scanner(in);
        String email = scanner.nextLine();
        EmailSlicer slicer = new EmailSlicer();

        try {
            EmailParts parts = slicer.parse(email);
            // Python's print("Your username is: ", username) produces two spaces
            // after the colon due to comma-separated print argument behavior.
            out.println("Your username is:  " + parts.username());
            out.println("Your domain is:  " + parts.domain());
            return 0;
        } catch (IllegalArgumentException e) {
            out.println("Please enter a valid Email Id.");
            return 1;
        }
    }
}
