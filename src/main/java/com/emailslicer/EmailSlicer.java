package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 * This class handles user interaction via stdin/stdout.
 */
public class EmailSlicer {

    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();

            EmailSlicerService service = new EmailSlicerService();
            EmailSliceResult result = service.slice(input);

            if (result.valid()) {
                // Python's print("Your username is: ", username) adds a space between args
                // So we need two spaces: one from the literal string, one from Python's print behavior
                System.out.println("Your username is:  " + result.username());
                System.out.println("Your domain is:  " + result.domain());
            } else {
                System.out.println(result.errorMessage());
            }
        }
    }
}
