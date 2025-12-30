package com.emailslicer;

import com.emailslicer.validator.EmailValidator;

/**
 * Service class responsible for slicing email addresses into
 * username and domain parts.
 * This class encapsulates the core business logic for email processing,
 * delegating validation to the EmailValidator and performing string
 * slicing analogous to Python's slice notation.
 */
public class EmailSlicer {

    /**
     * The email validator used for validating and normalizing
     * email addresses.
     */
    private final EmailValidator validator;

    /**
     * Constructs an EmailSlicer with the specified EmailValidator.
     * Uses constructor injection pattern to enable testability and
     * future extensibility.
     *
     * @param emailValidator the EmailValidator to use for validation
     *        and normalization
     */
    public EmailSlicer(final EmailValidator emailValidator) {
        this.validator = emailValidator;
    }

    /**
     * Slices an email address into username and domain parts.
     * The email is first validated and normalized by the injected
     * validator.
     * The method assumes the validator guarantees exactly one '@'
     * symbol and non-empty username and domain.
     *
     * @param email the email address to slice
     * @return an EmailParts object containing the username and domain
     * @throws com.emailslicer.validator.InvalidEmailException
     *         if validation fails
     */
    public EmailParts slice(final String email) {
        // Delegate validation and get normalized email
        String normalizedEmail = validator.validate(email);

        // Find the index of '@' (guaranteed to exist by validator)
        int atIndex = normalizedEmail.indexOf('@');

        // Extract username (substring from 0 to atIndex)
        String username = normalizedEmail.substring(0, atIndex);

        // Extract domain (substring from atIndex + 1 to end)
        String domain = normalizedEmail.substring(atIndex + 1);

        // Create and return the EmailParts value object
        return new EmailParts(username, domain);
    }
}
