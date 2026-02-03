package com.emailslicer;

import java.util.Scanner;

/**
 * Email Slicer - parses an email address into username and domain components.
 */
public class EmailSlicer {

    /**
     * Represents a parsed email with username and domain components.
     */
    public record ParsedEmail(String username, String domain) {}

    /**
     * Parses an email address into its username and domain components.
     *
     * @param input the email address to parse
     * @return a ParsedEmail containing the username and domain, or null if invalid
     */
    public static ParsedEmail parseEmail(String input) {
        if (input == null) {
            return null;
        }

        String trimmed = input.trim();

        // Find first @ position
        int atIndex = trimmed.indexOf('@');

        // Check if @ exists
        if (atIndex == -1) {
            return null;
        }

        // Check if @ is at the first position
        if (atIndex == 0) {
            return null;
        }

        // Check if @ is at the last position
        if (atIndex == trimmed.length() - 1) {
            return null;
        }

        // Check for multiple @ symbols
        if (trimmed.indexOf('@', atIndex + 1) != -1) {
            return null;
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new ParsedEmail(username, domain);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your Email Id:");
        String email = scanner.nextLine();

        ParsedEmail result = parseEmail(email);

        if (result != null) {
            System.out.println("Your username is: " + result.username());
            System.out.println("Your domain is: " + result.domain());
        } else {
            System.out.println("Please enter a valid Email Id.");
        }

        scanner.close();
    }
}
