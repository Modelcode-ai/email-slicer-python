package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 * <p>
 * Reads an email address from stdin, validates it, and prints the
 * username and domain components — or an error message if the input
 * is not a valid email address.
 */
public class Main {

    /** Input prompt displayed to the user. */
    public static final String PROMPT = "Enter your Email Id: ";

    /** Output format for the username line. */
    public static final String USERNAME_FORMAT = "Your username is: ";

    /** Output format for the domain line. */
    public static final String DOMAIN_FORMAT = "Your domain is: ";

    /** Error message for invalid email input. */
    public static final String INVALID_EMAIL_MESSAGE = "Please enter a valid Email Id.";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(PROMPT);

        String input = scanner.nextLine();

        if (!EmailSlicer.isValid(input)) {
            System.out.println(INVALID_EMAIL_MESSAGE);
            return;
        }

        String username = EmailSlicer.extractUsername(input);
        String domain = EmailSlicer.extractDomain(input);

        System.out.println(USERNAME_FORMAT + username);
        System.out.println(DOMAIN_FORMAT + domain);
    }
}
