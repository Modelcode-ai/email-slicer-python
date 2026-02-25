package com.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer} core parsing logic.
 */
class EmailSlicerTest {

    @Test
    void parseValidEmailSimpleCase() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parse("avimax37@gmail.com");
        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    void parseEmailWithMultipleAtCharacters() {
        // Python's index("@") finds the first @, so "user" is before the first @
        // and "sub@domain.com" is everything after the first @
        EmailSlicer.ParsedEmail result = EmailSlicer.parse("user@sub@domain.com");
        assertEquals("user", result.username());
        assertEquals("sub@domain.com", result.domain());
    }

    @Test
    void parseEmailWithEmptyUsername() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parse("@domain.com");
        assertEquals("", result.username());
        assertEquals("domain.com", result.domain());
    }

    @Test
    void parseEmailWithEmptyDomain() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parse("user@");
        assertEquals("user", result.username());
        assertEquals("", result.domain());
    }

    @Test
    void parseEmailWithLeadingAndTrailingWhitespace() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parse("  user@example.com  ");
        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    void parseInvalidEmailNoAtCharacter() {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse("invalid-email"));
    }

    @Test
    void parseEmailAtOnly() {
        // Just "@" is valid (has @): username="" and domain=""
        EmailSlicer.ParsedEmail result = EmailSlicer.parse("@");
        assertEquals("", result.username());
        assertEquals("", result.domain());
    }

    @Test
    void parseEmptyStringThrows() {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse(""));
    }

    @Test
    void parseWhitespaceOnlyStringThrows() {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse("   "));
    }
}
