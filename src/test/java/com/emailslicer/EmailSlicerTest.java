package com.emailslicer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer#slice(String)}.
 */
class EmailSlicerTest {

    // ── Valid emails ──────────────────────────────────────────────────

    @ParameterizedTest(name = "valid: \"{0}\" → username=\"{1}\", domain=\"{2}\"")
    @CsvSource({
            "user@domain.com,       user,           domain.com",
            "a@b.co,                a,              b.co",
            "user.name+tag@sub.domain.org, user.name+tag, sub.domain.org",
            "firstname.lastname@example.com, firstname.lastname, example.com",
            "user123@test.io,       user123,        test.io"
    })
    void validEmails(String input, String expectedUsername, String expectedDomain) {
        Optional<EmailParts> result = EmailSlicer.slice(input);

        assertTrue(result.isPresent(), "Expected a valid result for: " + input);
        assertEquals(expectedUsername, result.get().username());
        assertEquals(expectedDomain, result.get().domain());
    }

    // ── Missing @ ────────────────────────────────────────────────────

    @ParameterizedTest(name = "missing @: \"{0}\"")
    @ValueSource(strings = {"nodomain", "justtext", "plainstring"})
    void missingAtSymbol(String input) {
        assertTrue(EmailSlicer.slice(input).isEmpty(),
                "Expected empty Optional for input without @: " + input);
    }

    // ── Multiple @ ───────────────────────────────────────────────────

    @ParameterizedTest(name = "multiple @: \"{0}\"")
    @ValueSource(strings = {"a@b@c.com", "@@", "user@@domain.com"})
    void multipleAtSymbols(String input) {
        assertTrue(EmailSlicer.slice(input).isEmpty(),
                "Expected empty Optional for input with multiple @: " + input);
    }

    // ── Empty parts ──────────────────────────────────────────────────

    @ParameterizedTest(name = "empty part: \"{0}\"")
    @ValueSource(strings = {"@domain.com", "user@", "@"})
    void emptyParts(String input) {
        assertTrue(EmailSlicer.slice(input).isEmpty(),
                "Expected empty Optional for input with empty part: " + input);
    }

    // ── Whitespace handling ──────────────────────────────────────────

    @Test
    void leadingAndTrailingWhitespaceIsTrimmed() {
        Optional<EmailParts> result = EmailSlicer.slice("  user@domain.com  ");

        assertTrue(result.isPresent(), "Trimmed input should be valid");
        assertEquals("user", result.get().username());
        assertEquals("domain.com", result.get().domain());
    }

    @ParameterizedTest(name = "internal whitespace: \"{0}\"")
    @ValueSource(strings = {"user @domain.com", "user@ domain.com", "us er@domain.com"})
    void internalWhitespaceRejected(String input) {
        assertTrue(EmailSlicer.slice(input).isEmpty(),
                "Expected empty Optional for input with internal whitespace: " + input);
    }

    // ── Null and empty input ─────────────────────────────────────────

    @ParameterizedTest(name = "null/empty: \"{0}\"")
    @NullAndEmptySource
    void nullAndEmptyInput(String input) {
        assertTrue(EmailSlicer.slice(input).isEmpty(),
                "Expected empty Optional for null or empty input");
    }

    @Test
    void blankInput() {
        assertTrue(EmailSlicer.slice("   ").isEmpty(),
                "Expected empty Optional for blank input");
    }
}
