package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer tool.
 * <p>
 * Reads an email address from standard input, splits it into username and
 * domain using {@link EmailSlicer#slice(String)}, and prints the results.
 * This mirrors the behavior of the original Python {@code emailSlicer.py}.
 */
public final class Main {

    private Main() {
        // Entry-point class — prevent instantiation
    }

    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        try (Scanner scanner = new Scanner(System.in)) {
            String email = scanner.nextLine().strip();

            try {
                EmailParts parts = EmailSlicer.slice(email);
                System.out.println("Your username is:  " + parts.username());
                System.out.println("Your domain is:  " + parts.domain());
            } catch (IllegalArgumentException e) {
                System.out.println("Please enter a valid Email Id.");
                System.exit(1);
            }
        }
    }
}
