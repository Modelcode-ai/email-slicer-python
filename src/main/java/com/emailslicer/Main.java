package com.emailslicer;

import java.util.Optional;
import java.util.Scanner;

/**
 * CLI entry point — reads an email address from stdin and prints the
 * extracted username and domain, mirroring the original Python script's
 * prompts and output format.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        try (Scanner scanner = new Scanner(System.in)) {
            String email = scanner.nextLine();

            Optional<EmailParts> result = EmailSlicer.slice(email);

            if (result.isPresent()) {
                EmailParts parts = result.get();
                System.out.println("Your username is:  " + parts.username());
                System.out.println("Your domain is:  " + parts.domain());
            } else {
                System.out.println(EmailSlicer.INVALID_EMAIL_MESSAGE);
                System.exit(1);
            }
        }
    }
}
