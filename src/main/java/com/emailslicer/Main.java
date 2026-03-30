package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer tool.
 * <p>
 * Reads an email address from standard input, delegates parsing to
 * {@link EmailSlicer}, and prints the username and domain to standard output.
 * This mirrors the behavior of the original Python {@code emailSlicer.py}.
 */
public final class Main {

    private Main() {
        // Entry-point class — prevent instantiation
    }

    public static void main(String[] args) {
        System.out.println(Messages.PROMPT_EMAIL);

        try (Scanner scanner = new Scanner(System.in)) {
            String email = scanner.nextLine().strip();

            try {
                EmailSliceResult result = EmailSlicer.slice(email);
                System.out.printf(Messages.USERNAME_FORMAT + "%n", result.username());
                System.out.printf(Messages.DOMAIN_FORMAT + "%n", result.domain());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
    }
}
