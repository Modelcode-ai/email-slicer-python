package emailslicer;

/**
 * Pure business logic for parsing email addresses into username and domain parts.
 * <p>
 * This class contains no console I/O. It accepts a raw email string, trims whitespace,
 * validates the presence of {@code @}, and splits on the first {@code @} to produce
 * an {@link EmailParts} record.
 * <p>
 * Behavior matches the original Python {@code emailSlicer.py} script:
 * <ul>
 *   <li>Whitespace is trimmed (equivalent to Python's {@code .strip()}).</li>
 *   <li>Validation checks only for the presence of at least one {@code @}.</li>
 *   <li>Splitting is performed on the <b>first</b> {@code @} (equivalent to Python's {@code .index("@")}).</li>
 *   <li>Empty username or domain segments are allowed (e.g., {@code @domain.com} or {@code user@}).</li>
 * </ul>
 */
public class EmailSlicer {

    /**
     * Parses the given email string into its username and domain components.
     *
     * @param email the raw email input (may include leading/trailing whitespace)
     * @return an {@link EmailParts} record containing the username and domain
     * @throws IllegalArgumentException if the trimmed input does not contain {@code @}
     */
    public EmailParts slice(String email) {
        String trimmed = email.strip();

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException("Please enter a valid Email Id.");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }
}
