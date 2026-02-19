package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer tool.
 *
 * <p>Reads a single email address from standard input, parses it into username
 * and domain components, and prints the result to standard output.</p>
 *
 * <p>Exit codes:</p>
 * <ul>
 *   <li>{@code 0} — successful parse</li>
 *   <li>{@code 1} — invalid input or missing input</li>
 * </ul>
 */
public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextLine()) {
                System.err.println("Invalid email address.");
                System.exit(1);
            }

            String line = scanner.nextLine();
            EmailSlicer slicer = new EmailSlicer();
            EmailComponents components = slicer.parse(line);

            System.out.println("Username: " + components.username());
            System.out.println("Domain: " + components.domain());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid email address.");
            System.exit(1);
        }
    }
}
