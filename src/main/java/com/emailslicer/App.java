package com.emailslicer;

import java.util.Optional;
import java.util.Scanner;

/**
 * CLI entrypoint for the Email Slicer application.
 *
 * <p>Prompts the user for an email address, delegates parsing and validation
 * to {@link EmailSlicer}, and prints the username and domain on success or
 * an error message on failure.</p>
 *
 * <p>This is a single-run tool: it prompts once, reads once, and exits with
 * code {@code 0} regardless of whether the input was valid or invalid.</p>
 */
public class App {

    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();

            EmailSlicer slicer = new EmailSlicer();
            Optional<EmailParts> result = slicer.slice(input);

            if (result.isPresent()) {
                EmailParts parts = result.get();
                System.out.println("Your username is: " + parts.getUsername());
                System.out.println("Your domain is: " + parts.getDomain());
            } else {
                System.out.println("Please enter a valid Email Id.");
            }
        }
    }
}
