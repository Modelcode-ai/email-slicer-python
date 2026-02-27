package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer}.
 * <p>
 * Covers the nominal case, edge cases (leading/trailing {@code @},
 * multiple {@code @}, whitespace), and invalid inputs.
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    // ---- Nominal case ----

    @Test
    @DisplayName("Nominal case: avimax37@gmail.com")
    void parseNominalCase() {
        EmailParts parts = slicer.parse("avimax37@gmail.com");

        assertEquals("avimax37", parts.username());
        assertEquals("gmail.com", parts.domain());
    }

    @Test
    @DisplayName("Standard email with subdomain")
    void parseEmailWithSubdomain() {
        EmailParts parts = slicer.parse("user@mail.example.org");

        assertEquals("user", parts.username());
        assertEquals("mail.example.org", parts.domain());
    }

    // ---- Invalid inputs ----

    @Test
    @DisplayName("Missing @ throws InvalidEmailException")
    void parseMissingAtThrows() {
        assertThrows(InvalidEmailException.class, () -> slicer.parse("invalidemail.com"));
    }

    @Test
    @DisplayName("Empty string throws InvalidEmailException")
    void parseEmptyStringThrows() {
        assertThrows(InvalidEmailException.class, () -> slicer.parse(""));
    }

    @Test
    @DisplayName("Whitespace-only input throws InvalidEmailException")
    void parseWhitespaceOnlyThrows() {
        assertThrows(InvalidEmailException.class, () -> slicer.parse("   "));
    }

    // ---- Edge cases: leading/trailing @ ----

    @Test
    @DisplayName("Leading @ yields empty username")
    void parseLeadingAt() {
        EmailParts parts = slicer.parse("@domain.com");

        assertEquals("", parts.username());
        assertEquals("domain.com", parts.domain());
    }

    @Test
    @DisplayName("Trailing @ yields empty domain")
    void parseTrailingAt() {
        EmailParts parts = slicer.parse("user@");

        assertEquals("user", parts.username());
        assertEquals("", parts.domain());
    }

    // ---- Edge case: multiple @ characters ----

    @Test
    @DisplayName("Multiple @ splits at first occurrence")
    void parseMultipleAtSplitsAtFirst() {
        EmailParts parts = slicer.parse("user@sub@domain.com");

        assertEquals("user", parts.username());
        assertEquals("sub@domain.com", parts.domain());
    }

    // ---- Whitespace trimming ----

    @Test
    @DisplayName("Leading whitespace is trimmed")
    void parseTrimsLeadingWhitespace() {
        EmailParts parts = slicer.parse("  user@example.com");

        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    @Test
    @DisplayName("Trailing whitespace is trimmed")
    void parseTrimsTrailingWhitespace() {
        EmailParts parts = slicer.parse("user@example.com  ");

        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    @Test
    @DisplayName("Leading and trailing whitespace is trimmed")
    void parseTrimsBothWhitespace() {
        EmailParts parts = slicer.parse("  user@example.com  ");

        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }
}
