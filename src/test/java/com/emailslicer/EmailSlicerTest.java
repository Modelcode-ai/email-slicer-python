package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Comprehensive unit tests for {@link EmailSlicer}.
 * <p>
 * Covers valid parsing, invalid input rejection, whitespace trimming,
 * and multiple-{@code @} handling.
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    // ---- Valid email parsing ----

    @ParameterizedTest(name = "parse \"{0}\" -> username=\"{1}\", domain=\"{2}\"")
    @CsvSource({
            "avimax37@gmail.com,   avimax37,   gmail.com",
            "user@example.com,     user,       example.com",
            "a@b.com,              a,          b.com",
            "john.doe@company.org, john.doe,   company.org",
            "user+tag@example.com, user+tag,   example.com",
    })
    void parsesValidEmails(String input, String expectedUser, String expectedDomain) {
        EmailParts parts = slicer.parse(input);
        assertEquals(expectedUser, parts.username());
        assertEquals(expectedDomain, parts.domain());
    }

    // ---- Whitespace trimming ----

    @ParameterizedTest(name = "parse \"{0}\" with whitespace -> username=\"{1}\", domain=\"{2}\"")
    @CsvSource({
            "'  user@example.com  ',  user,  example.com",
            "' avimax37@gmail.com ',  avimax37,  gmail.com",
            "'user@example.com ',     user,  example.com",
            "' user@example.com',     user,  example.com",
    })
    void trimsWhitespaceBeforeParsing(String input, String expectedUser, String expectedDomain) {
        EmailParts parts = slicer.parse(input);
        assertEquals(expectedUser, parts.username());
        assertEquals(expectedDomain, parts.domain());
    }

    // ---- Multiple @ handling ----

    @Test
    void splitsOnFirstAtWhenMultiplePresent() {
        EmailParts parts = slicer.parse("user@sub@domain.com");
        assertEquals("user", parts.username());
        assertEquals("sub@domain.com", parts.domain());
    }

    @Test
    void splitsOnFirstAtWithManyAtSymbols() {
        EmailParts parts = slicer.parse("a@b@c@d");
        assertEquals("a", parts.username());
        assertEquals("b@c@d", parts.domain());
    }

    // ---- Invalid input: null ----

    @Test
    void rejectsNullInput() {
        var ex = assertThrows(IllegalArgumentException.class, () -> slicer.parse(null));
        assertEquals("Email input must not be null", ex.getMessage());
    }

    // ---- Invalid input: empty / whitespace-only ----

    @ParameterizedTest(name = "rejects empty/whitespace input: \"{0}\"")
    @ValueSource(strings = {"", "   ", "\t", "\n"})
    void rejectsEmptyOrWhitespaceOnlyInput(String input) {
        assertThrows(IllegalArgumentException.class, () -> slicer.parse(input));
    }

    // ---- Invalid input: no @ symbol ----

    @ParameterizedTest(name = "rejects input without @: \"{0}\"")
    @ValueSource(strings = {"invalidemail", "noatsign", "justastring"})
    void rejectsInputWithoutAtSymbol(String input) {
        var ex = assertThrows(IllegalArgumentException.class, () -> slicer.parse(input));
        assertEquals("Email must contain an '@' symbol", ex.getMessage());
    }

    // ---- Invalid input: @ at start ----

    @Test
    void rejectsAtAtStart() {
        var ex = assertThrows(IllegalArgumentException.class, () -> slicer.parse("@example.com"));
        assertEquals("Email must have a username before '@'", ex.getMessage());
    }

    // ---- Invalid input: @ at end ----

    @Test
    void rejectsAtAtEnd() {
        var ex = assertThrows(IllegalArgumentException.class, () -> slicer.parse("user@"));
        assertEquals("Email must have a domain after '@'", ex.getMessage());
    }

    // ---- Invalid input: lone @ ----

    @Test
    void rejectsLoneAtSymbol() {
        assertThrows(IllegalArgumentException.class, () -> slicer.parse("@"));
    }

    // ---- EmailParts record ----

    @Test
    void emailPartsRecordHoldsValues() {
        var parts = new EmailParts("user", "example.com");
        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }
}
