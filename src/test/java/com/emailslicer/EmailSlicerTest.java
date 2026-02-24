package com.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link EmailSlicer#slice(String)}.
 *
 * <p>These tests validate parsing semantics that mirror the original Python
 * {@code emailSlicer.py} script, covering valid emails, edge cases, and
 * invalid input.</p>
 */
class EmailSlicerTest {

    // ---- Valid email parsing ----

    @Test
    void sliceValidEmail() {
        EmailParts parts = EmailSlicer.slice("avimax37@gmail.com");

        assertEquals("avimax37", parts.username());
        assertEquals("gmail.com", parts.domain());
    }

    @Test
    void sliceValidEmailWithSubdomain() {
        EmailParts parts = EmailSlicer.slice("user@mail.example.org");

        assertEquals("user", parts.username());
        assertEquals("mail.example.org", parts.domain());
    }

    // ---- Whitespace handling ----

    @Test
    void sliceTrimsLeadingAndTrailingWhitespace() {
        EmailParts parts = EmailSlicer.slice("  avimax37@gmail.com  ");

        assertEquals("avimax37", parts.username());
        assertEquals("gmail.com", parts.domain());
    }

    @Test
    void sliceTrimsTabsAndNewlines() {
        EmailParts parts = EmailSlicer.slice("\t avimax37@gmail.com \n");

        assertEquals("avimax37", parts.username());
        assertEquals("gmail.com", parts.domain());
    }

    // ---- Multiple @ signs ----

    @Test
    void sliceWithMultipleAtSignsSplitsAtFirst() {
        EmailParts parts = EmailSlicer.slice("user@sub@domain.com");

        assertEquals("user", parts.username());
        assertEquals("sub@domain.com", parts.domain());
    }

    // ---- Empty username ----

    @Test
    void sliceWithEmptyUsername() {
        EmailParts parts = EmailSlicer.slice("@domain.com");

        assertEquals("", parts.username());
        assertEquals("domain.com", parts.domain());
    }

    // ---- Empty domain ----

    @Test
    void sliceWithEmptyDomain() {
        EmailParts parts = EmailSlicer.slice("user@");

        assertEquals("user", parts.username());
        assertEquals("", parts.domain());
    }

    // ---- At-sign only ----

    @Test
    void sliceWithOnlyAtSign() {
        EmailParts parts = EmailSlicer.slice("@");

        assertEquals("", parts.username());
        assertEquals("", parts.domain());
    }

    // ---- Invalid email (no @) ----

    @Test
    void sliceThrowsForEmailWithoutAtSign() {
        assertThrows(IllegalArgumentException.class,
                () -> EmailSlicer.slice("avimax37gmail.com"));
    }

    @Test
    void sliceThrowsForEmptyString() {
        assertThrows(IllegalArgumentException.class,
                () -> EmailSlicer.slice(""));
    }

    @Test
    void sliceThrowsForWhitespaceOnlyString() {
        assertThrows(IllegalArgumentException.class,
                () -> EmailSlicer.slice("   "));
    }

    // ---- Null input ----

    @Test
    void sliceThrowsForNullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> EmailSlicer.slice(null));
    }
}
