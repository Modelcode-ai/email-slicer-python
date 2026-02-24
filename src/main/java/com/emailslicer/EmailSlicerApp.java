package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 * Reads an email address from stdin and prints the username and domain.
 */
public class EmailSlicerApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your Email Id:");
        String input = scanner.nextLine();

        try {
            EmailSlicer.EmailParts parts = EmailSlicer.slice(input);
            System.out.println("Your username is: " + parts.getUsername());
            System.out.println("Your domain is: " + parts.getDomain());
        } catch (IllegalArgumentException ex) {
            System.out.println("Please enter a valid Email Id.");
        }
    }
}
