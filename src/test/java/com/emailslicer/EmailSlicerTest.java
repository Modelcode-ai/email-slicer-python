package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link EmailSlicer}.
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    // ---- Valid input tests ----

    @Test
    void parse_validEmail_returnsComponents() {
        EmailComponents result = slicer.parse("avimax37@gmail.com");

        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    void parse_validEmailWithSubdomain_returnsComponents() {
        EmailComponents result = slicer.parse("user@mail.example.com");

        assertEquals("user", result.username());
        assertEquals("mail.example.com", result.domain());
    }

    @Test
    void parse_emailWithLeadingAndTrailingWhitespace_trimmedBeforeParsing() {
        EmailComponents result = slicer.parse("  avimax37@gmail.com  ");

        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    void parse_emailWithTabWhitespace_trimmedBeforeParsing() {
        EmailComponents result = slicer.parse("\tavimax37@gmail.com\t");

        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    // ---- Invalid input tests ----

    @Test
    void parse_nullInput_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.parse(null));
    }

    @Test
    void parse_emptyString_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.parse(""));
    }

    @Test
    void parse_whitespaceOnly_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.parse("   "));
    }

    @Test
    void parse_missingAtSymbol_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.parse("invalid-email.com"));
    }

    @Test
    void parse_multipleAtSymbols_consecutiveAts_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.parse("user@@example.com"));
    }

    @Test
    void parse_multipleAtSymbols_separatedAts_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.parse("user@sub@domain.com"));
    }

    @Test
    void parse_emptyUsername_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.parse("@example.com"));
    }

    @Test
    void parse_emptyDomain_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.parse("user@"));
    }
}
