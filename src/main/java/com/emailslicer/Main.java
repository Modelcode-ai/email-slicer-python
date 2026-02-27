package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 *
 * <p>Prompts the user for an email address, delegates parsing to
 * {@link EmailSlicer#slice(String)}, and prints the username and domain
 * or an error message for invalid input.</p>
 */
public final class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your email: ");
        String input = scanner.nextLine();

        try {
            EmailParts parts = EmailSlicer.slice(input);
            System.out.println("Your username is " + parts.username());
            System.out.println("Your domain is " + parts.domain());
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid email address");
        }
    }
}
