package com.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for EmailSlicer parsing logic.
 */
class EmailSlicerTest {

    @Test
    void parsesStandardEmail() {
        EmailSlicer.ParsedEmail parsed = EmailSlicer.parse("user@domain.com");
        assertEquals("user", parsed.getUsername());
        assertEquals("domain.com", parsed.getDomain());
    }

    @Test
    void trimsWhitespace() {
        EmailSlicer.ParsedEmail parsed = EmailSlicer.parse("  user@domain.com  ");
        assertEquals("user", parsed.getUsername());
        assertEquals("domain.com", parsed.getDomain());
    }

    @Test
    void trimsLeadingWhitespaceOnly() {
        EmailSlicer.ParsedEmail parsed = EmailSlicer.parse("   user@domain.com");
        assertEquals("user", parsed.getUsername());
        assertEquals("domain.com", parsed.getDomain());
    }

    @Test
    void trimsTrailingWhitespaceOnly() {
        EmailSlicer.ParsedEmail parsed = EmailSlicer.parse("user@domain.com   ");
        assertEquals("user", parsed.getUsername());
        assertEquals("domain.com", parsed.getDomain());
    }

    @Test
    void throwsOnMissingAtSymbol() {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse("invalidemail"));
    }

    @Test
    void throwsOnEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse(""));
    }

    @Test
    void throwsOnWhitespaceOnly() {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse("   "));
    }

    @Test
    void throwsOnNullInput() {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse(null));
    }

    @Test
    void parsesEmailWithMultipleAtSymbols() {
        // Split at first @ only; domain may contain additional @ characters
        EmailSlicer.ParsedEmail parsed = EmailSlicer.parse("user@sub@domain.com");
        assertEquals("user", parsed.getUsername());
        assertEquals("sub@domain.com", parsed.getDomain());
    }

    @Test
    void throwsOnAtAtStart() {
        // @ at the first position is invalid
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse("@domain.com"));
    }

    @Test
    void throwsOnAtAtEnd() {
        // @ at the last position is invalid
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse("user@"));
    }

    @Test
    void throwsOnJustAtSymbol() {
        // Just "@" is invalid - @ is both at start and end
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse("@"));
    }

    @Test
    void parsesKnownExample() {
        // Verifies the exact example from the specification
        EmailSlicer.ParsedEmail parsed = EmailSlicer.parse("avimax37@gmail.com");
        assertEquals("avimax37", parsed.getUsername());
        assertEquals("gmail.com", parsed.getDomain());
    }

    @Test
    void parsesEmailWithSubdomain() {
        EmailSlicer.ParsedEmail parsed = EmailSlicer.parse("user@mail.example.com");
        assertEquals("user", parsed.getUsername());
        assertEquals("mail.example.com", parsed.getDomain());
    }

    @Test
    void parsesEmailWithNumericUsername() {
        EmailSlicer.ParsedEmail parsed = EmailSlicer.parse("12345@example.com");
        assertEquals("12345", parsed.getUsername());
        assertEquals("example.com", parsed.getDomain());
    }

    @Test
    void parsesEmailWithDotsInUsername() {
        EmailSlicer.ParsedEmail parsed = EmailSlicer.parse("first.last@example.com");
        assertEquals("first.last", parsed.getUsername());
        assertEquals("example.com", parsed.getDomain());
    }

    @Test
    void parsesEmailWithPlusInUsername() {
        EmailSlicer.ParsedEmail parsed = EmailSlicer.parse("user+tag@example.com");
        assertEquals("user+tag", parsed.getUsername());
        assertEquals("example.com", parsed.getDomain());
    }

    @Test
    void parsesEmailWithSingleCharacterUsername() {
        EmailSlicer.ParsedEmail parsed = EmailSlicer.parse("a@example.com");
        assertEquals("a", parsed.getUsername());
        assertEquals("example.com", parsed.getDomain());
    }

    @Test
    void parsesEmailWithSingleCharacterDomain() {
        EmailSlicer.ParsedEmail parsed = EmailSlicer.parse("user@x");
        assertEquals("user", parsed.getUsername());
        assertEquals("x", parsed.getDomain());
    }
}
