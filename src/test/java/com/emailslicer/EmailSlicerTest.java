package com.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link EmailSlicer} covering valid emails, invalid inputs,
 * and edge cases to ensure behavioral equivalence with the original Python script.
 */
class EmailSlicerTest {

    private final EmailSlicer slicer = new EmailSlicer();

    // ---- Valid email parsing ----

    @Test
    void parsesValidEmail() {
        EmailSliceResult result = slicer.slice("avimax37@gmail.com");
        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    void parsesAnotherValidEmail() {
        EmailSliceResult result = slicer.slice("user@example.com");
        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    void parsesEmailWithSubdomain() {
        EmailSliceResult result = slicer.slice("admin@mail.company.org");
        assertEquals("admin", result.username());
        assertEquals("mail.company.org", result.domain());
    }

    // ---- Whitespace trimming ----

    @Test
    void trimsLeadingAndTrailingWhitespace() {
        EmailSliceResult result = slicer.slice("  spaced@example.com  ");
        assertEquals("spaced", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    void trimsTabsAndSpaces() {
        EmailSliceResult result = slicer.slice("\t user@example.com \t");
        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    // ---- Case preservation ----

    @Test
    void preservesOriginalCasing() {
        EmailSliceResult result = slicer.slice("UserName@Domain.COM");
        assertEquals("UserName", result.username());
        assertEquals("Domain.COM", result.domain());
    }

    // ---- Multiple @ symbols ----

    @Test
    void handlesMultipleAtSymbolsUsingSplitAtFirst() {
        EmailSliceResult result = slicer.slice("user@sub@domain.com");
        assertEquals("user", result.username());
        assertEquals("sub@domain.com", result.domain());
    }

    @Test
    void handlesDoubleAtInDomain() {
        EmailSliceResult result = slicer.slice("name@host@port.org");
        assertEquals("name", result.username());
        assertEquals("host@port.org", result.domain());
    }

    // ---- Invalid input: missing @ ----

    @Test
    void rejectsEmailWithoutAtSymbol() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("invalidemail"));
    }

    @Test
    void rejectsPlainString() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("justtext"));
    }

    // ---- Invalid input: empty username ----

    @Test
    void rejectsEmailWithEmptyUsername() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("@domain.com"));
    }

    // ---- Invalid input: empty domain ----

    @Test
    void rejectsEmailWithEmptyDomain() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("user@"));
    }

    // ---- Invalid input: null ----

    @Test
    void rejectsNullInput() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice(null));
    }

    // ---- Invalid input: empty and whitespace-only ----

    @Test
    void rejectsEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice(""));
    }

    @Test
    void rejectsWhitespaceOnlyString() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("   "));
    }

    // ---- Invalid input: just @ symbol ----

    @Test
    void rejectsJustAtSymbol() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("@"));
    }
}
