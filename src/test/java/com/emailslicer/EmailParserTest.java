package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for EmailParser to verify functional equivalence with the Python implementation.
 */
class EmailParserTest {

    private EmailParser parser;

    @BeforeEach
    void setUp() {
        parser = new EmailParser();
    }

    @Test
    void testValidEmailParsing() {
        EmailComponents result = parser.parse("avimax37@gmail.com");

        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    void testValidEmailWithUppercase() {
        EmailComponents result = parser.parse("John.Doe@Example.COM");

        assertEquals("John.Doe", result.username());
        assertEquals("Example.COM", result.domain());
    }

    @Test
    void testValidEmailWithNumbers() {
        EmailComponents result = parser.parse("user123@domain456.org");

        assertEquals("user123", result.username());
        assertEquals("domain456.org", result.domain());
    }

    @Test
    void testValidEmailWithSpecialCharacters() {
        EmailComponents result = parser.parse("user+tag@sub.domain.com");

        assertEquals("user+tag", result.username());
        assertEquals("sub.domain.com", result.domain());
    }

    @Test
    void testInvalidEmailNoAtSymbol() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse("example.com")
        );

        assertTrue(exception.getMessage().contains("@"));
    }

    @Test
    void testMultipleAtSymbols() {
        // Should split on the first @ only
        EmailComponents result = parser.parse("user@sub@domain.com");

        assertEquals("user", result.username());
        assertEquals("sub@domain.com", result.domain());
    }

    @Test
    void testWhitespaceTrimming() {
        // Whitespace should be trimmed before parsing (matching Python's strip())
        EmailComponents result = parser.parse("  avimax37@gmail.com  ");

        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    void testWhitespaceWithTabsAndNewlines() {
        EmailComponents result = parser.parse("\t user@domain.com \n");

        assertEquals("user", result.username());
        assertEquals("domain.com", result.domain());
    }

    @Test
    void testAtSymbolAtStart() {
        // @domain.com is valid: empty username, domain = "domain.com"
        EmailComponents result = parser.parse("@domain.com");

        assertEquals("", result.username());
        assertEquals("domain.com", result.domain());
    }

    @Test
    void testAtSymbolAtEnd() {
        // user@ is valid: username = "user", empty domain
        EmailComponents result = parser.parse("user@");

        assertEquals("user", result.username());
        assertEquals("", result.domain());
    }

    @Test
    void testJustAtSymbol() {
        // Just @ is valid: both username and domain are empty
        EmailComponents result = parser.parse("@");

        assertEquals("", result.username());
        assertEquals("", result.domain());
    }

    @Test
    void testEmptyInput() {
        // Empty string has no @ symbol
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse("")
        );

        assertTrue(exception.getMessage().contains("@"));
    }

    @Test
    void testWhitespaceOnlyInput() {
        // After trimming, becomes empty string (no @ symbol)
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse("   ")
        );

        assertTrue(exception.getMessage().contains("@"));
    }

    @Test
    void testNullInput() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse(null)
        );

        assertTrue(exception.getMessage().toLowerCase().contains("null"));
    }
}
