package com.emailslicer;

/**
 * CLI entry point for the Email Slicer tool.
 *
 * <p>Accepts a single email address as a command-line argument, parses it
 * using {@link EmailSlicer}, and prints the username and domain to stdout.</p>
 *
 * <p>Exit codes:</p>
 * <ul>
 *   <li>{@code 0} – successful parse</li>
 *   <li>{@code 1} – missing argument or invalid email</li>
 * </ul>
 */
public final class Main {

    private Main() {
        // Entry-point class; prevent instantiation.
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java -jar emailslicer.jar <email>");
            System.exit(1);
        }

        String input = args[0];
        try {
            EmailSlicer.ParsedEmail parsed = EmailSlicer.parse(input);
            System.out.println("Your user name is: " + parsed.getUsername());
            System.out.println("Your domain name is: " + parsed.getDomain());
            System.exit(0);
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }
}
