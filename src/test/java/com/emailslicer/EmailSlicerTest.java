package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for EmailSlicer class.
 *
 * Tests cover valid emails, invalid emails, and edge cases to ensure
 * behavior parity with the original Python implementation.
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    @Test
    void testValidEmail() {
        EmailParts result = slicer.slice("user@domain.com");

        assertEquals("user", result.username());
        assertEquals("domain.com", result.domain());
    }

    @Test
    void testInvalidEmailNoAt() {
        assertThrows(IllegalArgumentException.class, () -> {
            slicer.slice("userdomain.com");
        });
    }

    @Test
    void testMultipleAtSymbols() {
        // Python's email.index("@") returns the first occurrence,
        // so "user@sub@domain.com" splits into username="user" and domain="sub@domain.com"
        EmailParts result = slicer.slice("user@sub@domain.com");

        assertEquals("user", result.username());
        assertEquals("sub@domain.com", result.domain());
    }

    @Test
    void testAtAtBeginning() {
        // Email like "@domain.com" produces empty username
        EmailParts result = slicer.slice("@domain.com");

        assertEquals("", result.username());
        assertEquals("domain.com", result.domain());
    }

    @Test
    void testAtAtEnd() {
        // Email like "user@" produces empty domain
        EmailParts result = slicer.slice("user@");

        assertEquals("user", result.username());
        assertEquals("", result.domain());
    }

    @Test
    void testWhitespaceTrimming() {
        // Leading and trailing whitespace should be trimmed before parsing
        EmailParts result = slicer.slice("  user@domain.com  ");

        assertEquals("user", result.username());
        assertEquals("domain.com", result.domain());
    }

    @Test
    void testEmptyStringThrows() {
        // Empty string has no @ symbol
        assertThrows(IllegalArgumentException.class, () -> {
            slicer.slice("");
        });
    }

    @Test
    void testOnlyWhitespaceThrows() {
        // Whitespace-only input, after trimming, has no @ symbol
        assertThrows(IllegalArgumentException.class, () -> {
            slicer.slice("   ");
        });
    }

    @Test
    void testSingleAtSymbol() {
        // Just "@" produces empty username and empty domain
        EmailParts result = slicer.slice("@");

        assertEquals("", result.username());
        assertEquals("", result.domain());
    }
}
