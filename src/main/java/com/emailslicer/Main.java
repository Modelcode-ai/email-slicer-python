package com.emailslicer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * CLI entry point for the Email Slicer tool.
 *
 * <p>Prompts the user for an email address via standard input, delegates
 * parsing to {@link EmailSlicer}, and prints the username and domain to
 * standard output. Replicates the observable behavior of the original
 * Python {@code emailSlicer.py} script.</p>
 */
public class Main {

    /**
     * Reads an email address from stdin, parses it, and prints the result.
     *
     * <p>Exit codes:</p>
     * <ul>
     *   <li>{@code 0} — normal termination (valid or invalid email)</li>
     *   <li>{@code 1} — unexpected I/O error while reading input</li>
     * </ul>
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String email = reader.readLine();

            if (email == null) {
                // EOF with no input — treat as empty / invalid
                System.out.println("Please enter a valid Email Id.");
                return;
            }

            EmailSlicer slicer = new EmailSlicer();
            EmailSliceResult result = slicer.slice(email);

            // Python's print("Your username is: ", username) adds a space
            // between the colon and value, resulting in two spaces total.
            System.out.println("Your username is:  " + result.username());
            System.out.println("Your domain is:  " + result.domain());

        } catch (IllegalArgumentException e) {
            System.out.println("Please enter a valid Email Id.");
        } catch (IOException e) {
            System.err.println("An unexpected I/O error occurred while reading input.");
            System.exit(1);
        }
    }
}
