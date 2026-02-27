package com.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer#parse(String)}.
 *
 * <p>Covers valid emails, whitespace handling, multiple '@' characters,
 * missing '@', empty/whitespace-only input, and null input.</p>
 */
class EmailSlicerTest {

    private final EmailSlicer slicer = new EmailSlicer();

    @Test
    void parseValidEmail() {
        EmailSlicer.Result result = slicer.parse("avimax37@gmail.com");
        assertNotNull(result);
        assertEquals("avimax37", result.getUsername());
        assertEquals("gmail.com", result.getDomain());
    }

    @Test
    void parseEmailWithWhitespace() {
        EmailSlicer.Result result = slicer.parse("  user@example.com  ");
        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }

    @Test
    void parseEmailWithMultipleAtSigns() {
        EmailSlicer.Result result = slicer.parse("user@host@domain.com");
        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals("host@domain.com", result.getDomain());
    }

    @Test
    void parseEmailWithNoAtSign() {
        EmailSlicer.Result result = slicer.parse("invalid-email");
        assertNull(result);
    }

    @Test
    void parseEmptyString() {
        EmailSlicer.Result result = slicer.parse("");
        assertNull(result);
    }

    @Test
    void parseWhitespaceOnly() {
        EmailSlicer.Result result = slicer.parse("   ");
        assertNull(result);
    }

    @Test
    void parseNullInput() {
        EmailSlicer.Result result = slicer.parse(null);
        assertNull(result);
    }

    @Test
    void parseEmailAtStart() {
        // Edge case: '@' is the first character → empty username
        EmailSlicer.Result result = slicer.parse("@domain.com");
        assertNotNull(result);
        assertEquals("", result.getUsername());
        assertEquals("domain.com", result.getDomain());
    }

    @Test
    void parseEmailAtEnd() {
        // Edge case: '@' is the last character → empty domain
        EmailSlicer.Result result = slicer.parse("user@");
        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals("", result.getDomain());
    }
}
