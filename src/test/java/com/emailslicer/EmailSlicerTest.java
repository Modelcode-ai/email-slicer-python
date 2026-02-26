package com.emailslicer;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link EmailSlicer#parse(String)}.
 *
 * <p>Covers all test cases specified in the modernization specification:
 * valid email, invalid email, multiple {@code @} characters, {@code @} at start/end,
 * whitespace handling, and null/empty input.</p>
 */
class EmailSlicerTest {

    @Test
    void validEmailFromReadme() {
        Optional<EmailParts> result = EmailSlicer.parse("avimax37@gmail.com");

        assertTrue(result.isPresent(), "Expected a valid parse result");
        assertEquals("avimax37", result.get().username());
        assertEquals("gmail.com", result.get().domain());
    }

    @Test
    void invalidEmailNoAtSign() {
        Optional<EmailParts> result = EmailSlicer.parse("invalidemail");

        assertTrue(result.isEmpty(), "Expected Optional.empty() for input without @");
    }

    @Test
    void multipleAtCharacters() {
        Optional<EmailParts> result = EmailSlicer.parse("user@name@domain.com");

        assertTrue(result.isPresent(), "Expected a valid parse result");
        assertEquals("user", result.get().username());
        assertEquals("name@domain.com", result.get().domain());
    }

    @Test
    void atSignAtStart() {
        Optional<EmailParts> result = EmailSlicer.parse("@domain.com");

        assertTrue(result.isPresent(), "Expected a valid parse result");
        assertEquals("", result.get().username());
        assertEquals("domain.com", result.get().domain());
    }

    @Test
    void atSignAtEnd() {
        Optional<EmailParts> result = EmailSlicer.parse("user@");

        assertTrue(result.isPresent(), "Expected a valid parse result");
        assertEquals("user", result.get().username());
        assertEquals("", result.get().domain());
    }

    @Test
    void leadingAndTrailingWhitespace() {
        Optional<EmailParts> result = EmailSlicer.parse("   user@example.com  ");

        assertTrue(result.isPresent(), "Expected a valid parse result");
        assertEquals("user", result.get().username());
        assertEquals("example.com", result.get().domain());
    }

    @Test
    void nullInput() {
        Optional<EmailParts> result = EmailSlicer.parse(null);

        assertTrue(result.isEmpty(), "Expected Optional.empty() for null input");
    }

    @Test
    void emptyStringInput() {
        Optional<EmailParts> result = EmailSlicer.parse("");

        assertTrue(result.isEmpty(), "Expected Optional.empty() for empty string input");
    }
}
