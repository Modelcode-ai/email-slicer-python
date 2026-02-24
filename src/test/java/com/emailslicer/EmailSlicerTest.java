package com.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link EmailSlicer} core parsing logic.
 */
class EmailSlicerTest {

    // ---- Valid email parsing ----

    @Test
    void parseStandardEmail() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parse("avimax37@gmail.com");
        assertEquals("avimax37", result.getUsername());
        assertEquals("gmail.com", result.getDomain());
    }

    @Test
    void parseEmailWithSubdomain() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parse("user@mail.company.co.uk");
        assertEquals("user", result.getUsername());
        assertEquals("mail.company.co.uk", result.getDomain());
    }

    // ---- Whitespace handling ----

    @Test
    void parseEmailWithLeadingAndTrailingWhitespace() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parse("  user@example.com  ");
        assertEquals("user", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }

    // ---- Invalid input: missing '@' ----

    @Test
    void parseMissingAtCharacterThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("invalid-email")
        );
        assertEquals("Invalid email address: missing '@' character.", ex.getMessage());
    }

    // ---- Invalid input: null ----

    @Test
    void parseNullInputThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse(null)
        );
        assertEquals("Invalid email address: input is empty.", ex.getMessage());
    }

    // ---- Invalid input: empty string ----

    @Test
    void parseEmptyStringThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("")
        );
        assertEquals("Invalid email address: input is empty.", ex.getMessage());
    }

    @Test
    void parseWhitespaceOnlyStringThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("   ")
        );
        assertEquals("Invalid email address: input is empty.", ex.getMessage());
    }

    // ---- Edge cases: empty username or domain ----

    @Test
    void parseEmailWithEmptyUsername() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parse("@example.com");
        assertEquals("", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }

    @Test
    void parseEmailWithEmptyDomain() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parse("user@");
        assertEquals("user", result.getUsername());
        assertEquals("", result.getDomain());
    }

    // ---- Edge case: multiple '@' characters ----

    @Test
    void parseEmailWithMultipleAtCharactersSplitsAtFirst() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parse("user@domain@extra");
        assertEquals("user", result.getUsername());
        assertEquals("domain@extra", result.getDomain());
    }
}
