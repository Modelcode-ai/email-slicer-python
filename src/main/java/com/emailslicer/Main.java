package com.emailslicer;

import java.util.Scanner;

/**
 * Console entry point for the Email Slicer application.
 * Reads an email address from standard input, parses it, and prints the username and domain.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(Messages.PROMPT_EMAIL);

        try (Scanner scanner = new Scanner(System.in)) {
            String email = scanner.nextLine().strip();

            EmailSlicer.slice(email).ifPresentOrElse(
                    parts -> {
                        System.out.println(Messages.USERNAME_PREFIX + parts.username());
                        System.out.println(Messages.DOMAIN_PREFIX + parts.domain());
                    },
                    () -> {
                        System.out.println(Messages.ERROR_INVALID_EMAIL);
                        System.exit(1);
                    }
            );
        }
    }
}
