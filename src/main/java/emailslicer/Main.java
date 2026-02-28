package emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 * <p>
 * Reads a single email address from standard input, parses it into
 * username and domain parts, and prints the results to standard output.
 * On invalid input, an error message is printed to standard error
 * and the process exits with code 1.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();

        try {
            EmailParts parts = EmailSlicer.parse(input);
            System.out.println("Your username is " + parts.username());
            System.out.println("Your domain is " + parts.domain());
        } catch (InvalidEmailException e) {
            System.err.println("Invalid email address.");
            System.exit(1);
        }
    }
}
