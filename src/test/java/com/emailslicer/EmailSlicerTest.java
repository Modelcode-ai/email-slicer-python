package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for {@link EmailSlicer}. Covers valid email parsing,
 * invalid email rejection, and edge cases to ensure behavioral parity
 * with the original Python script.
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    // ---- Valid email parsing ----

    @Test
    void sliceValidEmail() {
        EmailSlicer.EmailParts parts = slicer.slice("avimax37@gmail.com");
        assertEquals("avimax37", parts.username());
        assertEquals("gmail.com", parts.domain());
    }

    @Test
    void sliceAnotherValidEmail() {
        EmailSlicer.EmailParts parts = slicer.slice("user@example.com");
        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    @Test
    void sliceEmailWithPlusTag() {
        EmailSlicer.EmailParts parts = slicer.slice("name+tag@domain.co");
        assertEquals("name+tag", parts.username());
        assertEquals("domain.co", parts.domain());
    }

    // ---- Invalid email handling ----

    @Test
    void sliceInvalidEmailWithoutAt() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("invalidemail"));
    }

    @Test
    void sliceInvalidEmailWithDotOnly() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("foo.bar"));
    }

    // ---- Edge cases ----

    @Test
    void sliceEmailWithMultipleAtSigns() {
        // First @ wins: username is "a", domain is "b@c.com"
        EmailSlicer.EmailParts parts = slicer.slice("a@b@c.com");
        assertEquals("a", parts.username());
        assertEquals("b@c.com", parts.domain());
    }

    @Test
    void sliceEmailWithLeadingAndTrailingWhitespace() {
        EmailSlicer.EmailParts parts = slicer.slice("  user@example.com  ");
        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    @Test
    void sliceEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice(""));
    }

    @Test
    void sliceWhitespaceOnlyString() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("   "));
    }

    @Test
    void sliceEmailWithAtAtBeginning() {
        // "@domain.com" → username is "", domain is "domain.com"
        EmailSlicer.EmailParts parts = slicer.slice("@domain.com");
        assertEquals("", parts.username());
        assertEquals("domain.com", parts.domain());
    }

    @Test
    void sliceEmailWithAtAtEnd() {
        // "user@" → username is "user", domain is ""
        EmailSlicer.EmailParts parts = slicer.slice("user@");
        assertEquals("user", parts.username());
        assertEquals("", parts.domain());
    }
}
