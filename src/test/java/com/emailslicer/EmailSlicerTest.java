package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer}, verifying behavioral parity with the
 * original Python implementation.
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    @Test
    void validEmail_typicalCase() {
        Optional<EmailSliceResult> result = slicer.slice("avimax37@gmail.com");

        assertTrue(result.isPresent());
        assertEquals("avimax37", result.get().username());
        assertEquals("gmail.com", result.get().domain());
    }

    @Test
    void validEmail_withLeadingAndTrailingWhitespace() {
        Optional<EmailSliceResult> result = slicer.slice("   avimax37@gmail.com  \n");

        assertTrue(result.isPresent());
        assertEquals("avimax37", result.get().username());
        assertEquals("gmail.com", result.get().domain());
    }

    @Test
    void invalidEmail_noAtSymbol() {
        Optional<EmailSliceResult> result = slicer.slice("invalid.email.example.com");

        assertTrue(result.isEmpty());
    }

    @Test
    void invalidEmail_emptyString() {
        Optional<EmailSliceResult> result = slicer.slice("");

        assertTrue(result.isEmpty());
    }

    @Test
    void invalidEmail_blankWhitespaceOnly() {
        Optional<EmailSliceResult> result = slicer.slice("   ");

        assertTrue(result.isEmpty());
    }

    @Test
    void edgeCase_atAtStart() {
        Optional<EmailSliceResult> result = slicer.slice("@gmail.com");

        assertTrue(result.isPresent());
        assertEquals("", result.get().username());
        assertEquals("gmail.com", result.get().domain());
    }

    @Test
    void edgeCase_atAtEnd() {
        Optional<EmailSliceResult> result = slicer.slice("user@");

        assertTrue(result.isPresent());
        assertEquals("user", result.get().username());
        assertEquals("", result.get().domain());
    }

    @Test
    void edgeCase_multipleAtCharacters() {
        // First @ is the split point, matching Python's index("@") behavior
        Optional<EmailSliceResult> result = slicer.slice("a@b@c.com");

        assertTrue(result.isPresent());
        assertEquals("a", result.get().username());
        assertEquals("b@c.com", result.get().domain());
    }

    @Test
    void edgeCase_onlyAtSymbol() {
        Optional<EmailSliceResult> result = slicer.slice("@");

        assertTrue(result.isPresent());
        assertEquals("", result.get().username());
        assertEquals("", result.get().domain());
    }

    @Test
    void nullInput_returnsEmpty() {
        Optional<EmailSliceResult> result = slicer.slice(null);

        assertTrue(result.isEmpty());
    }
}
