package com.emailslicer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for EmailParser.
 */
class EmailParserTest {

    @Test
    @DisplayName("Standard valid email should be parsed correctly")
    void testStandardValidEmail() {
        EmailParser.ParsedEmail result = EmailParser.parse("user@example.com");

        assertEquals("user", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }

    @Test
    @DisplayName("Email with multiple @ characters should split on first @")
    void testMultipleAtCharacters() {
        EmailParser.ParsedEmail result = EmailParser.parse("first@second@third");

        assertEquals("first", result.getUsername());
        assertEquals("second@third", result.getDomain());
    }

    @Test
    @DisplayName("Email with empty username should be allowed")
    void testEmptyUsername() {
        EmailParser.ParsedEmail result = EmailParser.parse("@example.com");

        assertEquals("", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }

    @Test
    @DisplayName("Email with empty domain should be allowed")
    void testEmptyDomain() {
        EmailParser.ParsedEmail result = EmailParser.parse("user@");

        assertEquals("user", result.getUsername());
        assertEquals("", result.getDomain());
    }

    @Test
    @DisplayName("Email without @ character should throw IllegalArgumentException")
    void testInvalidEmailNoAtCharacter() {
        assertThrows(IllegalArgumentException.class, () -> {
            EmailParser.parse("invalidemail.com");
        });
    }

    @Test
    @DisplayName("Null email should throw IllegalArgumentException")
    void testNullEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            EmailParser.parse(null);
        });
    }

    @Test
    @DisplayName("Empty string should throw IllegalArgumentException")
    void testEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> {
            EmailParser.parse("");
        });
    }

    @Test
    @DisplayName("Email with only @ character should have empty username and domain")
    void testOnlyAtCharacter() {
        EmailParser.ParsedEmail result = EmailParser.parse("@");

        assertEquals("", result.getUsername());
        assertEquals("", result.getDomain());
    }

    @Test
    @DisplayName("Email with spaces in username should preserve spaces")
    void testSpacesInUsername() {
        EmailParser.ParsedEmail result = EmailParser.parse("user name@example.com");

        assertEquals("user name", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }

    @Test
    @DisplayName("Trimmed email should be parsed correctly")
    void testTrimmedEmail() {
        // Note: Trimming is done in EmailSlicerApp before calling parse
        // This test verifies that parse works with already trimmed input
        String trimmedEmail = "  user@example.com  ".trim();
        EmailParser.ParsedEmail result = EmailParser.parse(trimmedEmail);

        assertEquals("user", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }
}
