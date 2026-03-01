package emailslicer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * A simple CLI tool that takes an email address as input and returns the
 * username and domain as output. This is a Java 17 port of the original
 * Python emailSlicer.py script.
 */
public class EmailSlicer {

    /**
     * Holds the parsed username and domain from a valid email address.
     */
    public record EmailParts(String username, String domain) {
    }

    /**
     * Parses a raw email input string into its username and domain parts.
     * <p>
     * The input is trimmed of leading/trailing whitespace. If the trimmed input
     * contains an '@' character, it is split at the first '@' into username
     * (everything before) and domain (everything after). This matches the
     * behavior of Python's {@code index("@")} and slice notation.
     *
     * @param rawInput the raw input string (may be null)
     * @return an Optional containing the EmailParts if the input is valid,
     *         or empty if the input is null, empty, or does not contain '@'
     */
    public static Optional<EmailParts> parseEmail(String rawInput) {
        if (rawInput == null) {
            return Optional.empty();
        }

        String email = rawInput.trim();

        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            return Optional.empty();
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return Optional.of(new EmailParts(username, domain));
    }

    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        String line;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            line = reader.readLine();
        } catch (IOException e) {
            // Let the JVM report the I/O error; no custom handling per spec.
            throw new RuntimeException(e);
        }

        Optional<EmailParts> result = parseEmail(line);

        if (result.isPresent()) {
            EmailParts parts = result.get();
            System.out.println("Your username is:  " + parts.username());
            System.out.println("Your domain is:  " + parts.domain());
        } else {
            System.out.println("Please enter a valid Email Id.");
        }
    }
}
