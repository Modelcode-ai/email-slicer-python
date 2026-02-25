package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer#slice(String)}.
 *
 * <p>Covers valid parsing, invalid input, multiple {@code @} characters,
 * whitespace handling, empty string, and null input.</p>
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    @Test
    void sliceValidEmail() {
        EmailSliceResult result = slicer.slice("avimax37@gmail.com");

        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    void sliceInvalidEmailNoAtSymbol() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("invalid.email.example"));
    }

    @Test
    void sliceEmailWithMultipleAtSymbols() {
        EmailSliceResult result = slicer.slice("user@sub@example.com");

        assertEquals("user", result.username());
        assertEquals("sub@example.com", result.domain());
    }

    @Test
    void sliceEmailWithLeadingAndTrailingWhitespace() {
        EmailSliceResult result = slicer.slice("   user@example.com  ");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    void sliceEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice(""));
    }

    @Test
    void sliceNullInput() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice(null));
    }

    @Test
    void sliceWhitespaceOnlyString() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("   "));
    }

    @Test
    void sliceEmailAtStart() {
        EmailSliceResult result = slicer.slice("@example.com");

        assertEquals("", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    void sliceEmailAtEnd() {
        EmailSliceResult result = slicer.slice("user@");

        assertEquals("user", result.username());
        assertEquals("", result.domain());
    }
}
