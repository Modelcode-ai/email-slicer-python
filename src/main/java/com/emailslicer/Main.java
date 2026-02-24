package com.emailslicer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * CLI entry point for the Email Slicer application.
 *
 * <p>Reads a single email address from stdin, delegates parsing to
 * {@link EmailSlicer#slice(String)}, and prints the username and domain
 * to stdout. Exits with code 0 on success or 1 on invalid input / I/O error.</p>
 */
public class Main {

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Enter your email: ");
            String line = reader.readLine();

            EmailParts parts = EmailSlicer.slice(line);

            System.out.println("Your username is: " + parts.username());
            System.out.println("Your domain is: " + parts.domain());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid email. Please include '@'.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("An error occurred while reading input.");
            System.exit(1);
        }
    }
}
