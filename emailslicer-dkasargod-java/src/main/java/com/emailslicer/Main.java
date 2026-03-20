package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 *
 * <p>Prompts the user for an email address, delegates parsing to
 * {@link EmailSlicer#parse(String)}, and prints the result. Exits with
 * code 1 when the input is invalid.
 */
public final class Main {

    private Main() {
        // Entry-point class — not instantiable
    }

    public static void main(String[] args) {
        System.out.println(Messages.PROMPT_MESSAGE);

        try (Scanner scanner = new Scanner(System.in)) {
            String email = scanner.nextLine().strip();

            try {
                EmailParts parts = EmailSlicer.parse(email);
                System.out.println(Messages.USERNAME_PREFIX + parts.username());
                System.out.println(Messages.DOMAIN_PREFIX + parts.domain());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
    }
}
