package com.emailslicer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for EmailSlicer parsing logic.
 *
 * Tests cover valid email parsing scenarios and all validation failure cases.
 */
class EmailSlicerTest {

    // ========== Valid Email Parsing Tests ==========

    @Test
    void testParseBasicEmail() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("avimax37@gmail.com");
        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    void testParseEmailWithSubdomain() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("user@mail.example.com");
        assertEquals("user", result.username());
        assertEquals("mail.example.com", result.domain());
    }

    @Test
    void testParseSingleCharacterUsername() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("a@b.com");
        assertEquals("a", result.username());
        assertEquals("b.com", result.domain());
    }

    @Test
    void testParseSingleCharacterDomain() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("user@d");
        assertEquals("user", result.username());
        assertEquals("d", result.domain());
    }

    @Test
    void testParseEmailWithPlusSign() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("test.user+tag@example.co.uk");
        assertEquals("test.user+tag", result.username());
        assertEquals("example.co.uk", result.domain());
    }

    @Test
    void testParseEmailWithDots() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("first.last@example.org");
        assertEquals("first.last", result.username());
        assertEquals("example.org", result.domain());
    }

    @Test
    void testParseEmailWithNumbers() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("user123@test456.com");
        assertEquals("user123", result.username());
        assertEquals("test456.com", result.domain());
    }

    // ========== Validation Failure Tests ==========

    @Test
    void testParseNullEmail() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> EmailSlicer.parseEmail(null)
        );
        assertEquals("email must not be empty", exception.getMessage());
    }

    @Test
    void testParseEmptyEmail() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> EmailSlicer.parseEmail("")
        );
        assertEquals("email must not be empty", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "\t", "\n", "   \t\n  "})
    void testParseWhitespaceOnlyEmail(String whitespace) {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> EmailSlicer.parseEmail(whitespace)
        );
        assertEquals("email must not be empty", exception.getMessage());
    }

    @Test
    void testParseEmailWithoutAtSymbol() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> EmailSlicer.parseEmail("invalid-email")
        );
        assertEquals("email must contain exactly one '@'", exception.getMessage());
    }

    @Test
    void testParseEmailWithoutAtSymbol2() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> EmailSlicer.parseEmail("user.example.com")
        );
        assertEquals("email must contain exactly one '@'", exception.getMessage());
    }

    @Test
    void testParseEmailWithMultipleAtSymbols() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> EmailSlicer.parseEmail("user@@example.com")
        );
        assertEquals("email must contain exactly one '@'", exception.getMessage());
    }

    @Test
    void testParseEmailWithMultipleAtSymbols2() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> EmailSlicer.parseEmail("user@domain@example.com")
        );
        assertEquals("email must contain exactly one '@'", exception.getMessage());
    }

    @Test
    void testParseEmailStartingWithAt() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> EmailSlicer.parseEmail("@example.com")
        );
        assertEquals("username part must not be empty", exception.getMessage());
    }

    @Test
    void testParseEmailEndingWithAt() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> EmailSlicer.parseEmail("user@")
        );
        assertEquals("domain part must not be empty", exception.getMessage());
    }

    @Test
    void testParseEmailOnlyAtSymbol() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> EmailSlicer.parseEmail("@")
        );
        // Will fail on empty username first
        assertEquals("username part must not be empty", exception.getMessage());
    }
}
