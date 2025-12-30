package com.emailslicer;

/**
 * Stateless service for parsing and validating email addresses.
 * Implements structural validation and extracts username and domain components.
 */
public class EmailSlicerService {
  /**
   * Parses an email address into its username and domain components.
   * Performs the following validations:
   * - Email must not be null
   * - Email must not be empty after trimming
   * - Email must contain exactly one '@' symbol
   * - Username (part before '@') must not be empty
   * - Domain (part after '@') must not be empty
   *
   * @param email the email address to parse
   * @return EmailComponents containing the username and domain
   * @throws InvalidEmailException if the email fails any validation rule
   */
  public EmailComponents parse(String email) {
    // Handle null input
    if (email == null) {
      throw new InvalidEmailException("Email must not be null.");
    }

    // Trim whitespace (equivalent to Python's strip())
    String trimmed = email.trim();

    // Check for empty string after trimming
    if (trimmed.isEmpty()) {
      throw new InvalidEmailException("Email must not be empty.");
    }

    // Find the '@' symbol positions
    int firstAt = trimmed.indexOf('@');
    int lastAt = trimmed.lastIndexOf('@');

    // Validate exactly one '@' symbol
    if (firstAt == -1) {
      throw new InvalidEmailException("Email must contain exactly one '@' symbol.");
    }

    if (firstAt != lastAt) {
      throw new InvalidEmailException("Email must contain exactly one '@' symbol.");
    }

    // Extract username and domain using substring
    // Mirrors Python's email[:email.index("@")] and email[email.index("@") + 1:]
    String username = trimmed.substring(0, firstAt);
    String domain = trimmed.substring(firstAt + 1);

    // Validate username is non-empty
    if (username.isEmpty()) {
      throw new InvalidEmailException("Username (part before '@') must not be empty.");
    }

    // Validate domain is non-empty
    if (domain.isEmpty()) {
      throw new InvalidEmailException("Domain (part after '@') must not be empty.");
    }

    // Return the parsed components
    return new EmailComponents(username, domain);
  }
}
