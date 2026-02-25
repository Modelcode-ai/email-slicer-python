package emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 *
 * <p>Prompts the user for an email address, delegates parsing to
 * {@link EmailSlicer}, and prints the username and domain to stdout.
 * Exits with code 0 on success and code 1 on invalid input.
 */
public class Main {

    /**
     * Runs the Email Slicer CLI: reads one email from stdin, prints the
     * result or an error message, and returns the appropriate exit code.
     *
     * <p>This method is separated from {@code main()} so that tests can
     * invoke it without triggering {@link System#exit(int)}.
     *
     * @return 0 on success, 1 on invalid input
     */
    public static int run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your email: ");

        if (!scanner.hasNextLine()) {
            System.out.println("Invalid email address.");
            return 1;
        }

        String input = scanner.nextLine();

        try {
            EmailSlicer.EmailParts parts = EmailSlicer.parse(input);
            System.out.println("Your username is: " + parts.getUsername());
            System.out.println("Your domain is: " + parts.getDomain());
            return 0;
        } catch (InvalidEmailException e) {
            System.out.println("Invalid email address.");
            return 1;
        }
    }

    public static void main(String[] args) {
        int exitCode = run();
        System.exit(exitCode);
    }
}
