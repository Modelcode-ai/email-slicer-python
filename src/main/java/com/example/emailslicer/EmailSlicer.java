package com.example.emailslicer;

import java.util.Scanner;

/**
 * Email Slicer — reads an email address from stdin and prints the username and domain.
 * <p>
 * This is a Java 17 migration of the original Python emailSlicer.py tool.
 * It preserves the exact output format of the Python version, including spacing.
 */
public class EmailSlicer {

    /**
     * Parses the given email string and prints username/domain to stdout.
     * <p>
     * If the trimmed input contains an {@code @} character, the username (before
     * the first {@code @}) and domain (after the first {@code @}) are printed.
     * Otherwise, an error message is printed.
     *
     * @param email the raw email input (may include leading/trailing whitespace)
     */
    public static void sliceEmail(String email) {
        String trimmed = (email == null) ? "" : email.trim();

        if (!trimmed.contains("@")) {
            System.out.println("Please enter a valid Email Id.");
            return;
        }

        int atIndex = trimmed.indexOf("@");
        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        // Python's print("Your username is: ", username) uses sep=' ' which
        // inserts a space between arguments, resulting in two spaces after the colon.
        System.out.println("Your username is:  " + username);
        System.out.println("Your domain is:  " + domain);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.hasNextLine() ? scanner.nextLine() : "";
        sliceEmail(input);
    }
}
