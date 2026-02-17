package com.emailslicer;

import java.util.Optional;
import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 */
public class Main {

    static final String PROMPT = "Please enter your Email Id:";
    static final String USERNAME_LABEL = "Your username is:  ";
    static final String DOMAIN_LABEL = "Your domain is:  ";
    static final String ERROR_MESSAGE = "Please enter a valid Email Id.";

    public static void main(String[] args) {
        System.out.println(PROMPT);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().strip();

        Optional<EmailParts> result = EmailSlicer.parse(input);

        if (result.isPresent()) {
            EmailParts parts = result.get();
            System.out.println(USERNAME_LABEL + parts.username());
            System.out.println(DOMAIN_LABEL + parts.domain());
        } else {
            System.out.println(ERROR_MESSAGE);
        }
    }
}
