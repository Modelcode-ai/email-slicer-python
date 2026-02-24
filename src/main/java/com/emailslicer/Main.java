package com.emailslicer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * CLI entry point for the Email Slicer tool.
 * Reads an email address from stdin, parses it, and prints the username and domain.
 */
public class Main {

    /**
     * Entry point that delegates to {@link #run(InputStream, PrintStream, PrintStream)}
     * and translates the return value into a process exit code.
     */
    public static void main(String[] args) {
        int exitCode = run(System.in, System.out, System.err);
        System.exit(exitCode);
    }

    /**
     * Core CLI logic separated from {@code System.exit()} for testability.
     *
     * @param in  the input stream to read the email address from
     * @param out the output stream for successful results
     * @param err the output stream for error messages
     * @return 0 on success, 1 on failure
     */
    static int run(InputStream in, PrintStream out, PrintStream err) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        EmailSlicer slicer = new EmailSlicer();

        try {
            String line = reader.readLine();
            if (line == null) {
                err.println("Invalid email address.");
                return 1;
            }

            EmailSlicer.Result result = slicer.slice(line);
            out.println("Username: " + result.getUsername());
            out.println("Domain: " + result.getDomain());
            return 0;

        } catch (IllegalArgumentException e) {
            err.println("Invalid email address.");
            return 1;
        } catch (IOException e) {
            err.println("Error reading input.");
            return 1;
        }
    }
}
