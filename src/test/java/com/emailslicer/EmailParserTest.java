package com.emailslicer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailParser}.
 */
class EmailParserTest {

    @Test
    @DisplayName("Should parse standard valid email correctly")
    void testStandardValidEmail() {
        EmailParser.ParsedEmail result = EmailParser.parse("user@example.com");

        assertEquals("user", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }

    @Test
    @DisplayName("Should split on first @ when multiple @ characters present")
    void testMultipleAtCharacters() {
        EmailParser.ParsedEmail result = EmailParser.parse("first@second@third");

        assertEquals("first", result.getUsername());
        assertEquals("second@third", result.getDomain());
    }

    @Test
    @DisplayName("Should allow empty username when email starts with @")
    void testEmptyUsername() {
        EmailParser.ParsedEmail result = EmailParser.parse("@example.com");

        assertEquals("", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }

    @Test
    @DisplayName("Should allow empty domain when email ends with @")
    void testEmptyDomain() {
        EmailParser.ParsedEmail result = EmailParser.parse("user@");

        assertEquals("user", result.getUsername());
        assertEquals("", result.getDomain());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when no @ is present")
    void testInvalidEmailNoAt() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> EmailParser.parse("invalidemail.com")
        );

        assertTrue(exception.getMessage().contains("@"));
    }

    @Test
    @DisplayName("Should handle email with only @ character")
    void testOnlyAtCharacter() {
        EmailParser.ParsedEmail result = EmailParser.parse("@");

        assertEquals("", result.getUsername());
        assertEquals("", result.getDomain());
    }

    @Test
    @DisplayName("Parser expects trimmed input - untrimmed input is handled by caller")
    void testWhitespaceTrimming() {
        // The parser expects already-trimmed input (per spec, trimming happens in EmailSlicerApp)
        // This test verifies behavior when trimmed input is provided
        EmailParser.ParsedEmail result = EmailParser.parse("user@example.com");

        assertEquals("user", result.getUsername());
        assertEquals("example.com", result.getDomain());

        // Note: If untrimmed input is passed, the parser will include whitespace in results
        // This is by design - the caller (EmailSlicerApp) is responsible for trimming
    }

    @Test
    @DisplayName("Should handle complex real-world email addresses")
    void testComplexEmail() {
        EmailParser.ParsedEmail result = EmailParser.parse("john.doe+tag@sub.example.co.uk");

        assertEquals("john.doe+tag", result.getUsername());
        assertEquals("sub.example.co.uk", result.getDomain());
    }

    @Test
    @DisplayName("Should throw for empty string")
    void testEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> EmailParser.parse(""));
    }

    @Test
    @DisplayName("Should throw for string with no @ but with dots")
    void testStringWithDotsNoAt() {
        assertThrows(IllegalArgumentException.class, () -> EmailParser.parse("user.name.example.com"));
    }
}
