package com.modelcode.emailslicer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailParserTest {

    private final EmailParser parser = new EmailParser();

    // Valid email test cases
    @Test
    @DisplayName("Parses simple valid email")
    void parsesSimpleValidEmail() {
        EmailComponents result = parser.parse("avimax37@gmail.com");
        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    @DisplayName("Parses email with subdomain")
    void parsesEmailWithSubdomain() {
        EmailComponents result = parser.parse("user@mail.example.org");
        assertEquals("user", result.username());
        assertEquals("mail.example.org", result.domain());
    }

    @Test
    @DisplayName("Parses email with surrounding whitespace")
    void parsesEmailWithSurroundingWhitespace() {
        EmailComponents result = parser.parse("  user@example.com  ");
        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    @DisplayName("Parses email with special characters in local part")
    void parsesEmailWithSpecialCharacters() {
        EmailComponents result = parser.parse("user.name+tag@example.com");
        assertEquals("user.name+tag", result.username());
        assertEquals("example.com", result.domain());
    }

    // Invalid input test cases
    @Test
    @DisplayName("Throws exception for null input")
    void throwsOnNullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse(null));
    }

    @Test
    @DisplayName("Throws exception for empty string")
    void throwsOnEmptyString() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse(""));
    }

    @Test
    @DisplayName("Throws exception for blank string")
    void throwsOnBlankString() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse("   "));
    }

    @Test
    @DisplayName("Throws exception for missing @ symbol")
    void throwsOnMissingAtSymbol() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse("invalid.email"));
    }

    @Test
    @DisplayName("Throws exception for multiple @ symbols (adjacent)")
    void throwsOnMultipleAtSymbolsAdjacent() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse("user@@example.com"));
    }

    @Test
    @DisplayName("Throws exception for multiple @ symbols (separated)")
    void throwsOnMultipleAtSymbolsSeparated() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse("user@domain@com"));
    }

    @Test
    @DisplayName("Throws exception for empty username")
    void throwsOnEmptyUsername() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse("@example.com"));
    }

    @Test
    @DisplayName("Throws exception for empty domain")
    void throwsOnEmptyDomain() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse("user@"));
    }

    @Test
    @DisplayName("Throws exception for domain without dot")
    void throwsOnDomainWithoutDot() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse("user@example"));
    }
}
