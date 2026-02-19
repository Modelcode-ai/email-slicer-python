package com.emailslicer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link EmailValidator}.
 *
 * <p>Covers all basic-plus validation rules including null, empty,
 * whitespace, missing '@', '@' at boundaries, and multiple '@' cases.
 */
class EmailValidatorTest {

    @ParameterizedTest(name = "valid email: \"{0}\"")
    @DisplayName("Should accept valid email addresses")
    @ValueSource(strings = {
            "avimax37@gmail.com",
            "user@mail.example.com",
            "john.doe123@company.co.uk",
            "a@b",
            " a@b ",
            "  user@domain.com  ",
            "test.user+tag@example.org",
            "x@y"
    })
    void shouldAcceptValidEmails(String email) {
        assertTrue(EmailValidator.isValid(email),
                "Expected valid: " + email);
    }

    @ParameterizedTest(name = "invalid email: null")
    @DisplayName("Should reject null input")
    @NullSource
    void shouldRejectNull(String email) {
        assertFalse(EmailValidator.isValid(email),
                "Expected invalid: null");
    }

    @ParameterizedTest(name = "invalid email: \"{0}\"")
    @DisplayName("Should reject invalid email addresses")
    @ValueSource(strings = {
            "",
            "   ",
            "user.domain.com",
            "nodomain",
            "@domain.com",
            "user@",
            "@",
            "user@@example.com",
            "a@b@c",
            "@@",
            "user@domain@extra.com"
    })
    void shouldRejectInvalidEmails(String email) {
        assertFalse(EmailValidator.isValid(email),
                "Expected invalid: " + email);
    }
}
