package com.emailslicer;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer#parseEmail(String)}.
 *
 * <p>Tests cover the same scenarios as the original Python script:
 * valid email, invalid (no {@code @}), whitespace trimming, multiple
 * {@code @} signs, and empty/whitespace-only input.</p>
 */
class EmailSlicerTest {

    @Test
    void parseEmail_validEmail_returnsUserNameAndDomain() {
        Optional<EmailParts> result = EmailSlicer.parseEmail("avimax37@gmail.com");

        assertTrue(result.isPresent(), "Expected a valid parse result");
        assertEquals("avimax37", result.get().username());
        assertEquals("gmail.com", result.get().domain());
    }

    @Test
    void parseEmail_noAtSymbol_returnsEmpty() {
        Optional<EmailParts> result = EmailSlicer.parseEmail("invalid.email.example");

        assertTrue(result.isEmpty(), "Expected empty for input without @");
    }

    @Test
    void parseEmail_withSurroundingWhitespace_trimsThenParses() {
        Optional<EmailParts> result = EmailSlicer.parseEmail("   avimax37@gmail.com  ");

        assertTrue(result.isPresent(), "Expected a valid parse result after trimming");
        assertEquals("avimax37", result.get().username());
        assertEquals("gmail.com", result.get().domain());
    }

    @Test
    void parseEmail_multipleAtSigns_splitsOnFirst() {
        Optional<EmailParts> result = EmailSlicer.parseEmail("user@sub@domain.com");

        assertTrue(result.isPresent(), "Expected a valid parse result for multiple @ signs");
        assertEquals("user", result.get().username());
        assertEquals("sub@domain.com", result.get().domain());
    }

    @Test
    void parseEmail_emptyString_returnsEmpty() {
        Optional<EmailParts> result = EmailSlicer.parseEmail("");

        assertTrue(result.isEmpty(), "Expected empty for blank input");
    }

    @Test
    void parseEmail_whitespaceOnly_returnsEmpty() {
        Optional<EmailParts> result = EmailSlicer.parseEmail("   ");

        assertTrue(result.isEmpty(), "Expected empty for whitespace-only input");
    }

    @Test
    void parseEmail_nullInput_returnsEmpty() {
        Optional<EmailParts> result = EmailSlicer.parseEmail(null);

        assertTrue(result.isEmpty(), "Expected empty for null input");
    }
}
