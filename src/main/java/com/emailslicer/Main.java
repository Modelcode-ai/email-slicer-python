package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer tool. Reads an email address from
 * standard input, parses it using {@link EmailSlicer}, and prints the
 * username and domain to standard output.
 *
 * <p>Output format matches the original Python implementation exactly,
 * including the double-space before values (Python {@code print("label", value)}
 * produces a separator space between arguments).
 */
public final class Main {

    private Main() {
        // Entry-point class — not instantiable
    }

    /**
     * Reads a single email address from stdin, parses it, and prints the result.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();

            EmailSlicer.ParsedEmail parsed = EmailSlicer.parse(input);

            // Two spaces before the value: one trailing in the label string,
            // one from Python's print() default sep=' ' between arguments.
            System.out.println("Your username is:  " + parsed.getUsername());
            System.out.println("Your domain is:  " + parsed.getDomain());
        } catch (IllegalArgumentException e) {
            System.out.println("Please enter a valid Email Id.");
        }
    }
}
