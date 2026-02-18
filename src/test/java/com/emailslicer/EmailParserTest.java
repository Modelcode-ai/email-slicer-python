package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for EmailParser class.
 * Tests cover valid email parsing, validation failures, edge cases, and whitespace handling.
 */
class EmailParserTest {

    private EmailParser parser;

    @BeforeEach
    void setUp() {
        parser = new EmailParser();
    }

    // ========== Valid Email Test Cases ==========

    @Test
    void parse_validEmail_returnsCorrectUsernameAndDomain() {
        // Test with the example from the original Python README
        EmailResult result = parser.parse("avimax37@gmail.com");

        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    void parse_simpleValidEmail_returnsCorrectParts() {
        EmailResult result = parser.parse("user@example.com");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    void parse_singleCharUsername_returnsCorrectParts() {
        EmailResult result = parser.parse("a@domain.com");

        assertEquals("a", result.username());
        assertEquals("domain.com", result.domain());
    }

    @Test
    void parse_complexValidEmail_returnsCorrectParts() {
        // Email with dots, plus sign, and subdomain
        EmailResult result = parser.parse("user.name+tag@sub.domain.co");

        assertEquals("user.name+tag", result.username());
        assertEquals("sub.domain.co", result.domain());
    }

    @Test
    void parse_emailWithWhitespace_trimsAndReturnsCorrectParts() {
        // Test whitespace trimming to match Python's strip() behavior
        EmailResult result = parser.parse("  user@example.com  ");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    void parse_emailWithTabsAndSpaces_trimsCorrectly() {
        EmailResult result = parser.parse("\t  user@example.com \t ");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    // ========== Validation Failure Test Cases ==========

    @Test
    void parse_noAtSymbol_throwsValidationException() {
        EmailValidationException exception = assertThrows(
            EmailValidationException.class,
            () -> parser.parse("userdomain.com")
        );

        assertTrue(exception.getMessage().contains("@"));
    }

    @Test
    void parse_multipleAtSymbols_throwsValidationException() {
        // Test with two consecutive @ symbols
        EmailValidationException exception1 = assertThrows(
            EmailValidationException.class,
            () -> parser.parse("user@@example.com")
        );

        assertTrue(exception1.getMessage().contains("exactly one"));

        // Test with @ in both username and domain
        EmailValidationException exception2 = assertThrows(
            EmailValidationException.class,
            () -> parser.parse("@user@example.com")
        );

        assertTrue(exception2.getMessage().contains("exactly one"));
    }

    @Test
    void parse_emptyUsername_throwsValidationException() {
        EmailValidationException exception = assertThrows(
            EmailValidationException.class,
            () -> parser.parse("@example.com")
        );

        assertTrue(exception.getMessage().contains("before"));
    }

    @Test
    void parse_emptyDomain_throwsValidationException() {
        EmailValidationException exception = assertThrows(
            EmailValidationException.class,
            () -> parser.parse("user@")
        );

        assertTrue(exception.getMessage().contains("after"));
    }

    @Test
    void parse_emptyString_throwsValidationException() {
        EmailValidationException exception = assertThrows(
            EmailValidationException.class,
            () -> parser.parse("")
        );

        assertTrue(exception.getMessage().contains("empty"));
    }

    @Test
    void parse_whitespaceOnly_throwsValidationException() {
        EmailValidationException exception = assertThrows(
            EmailValidationException.class,
            () -> parser.parse("   ")
        );

        assertTrue(exception.getMessage().contains("empty"));
    }

    @Test
    void parse_nullInput_throwsValidationException() {
        EmailValidationException exception = assertThrows(
            EmailValidationException.class,
            () -> parser.parse(null)
        );

        assertTrue(exception.getMessage().contains("null"));
    }

    // ========== Additional Edge Cases ==========

    @Test
    void parse_singleCharDomain_returnsCorrectParts() {
        // While not a realistic email, it should parse correctly
        EmailResult result = parser.parse("user@d");

        assertEquals("user", result.username());
        assertEquals("d", result.domain());
    }

    @Test
    void parse_emailWithNumbers_returnsCorrectParts() {
        EmailResult result = parser.parse("user123@domain456.com");

        assertEquals("user123", result.username());
        assertEquals("domain456.com", result.domain());
    }

    @Test
    void parse_emailWithHyphens_returnsCorrectParts() {
        EmailResult result = parser.parse("first-last@my-domain.com");

        assertEquals("first-last", result.username());
        assertEquals("my-domain.com", result.domain());
    }

    @Test
    void parse_emailWithUnderscores_returnsCorrectParts() {
        EmailResult result = parser.parse("user_name@example_domain.com");

        assertEquals("user_name", result.username());
        assertEquals("example_domain.com", result.domain());
    }

    // ========== EmailResult Record Tests ==========

    @Test
    void emailResult_equality_worksCorrectly() {
        EmailResult result1 = new EmailResult("user", "domain.com");
        EmailResult result2 = new EmailResult("user", "domain.com");
        EmailResult result3 = new EmailResult("other", "domain.com");

        // Test equality (records auto-generate equals)
        assertEquals(result1, result2);
        assertNotEquals(result1, result3);
    }

    @Test
    void emailResult_toString_containsComponents() {
        EmailResult result = new EmailResult("user", "domain.com");
        String toString = result.toString();

        // Records auto-generate toString
        assertTrue(toString.contains("user"));
        assertTrue(toString.contains("domain.com"));
    }
}
