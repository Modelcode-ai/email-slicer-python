package com.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link EmailSlicer} covering valid parsing, invalid input
 * handling, edge cases with multiple {@code @} characters, whitespace
 * trimming, and empty/null inputs.
 */
class EmailSlicerTest {

    @Test
    void testValidEmailParsing() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parse("avimax37@gmail.com");

        assertNotNull(result);
        assertEquals("avimax37", result.getUsername());
        assertEquals("gmail.com", result.getDomain());
    }

    @Test
    void testInvalidEmailWithoutAt() {
        assertThrows(IllegalArgumentException.class, () ->
                EmailSlicer.parse("invalid-email")
        );
    }

    @Test
    void testMultipleAtCharacters() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parse("user@sub@domain.com");

        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals("sub@domain.com", result.getDomain());
    }

    @Test
    void testLeadingTrailingWhitespace() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parse("  user@example.com  ");

        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }

    @Test
    void testEmptyInput() {
        assertThrows(IllegalArgumentException.class, () ->
                EmailSlicer.parse("")
        );
    }

    @Test
    void testWhitespaceOnlyInput() {
        assertThrows(IllegalArgumentException.class, () ->
                EmailSlicer.parse("   ")
        );
    }

    @Test
    void testNullInput() {
        assertThrows(IllegalArgumentException.class, () ->
                EmailSlicer.parse(null)
        );
    }
}
