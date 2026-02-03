package com.emailslicer;

import java.util.Scanner;

/**
 * EmailSlicer - A simple CLI tool that parses an email address into username and domain.
 * Migrated from Python to Java 17.
 */
public class EmailSlicer {

    private static final String PROMPT = "Please enter your Email Id:";
    private static final String USERNAME_PREFIX = "Your username is:  ";
    private static final String DOMAIN_PREFIX = "Your domain is:  ";
    private static final String INVALID_MESSAGE = "Please enter a valid Email Id.";

    public static void main(String[] args) {
        System.out.println(PROMPT);

        try (Scanner scanner = new Scanner(System.in)) {
            String rawInput = scanner.nextLine();
            EmailParts parts = parseEmail(rawInput);

            if (parts != null) {
                System.out.println(USERNAME_PREFIX + parts.getUsername());
                System.out.println(DOMAIN_PREFIX + parts.getDomain());
                System.exit(0);
            } else {
                System.out.println(INVALID_MESSAGE);
                System.exit(1);
            }
        } catch (Exception e) {
            System.out.println(INVALID_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * Parses an email address into username and domain, using the first '@'.
     * Returns null for invalid inputs (null, empty, whitespace-only, or missing '@').
     *
     * @param rawInput the raw email string input
     * @return EmailParts containing username and domain, or null if invalid
     */
    static EmailParts parseEmail(String rawInput) {
        if (rawInput == null) {
            return null;
        }

        String email = rawInput.trim();

        if (email.isEmpty()) {
            return null;
        }

        if (!email.contains("@")) {
            return null;
        }

        int atIndex = email.indexOf('@');
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }

    /**
     * Immutable value class holding the parsed username and domain parts of an email.
     */
    static class EmailParts {
        private final String username;
        private final String domain;

        EmailParts(String username, String domain) {
            this.username = username;
            this.domain = domain;
        }

        String getUsername() {
            return username;
        }

        String getDomain() {
            return domain;
        }
    }
}
