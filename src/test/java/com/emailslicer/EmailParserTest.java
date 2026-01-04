package com.emailslicer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for EmailParser.
 * Tests all validation rules and parsing scenarios including:
 * - Valid email formats with various structures
 * - Invalid inputs (null, empty, malformed)
 * - Edge cases (whitespace, special characters)
 */
class EmailParserTest {

    /**
     * Tests parsing of valid email addresses with various formats.
     * Uses parameterized testing to efficiently test multiple cases.
     */
    @ParameterizedTest
    @CsvSource({
            "avimax37@gmail.com, avimax37, gmail.com",
            "user.name@domain.co.uk, user.name, domain.co.uk",
            "test+tag@example.org, test+tag, example.org",
            "user123@sub.domain.com, user123, sub.domain.com",
            "first.last+tag@company.co.uk, first.last+tag, company.co.uk"
    })
    void parsesValidEmails(final String input, final String expectedUsername, final String expectedDomain) {
        final EmailParser.ParsedEmail result = EmailParser.parse(input);
        assertEquals(expectedUsername, result.username());
        assertEquals(expectedDomain, result.domain());
    }

    /**
     * Tests that emails with surrounding whitespace are trimmed and parsed correctly.
     */
    @Test
    void parsesEmailWithSurroundingWhitespace() {
        final EmailParser.ParsedEmail result = EmailParser.parse("  user@example.com  ");
        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    /**
     * Tests that null input throws ValidationException.
     */
    @Test
    void throwsExceptionForNullInput() {
        final Exception exception = assertThrows(NullPointerException.class, () -> {
            EmailParser.parse(null);
        });
        assertTrue(exception.getMessage().contains("Email must not be null"));
    }

    /**
     * Tests that empty string throws ValidationException.
     */
    @Test
    void throwsExceptionForEmptyInput() {
        final ValidationException exception = assertThrows(ValidationException.class, () -> {
            EmailParser.parse("");
        });
        assertEquals("Email must not be empty", exception.getMessage());
    }

    /**
     * Tests that whitespace-only string throws ValidationException.
     */
    @Test
    void throwsExceptionForWhitespaceOnlyInput() {
        final ValidationException exception = assertThrows(ValidationException.class, () -> {
            EmailParser.parse("   ");
        });
        assertEquals("Email must not be empty", exception.getMessage());
    }

    /**
     * Tests that emails without @ symbol throw ValidationException.
     */
    @ParameterizedTest
    @ValueSource(strings = {"userexample.com", "user.example.com", "plaintext"})
    void throwsExceptionForMissingAtSymbol(final String input) {
        final ValidationException exception = assertThrows(ValidationException.class, () -> {
            EmailParser.parse(input);
        });
        assertEquals("Email format is invalid", exception.getMessage());
    }

    /**
     * Tests that emails with multiple @ symbols throw ValidationException.
     */
    @ParameterizedTest
    @ValueSource(strings = {"user@@example.com", "user@domain@example.com", "@@example.com"})
    void throwsExceptionForMultipleAtSymbols(final String input) {
        final ValidationException exception = assertThrows(ValidationException.class, () -> {
            EmailParser.parse(input);
        });
        assertEquals("Email format is invalid", exception.getMessage());
    }

    /**
     * Tests that emails without a dot in domain throw ValidationException.
     */
    @ParameterizedTest
    @ValueSource(strings = {"user@example", "user@localhost", "test@domain"})
    void throwsExceptionForNoDomainDot(final String input) {
        final ValidationException exception = assertThrows(ValidationException.class, () -> {
            EmailParser.parse(input);
        });
        assertEquals("Email format is invalid", exception.getMessage());
    }

    /**
     * Tests that emails with missing username throw ValidationException.
     */
    @ParameterizedTest
    @ValueSource(strings = {"@example.com", "@domain.org", "@test.co.uk"})
    void throwsExceptionForMissingUsername(final String input) {
        final ValidationException exception = assertThrows(ValidationException.class, () -> {
            EmailParser.parse(input);
        });
        assertEquals("Email format is invalid", exception.getMessage());
    }

    /**
     * Tests that emails with missing domain throw ValidationException.
     */
    @ParameterizedTest
    @ValueSource(strings = {"user@", "test@", "name@"})
    void throwsExceptionForMissingDomain(final String input) {
        final ValidationException exception = assertThrows(ValidationException.class, () -> {
            EmailParser.parse(input);
        });
        assertEquals("Email format is invalid", exception.getMessage());
    }

    /**
     * Tests that emails with embedded whitespace throw ValidationException.
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "user name@example.com",
            "user@exa mple.com",
            "user @example.com",
            "user@ example.com",
            "user name@domain name.com"
    })
    void throwsExceptionForEmbeddedWhitespace(final String input) {
        final ValidationException exception = assertThrows(ValidationException.class, () -> {
            EmailParser.parse(input);
        });
        assertEquals("Email format is invalid", exception.getMessage());
    }

    /**
     * Tests ParsedEmail record equality and toString.
     */
    @Test
    void parsedEmailRecordBehavior() {
        final EmailParser.ParsedEmail email1 = new EmailParser.ParsedEmail("user", "example.com");
        final EmailParser.ParsedEmail email2 = new EmailParser.ParsedEmail("user", "example.com");
        final EmailParser.ParsedEmail email3 = new EmailParser.ParsedEmail("other", "example.com");

        // Test equality
        assertEquals(email1, email2);
        assertNotEquals(email1, email3);

        // Test hashCode consistency
        assertEquals(email1.hashCode(), email2.hashCode());

        // Test toString contains both components
        final String toString = email1.toString();
        assertTrue(toString.contains("user"));
        assertTrue(toString.contains("example.com"));
    }
}
