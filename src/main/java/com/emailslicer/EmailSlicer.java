package com.emailslicer;

/**
 * Core email-parsing logic, mirroring the behaviour of the original Python script.
 *
 * <p>The parse pipeline is:
 * <ol>
 *   <li>Null guard — throws {@link InvalidEmailException} on {@code null} input.</li>
 *   <li>Whitespace trimming — equivalent to Python's {@code str.strip()}.</li>
 *   <li>Presence check — {@code indexOf('@')} equivalent to Python's
 *       {@code str.find("@") != -1}; throws {@link InvalidEmailException} when
 *       no {@code @} is found.</li>
 *   <li>Slicing — extracts the substring before and after the <em>first</em>
 *       {@code @}, equivalent to Python's {@code str.index("@")} semantics.</li>
 * </ol>
 *
 * <p>No further validation is applied. Edge cases such as {@code "@domain"},
 * {@code "user@"}, or {@code "user@@domain"} are considered valid as long as
 * at least one {@code @} character is present.
 */
public class EmailSlicer {

    /**
     * Parses a raw email address string into its username and domain components.
     *
     * @param rawEmail the raw email address; must not be {@code null}
     * @return an {@link EmailParts} record containing the parsed username and domain
     * @throws InvalidEmailException if {@code rawEmail} is {@code null} or does not
     *                               contain an {@code @} character
     */
    public EmailParts parse(String rawEmail) {
        if (rawEmail == null) {
            throw new InvalidEmailException("Email must not be null");
        }

        String email = rawEmail.trim();

        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            throw new InvalidEmailException("Invalid email address");
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }
}
