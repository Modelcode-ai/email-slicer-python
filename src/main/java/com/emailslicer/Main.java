package com.emailslicer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * CLI entry point for the Email Slicer tool.
 * <p>
 * Prompts the user for an email address, parses it using {@link EmailSlicer},
 * and prints the username and domain to standard output.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String input = reader.readLine();

            if (input == null) {
                System.out.println("Please enter a valid Email Id.");
                return;
            }

            EmailSlicer.Result result = EmailSlicer.slice(input);
            System.out.println("Your username is: " + result.getUsername());
            System.out.println("Your domain is: " + result.getDomain());

        } catch (IllegalArgumentException e) {
            System.out.println("Please enter a valid Email Id.");
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }
}
