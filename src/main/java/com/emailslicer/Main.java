package com.emailslicer;

import java.util.Optional;
import java.util.Scanner;

/**
 * Console entry point for the Email Slicer application.
 * Prompts the user for an email address, slices it, and prints the result.
 *
 * <p>This class is the Java equivalent of the original Python script's
 * top-level I/O logic.</p>
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(Messages.PROMPT_EMAIL);

        try (Scanner scanner = new Scanner(System.in)) {
            String email = scanner.nextLine().strip();

            Optional<EmailSlicerResult> result = EmailSlicer.slice(email);

            if (result.isPresent()) {
                EmailSlicerResult sliced = result.get();
                System.out.println(Messages.USERNAME_PREFIX + sliced.username());
                System.out.println(Messages.DOMAIN_PREFIX + sliced.domain());
            } else {
                System.out.println(Messages.INVALID_EMAIL);
                System.exit(1);
            }
        }
    }
}
