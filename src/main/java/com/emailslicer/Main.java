package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 * <p>
 * Prompts the user for an email address, delegates parsing to {@link EmailSlicer},
 * and prints the extracted username and domain. The prompt text and output format
 * mirror the original Python script's behavior, including the extra space produced
 * by Python's {@code print("label: ", value)} comma-separated arguments.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your Email Id:");
        String input = scanner.nextLine();

        try {
            EmailSlicer.Result result = EmailSlicer.slice(input);
            // Python's print("Your username is: ", username) outputs a double space
            // before the value due to the comma separator adding a space character.
            System.out.println("Your username is:  " + result.username());
            System.out.println("Your domain is:  " + result.domain());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
