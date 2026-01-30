package com.modelcode.emailslicer;

public class EmailParser {

    public EmailComponents parse(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        String trimmed = email.strip();

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException("Invalid email: missing '@' symbol");
        }
        if (trimmed.indexOf('@', atIndex + 1) != -1) {
            throw new IllegalArgumentException("Invalid email: multiple '@' symbols");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        if (username.isEmpty()) {
            throw new IllegalArgumentException("Invalid email: username is empty");
        }
        if (domain.isEmpty()) {
            throw new IllegalArgumentException("Invalid email: domain is empty");
        }
        if (!domain.contains(".")) {
            throw new IllegalArgumentException("Invalid email: domain must contain '.'");
        }

        return new EmailComponents(username, domain);
    }
}
