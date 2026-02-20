package ai.modelcode.emailslicer;

/**
 * Core email-slicing service that parses an email address string into its
 * username and domain components.
 *
 * <p>This class mirrors the logic of the original Python {@code emailSlicer.py}
 * script: it strips leading/trailing whitespace, checks for the presence of
 * {@code @}, and splits on the first {@code @} character.</p>
 *
 * <p>Edge cases such as {@code "@"}, {@code "@domain"}, and {@code "user@"}
 * are treated as valid (matching the Python script's behavior), since the only
 * validation performed is checking that {@code @} is present.</p>
 */
public class EmailSlicer {

    /**
     * Slices the given raw email string into its username and domain parts.
     *
     * @param rawEmail the raw email input (may contain leading/trailing whitespace)
     * @return an {@link EmailComponents} record containing the username and domain
     * @throws IllegalArgumentException if {@code rawEmail} is {@code null} or
     *                                  does not contain an {@code @} character
     */
    public EmailComponents slice(String rawEmail) {
        if (rawEmail == null) {
            throw new IllegalArgumentException("Email must not be null");
        }

        // Match Python's .strip() behavior — removes leading and trailing whitespace
        String email = rawEmail.strip();

        // Mirror Python's: if email.find("@") != -1
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email: missing '@'");
        }

        int atIndex = email.indexOf('@');
        // Equivalent to Python's email[:email.index("@")]
        String username = email.substring(0, atIndex);
        // Equivalent to Python's email[email.index("@") + 1:]
        String domain = email.substring(atIndex + 1);

        return new EmailComponents(username, domain);
    }
}
