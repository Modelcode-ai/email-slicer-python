package com.emailslicer;

import java.util.Scanner;

/**
 * A CLI tool that takes an email address as input and returns the username and domain.
 * Migrated from the Python Email Slicer tool.
 */
public class EmailSlicer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your Email Id:");
        String input = scanner.nextLine();
        String email = input == null ? "" : input.trim();

        if (!email.contains("@")) {
            System.out.println("Please enter a valid Email Id.");
            return;
        }

        EmailParts parts = parseEmail(email);

        // Python's print("Your username is: ", username) inserts a space separator,
        // resulting in a double space after the colon. Replicate this exactly.
        System.out.println("Your username is:  " + parts.username());
        System.out.println("Your domain is:  " + parts.domain());
    }

    /**
     * Parses an email address by splitting on the first '@' character.
     *
     * @param email the email string to parse (must contain '@')
     * @return an EmailParts record with the username and domain
     */
    static EmailParts parseEmail(String email) {
        int atIndex = email.indexOf('@');
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);
        return new EmailParts(username, domain);
    }
}
