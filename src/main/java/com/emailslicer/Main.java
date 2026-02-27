package com.emailslicer;

import java.util.Optional;
import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 *
 * <p>Reads a single email address from stdin, parses it using {@link EmailSlicer},
 * and prints the username and domain to stdout. Output format matches the original
 * Python implementation exactly, including the two-space gap after the colon
 * (produced by Python's {@code print("label", value)} with default separator).</p>
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        String input;
        try (Scanner scanner = new Scanner(System.in)) {
            if (scanner.hasNextLine()) {
                input = scanner.nextLine();
            } else {
                input = null;
            }
        }

        EmailSlicer slicer = new EmailSlicer();
        Optional<EmailSliceResult> result = slicer.slice(input);

        if (result.isPresent()) {
            EmailSliceResult email = result.get();
            // Python's print("Your username is: ", username) produces two spaces
            // after the colon: one from the string literal and one from print's
            // default sep=" " separator.
            System.out.println("Your username is:  " + email.username());
            System.out.println("Your domain is:  " + email.domain());
        } else {
            System.out.println("Please enter a valid Email Id.");
            System.exit(1);
        }
    }
}
