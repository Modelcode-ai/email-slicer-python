package emailslicer;

import java.util.Scanner;

/**
 * Email Slicer CLI application.
 * <p>
 * Prompts the user for an email address, parses it into username and domain
 * components by splitting on the first '@' character, and prints the results.
 * <p>
 * This is a Java 17 migration of the original Python Email Slicer tool.
 */
public class EmailSlicer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your email: ");
        String input = scanner.nextLine();

        String trimmed = input == null ? "" : input.trim();

        EmailSliceResult result = sliceEmail(trimmed);

        if (!result.isValid()) {
            System.out.println("Invalid email address.");
        } else {
            System.out.println("Your username is: " + result.getUsername());
            System.out.println("Your domain is: " + result.getDomain());
        }
    }

    /**
     * Parses an email address by splitting on the first '@' character.
     * <p>
     * The method finds the first occurrence of '@' and splits the string into
     * a username (everything before '@') and domain (everything after '@').
     * If the input is {@code null} or does not contain '@', an invalid result
     * is returned.
     *
     * @param email the email address to parse (may be {@code null})
     * @return an {@link EmailSliceResult} representing the parsed result
     */
    static EmailSliceResult sliceEmail(String email) {
        if (email == null) {
            email = "";
        }

        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            return EmailSliceResult.invalid();
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return EmailSliceResult.valid(username, domain);
    }
}
