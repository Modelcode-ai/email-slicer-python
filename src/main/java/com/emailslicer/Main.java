package com.emailslicer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * CLI entry point for the Email Slicer tool.
 *
 * <p>Reads a single line from stdin (no prompt), delegates to
 * {@link EmailSlicer#slice(String)}, and prints the username and domain
 * (or an error message for invalid input) to stdout.</p>
 */
public class Main {

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

            String line = reader.readLine();

            EmailSlicer.EmailParts parts = EmailSlicer.slice(line);
            System.out.println("Your username is: " + parts.username());
            System.out.println("Your domain is: " + parts.domain());

        } catch (InvalidEmailException e) {
            System.out.println("Please enter a valid email address");
        } catch (java.io.IOException e) {
            System.out.println("Please enter a valid email address");
        }
    }
}
