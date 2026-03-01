package com.emailslicer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link EmailSlicer} covering valid parsing, invalid input rejection,
 * whitespace trimming, and edge cases consistent with the original Python script.
 */
class EmailSlicerTest {

    private final EmailSlicer slicer = new EmailSlicer();

    @Test
    void parsesValidEmailFromReadme() {
        EmailParts parts = slicer.slice("avimax37@gmail.com");
        assertEquals("avimax37", parts.username());
        assertEquals("gmail.com", parts.domain());
    }

    @Test
    void parsesAnotherValidEmail() {
        EmailParts parts = slicer.slice("john.doe@example.org");
        assertEquals("john.doe", parts.username());
        assertEquals("example.org", parts.domain());
    }

    @Test
    void rejectsEmailWithoutAtSymbol() {
        assertThrows(InvalidEmailException.class, () -> slicer.slice("invalidemail"));
    }

    @Test
    void rejectsEmailWithoutAtSymbolButWithDot() {
        assertThrows(InvalidEmailException.class, () -> slicer.slice("user.domain.com"));
    }

    @Test
    void trimsWhitespaceAroundEmail() {
        EmailParts parts = slicer.slice("  user@domain.com  ");
        assertEquals("user", parts.username());
        assertEquals("domain.com", parts.domain());
    }

    @Test
    void rejectsEmptyInput() {
        assertThrows(InvalidEmailException.class, () -> slicer.slice(""));
    }

    @Test
    void rejectsWhitespaceOnlyInput() {
        assertThrows(InvalidEmailException.class, () -> slicer.slice("   "));
    }

    @Test
    void rejectsNullInput() {
        assertThrows(InvalidEmailException.class, () -> slicer.slice(null));
    }

    @Test
    void rejectsEmptyUsername() {
        assertThrows(InvalidEmailException.class, () -> slicer.slice("@domain.com"));
    }

    @Test
    void rejectsEmptyDomain() {
        assertThrows(InvalidEmailException.class, () -> slicer.slice("user@"));
    }

    @Test
    void acceptsMultipleAtSymbolsBySplittingOnFirst() {
        EmailParts parts = slicer.slice("user@sub@domain.com");
        assertEquals("user", parts.username());
        assertEquals("sub@domain.com", parts.domain());
    }

    @Test
    void rejectsAtSymbolOnly() {
        assertThrows(InvalidEmailException.class, () -> slicer.slice("@"));
    }
}
