package com.modelcode.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for {@link EmailParser}.
 *
 * <p>Tests cover valid email formats, invalid formats, and edge cases
 * to ensure the parser correctly validates and extracts email components.
 */
class EmailParserTest {

    private EmailParser parser;

    @BeforeEach
    void setUp() {
        parser = new EmailParser();
    }

    // ========== Valid Email Format Tests ==========

    @Test
    @DisplayName("Should parse simple email address")
    void testParseSimpleEmail() {
        EmailParts result = parser.parse("user@example.com");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    @DisplayName("Should parse email with subdomain")
    void testParseEmailWithSubdomain() {
        EmailParts result = parser.parse("john.doe@sub.example.co.uk");

        assertEquals("john.doe", result.username());
        assertEquals("sub.example.co.uk", result.domain());
    }

    @Test
    @DisplayName("Should parse email with numeric username")
    void testParseEmailWithNumericUsername() {
        EmailParts result = parser.parse("12345@numbers.net");

        assertEquals("12345", result.username());
        assertEquals("numbers.net", result.domain());
    }

    @Test
    @DisplayName("Should parse email with plus sign in username")
    void testParseEmailWithPlusSign() {
        EmailParts result = parser.parse("user+tag@example.com");

        assertEquals("user+tag", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    @DisplayName("Should parse email with hyphen in domain")
    void testParseEmailWithHyphenInDomain() {
        EmailParts result = parser.parse("user@my-domain.com");

        assertEquals("user", result.username());
        assertEquals("my-domain.com", result.domain());
    }

    @Test
    @DisplayName("Should parse email with underscore in username")
    void testParseEmailWithUnderscore() {
        EmailParts result = parser.parse("user_name@example.com");

        assertEquals("user_name", result.username());
        assertEquals("example.com", result.domain());
    }

    // ========== Whitespace Handling Tests ==========

    @Test
    @DisplayName("Should trim leading whitespace from email")
    void testTrimLeadingWhitespace() {
        EmailParts result = parser.parse("  user@example.com");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    @DisplayName("Should trim trailing whitespace from email")
    void testTrimTrailingWhitespace() {
        EmailParts result = parser.parse("user@example.com  ");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    @DisplayName("Should trim both leading and trailing whitespace")
    void testTrimBothSides() {
        EmailParts result = parser.parse(" user@example.com ");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    @DisplayName("Should trim tab characters")
    void testTrimTabCharacters() {
        EmailParts result = parser.parse("\tuser@example.com\t");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    // ========== Invalid Email Format Tests ==========

    @Test
    @DisplayName("Should throw exception for null email")
    void testNullEmail() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> parser.parse(null)
        );

        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isEmpty());
        assertTrue(exception.getMessage().contains("null"));
    }

    @Test
    @DisplayName("Should throw exception for empty string")
    void testEmptyString() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> parser.parse("")
        );

        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isEmpty());
        assertTrue(exception.getMessage().contains("empty"));
    }

    @Test
    @DisplayName("Should throw exception for whitespace-only string")
    void testWhitespaceOnlyString() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> parser.parse("   ")
        );

        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isEmpty());
    }

    @Test
    @DisplayName("Should throw exception for email without @ symbol")
    void testNoAtSymbol() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> parser.parse("userexample.com")
        );

        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isEmpty());
        assertTrue(exception.getMessage().contains("@"));
    }

    @Test
    @DisplayName("Should throw exception for email with multiple @ symbols")
    void testMultipleAtSymbols() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> parser.parse("user@@example.com")
        );

        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isEmpty());
        assertTrue(exception.getMessage().contains("@"));
    }

    @Test
    @DisplayName("Should throw exception for email with three @ symbols")
    void testThreeAtSymbols() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> parser.parse("user@domain@example.com")
        );

        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isEmpty());
    }

    @Test
    @DisplayName("Should throw exception for email with @ at the beginning")
    void testAtSymbolAtBeginning() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> parser.parse("@example.com")
        );

        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isEmpty());
        assertTrue(exception.getMessage().contains("username"));
    }

    @Test
    @DisplayName("Should throw exception for email with @ at the end")
    void testAtSymbolAtEnd() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> parser.parse("user@")
        );

        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isEmpty());
        assertTrue(exception.getMessage().contains("domain"));
    }

    @Test
    @DisplayName("Should throw exception for email with only @ symbol")
    void testOnlyAtSymbol() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> parser.parse("@")
        );

        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isEmpty());
    }

    // ========== Edge Cases ==========

    @Test
    @DisplayName("Should parse email with single character username")
    void testSingleCharacterUsername() {
        EmailParts result = parser.parse("a@example.com");

        assertEquals("a", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    @DisplayName("Should parse email with single character domain")
    void testSingleCharacterDomain() {
        EmailParts result = parser.parse("user@x");

        assertEquals("user", result.username());
        assertEquals("x", result.domain());
    }

    @Test
    @DisplayName("Should parse minimal valid email")
    void testMinimalEmail() {
        EmailParts result = parser.parse("a@b");

        assertEquals("a", result.username());
        assertEquals("b", result.domain());
    }

    @Test
    @DisplayName("Should parse long email address")
    void testLongEmail() {
        String longUsername = "a".repeat(50);
        String longDomain = "b".repeat(50) + ".com";
        String email = longUsername + "@" + longDomain;

        EmailParts result = parser.parse(email);

        assertEquals(longUsername, result.username());
        assertEquals(longDomain, result.domain());
    }
}
