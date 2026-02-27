package com.emailslicer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * CLI entry point for the Email Slicer application.
 * <p>
 * Reads an email address from either a command-line argument or standard input,
 * parses it into username and domain components, and prints the result.
 * <p>
 * Exit codes:
 * <ul>
 *   <li>{@code 0} — success</li>
 *   <li>{@code 1} — invalid email input</li>
 *   <li>{@code 2} — unexpected error</li>
 * </ul>
 */
public class Main {

    public static void main(String[] args) {
        var slicer = new EmailSlicer();

        try {
            String input;

            if (args.length > 0) {
                input = args[0];
            } else {
                System.out.print("Enter your email: ");
                try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
                    input = reader.readLine();
                }
            }

            var parts = slicer.parse(input);
            System.out.println("Username: " + parts.username());
            System.out.println("Domain: " + parts.domain());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid email: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            System.exit(2);
        }
    }
}
