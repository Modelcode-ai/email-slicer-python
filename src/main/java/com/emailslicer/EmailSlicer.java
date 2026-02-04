package com.emailslicer;

import java.util.Scanner;

/**
 * Main entry point for the Email Slicer application.
 * Handles console I/O and delegates parsing to EmailParser.
 */
public final class EmailSlicer {

    private static final String PROMPT_MESSAGE = "Please enter your Email Id:";
    private static final String USERNAME_MESSAGE_PREFIX = "Your username is:  ";
    private static final String DOMAIN_MESSAGE_PREFIX = "Your domain is:  ";
    private static final String INVALID_EMAIL_MESSAGE = "Please enter a valid Email Id.";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmailParser parser = new EmailParser();

        System.out.println(PROMPT_MESSAGE);
        String input = scanner.nextLine();

        try {
            EmailComponents components = parser.parse(input);
            System.out.println(USERNAME_MESSAGE_PREFIX + components.username());
            System.out.println(DOMAIN_MESSAGE_PREFIX + components.domain());
        } catch (IllegalArgumentException e) {
            System.out.println(INVALID_EMAIL_MESSAGE);
        }
    }
}
