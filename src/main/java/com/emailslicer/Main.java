package com.emailslicer;

import java.util.Optional;
import java.util.Scanner;

/**
 * Console entry point for the Email Slicer application.
 *
 * <p>Mirrors the original Python script's interactive flow:
 * prompts for an email address, then prints the username and domain
 * or an error message for invalid input.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(Messages.PROMPT_EMAIL);

        try (Scanner scanner = new Scanner(System.in)) {
            String email = scanner.nextLine().strip();

            Optional<EmailComponents> result = EmailSlicer.slice(email);

            if (result.isPresent()) {
                EmailComponents components = result.get();
                System.out.println(Messages.USERNAME_PREFIX + components.username());
                System.out.println(Messages.DOMAIN_PREFIX + components.domain());
            } else {
                System.out.println(Messages.ERROR_INVALID_EMAIL);
                System.exit(1);
            }
        }
    }
}
