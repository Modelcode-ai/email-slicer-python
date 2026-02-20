package com.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer} covering valid parsing, whitespace
 * handling, invalid input rejection, and edge cases.
 */
class EmailSlicerTest {

    private final EmailSlicer slicer = new EmailSlicer();

    // ---- Valid email parsing ----

    @Test
    void parsesStandardEmail() {
        EmailParts parts = slicer.parse("avimax37@gmail.com");
        assertEquals("avimax37", parts.username());
        assertEquals("gmail.com", parts.domain());
    }

    @Test
    void parsesEmailWithSubdomain() {
        EmailParts parts = slicer.parse("user.name@sub.domain.com");
        assertEquals("user.name", parts.username());
        assertEquals("sub.domain.com", parts.domain());
    }

    @Test
    void parsesMinimalValidEmail() {
        EmailParts parts = slicer.parse("a@b");
        assertEquals("a", parts.username());
        assertEquals("b", parts.domain());
    }

    // ---- Whitespace handling ----

    @Test
    void trimsLeadingAndTrailingWhitespace() {
        EmailParts parts = slicer.parse("  user@example.com  ");
        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    @Test
    void trimsLeadingWhitespace() {
        EmailParts parts = slicer.parse("  user@example.com");
        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    @Test
    void trimsTrailingWhitespace() {
        EmailParts parts = slicer.parse("user@example.com  ");
        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    // ---- Invalid input rejection ----

    @Test
    void rejectsEmailWithoutAtSymbol() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> slicer.parse("invalid"));
        assertTrue(ex.getMessage().contains("Invalid email"));
    }

    @Test
    void rejectsEmailWithMultipleAtSymbols() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> slicer.parse("user@@example.com"));
        assertTrue(ex.getMessage().contains("Invalid email"));
    }

    @Test
    void rejectsEmailWithAtInUsernameAndDomain() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> slicer.parse("user@name@example.com"));
        assertTrue(ex.getMessage().contains("Invalid email"));
    }

    @Test
    void rejectsEmptyUsername() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> slicer.parse("@example.com"));
        assertTrue(ex.getMessage().contains("Invalid email"));
    }

    @Test
    void rejectsEmptyDomain() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> slicer.parse("user@"));
        assertTrue(ex.getMessage().contains("Invalid email"));
    }

    @Test
    void rejectsNullInput() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> slicer.parse(null));
        assertTrue(ex.getMessage().contains("Invalid email"));
    }

    @Test
    void rejectsEmptyString() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> slicer.parse(""));
        assertTrue(ex.getMessage().contains("Invalid email"));
    }

    @Test
    void rejectsWhitespaceOnlyString() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> slicer.parse("   "));
        assertTrue(ex.getMessage().contains("Invalid email"));
    }
}
