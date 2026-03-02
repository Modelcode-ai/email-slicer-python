package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 *
 * <p>Prompts the user for an email address, delegates parsing to
 * {@link EmailSlicer}, and prints the username and domain or an
 * error message for invalid input.</p>
 */
public class Main {

    public static void main(String[] args) {
        System.out.print("Please enter your Email Id:");

        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();
            try {
                EmailSliceResult result = EmailSlicer.slice(input);
                System.out.println("Your username is: " + result.username());
                System.out.println("Your domain is: " + result.domain());
            } catch (InvalidEmailException e) {
                System.out.println("Please enter a valid Email Id.");
            }
        }
    }
}
