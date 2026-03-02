package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer}, verifying behavioural parity
 * with the original Python {@code emailSlicer.py} script.
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    // --- Valid email parsing ---

    @Test
    void parseValidEmail() {
        EmailParts parts = slicer.parse("avimax37@gmail.com");
        assertEquals("avimax37", parts.username());
        assertEquals("gmail.com", parts.domain());
    }

    @Test
    void parseEmailWithSubdomain() {
        EmailParts parts = slicer.parse("user@mail.example.co.uk");
        assertEquals("user", parts.username());
        assertEquals("mail.example.co.uk", parts.domain());
    }

    // --- Multiple @ characters ---

    @Test
    void parseEmailWithMultipleAtSymbols() {
        EmailParts parts = slicer.parse("user@sub@domain.com");
        assertEquals("user", parts.username());
        assertEquals("sub@domain.com", parts.domain());
    }

    // --- Whitespace handling ---

    @Test
    void parseEmailWithLeadingAndTrailingWhitespace() {
        EmailParts parts = slicer.parse("  user@example.com  ");
        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    @Test
    void parseEmailWithTabsAndSpaces() {
        EmailParts parts = slicer.parse("\t user@example.com \t");
        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    // --- Edge cases with @ at boundaries ---

    @Test
    void parseEmailWithAtAtStart() {
        EmailParts parts = slicer.parse("@example.com");
        assertEquals("", parts.username());
        assertEquals("example.com", parts.domain());
    }

    @Test
    void parseEmailWithAtAtEnd() {
        EmailParts parts = slicer.parse("user@");
        assertEquals("user", parts.username());
        assertEquals("", parts.domain());
    }

    // --- Invalid input ---

    @Test
    void parseEmailWithoutAtThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> slicer.parse("not-an-email")
        );
        assertNotNull(ex.getMessage());
    }

    @Test
    void parseEmptyStringThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> slicer.parse("")
        );
    }

    @Test
    void parseWhitespaceOnlyStringThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> slicer.parse("   ")
        );
    }

    @Test
    void parseNullInputThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> slicer.parse(null)
        );
    }
}
