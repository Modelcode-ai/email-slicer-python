package com.emailslicer;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer} covering all parsing and validation
 * scenarios defined in the modernization specification.
 */
class EmailSlicerTest {

    private final EmailSlicer slicer = new EmailSlicer();

    @Test
    void parsesValidEmail() {
        Optional<EmailSlicer.EmailParts> result = slicer.slice("user@example.com");
        assertTrue(result.isPresent());
        assertEquals("user", result.get().getUsername());
        assertEquals("example.com", result.get().getDomain());
    }

    @Test
    void trimsLeadingAndTrailingWhitespace() {
        Optional<EmailSlicer.EmailParts> result = slicer.slice("  user@example.com  ");
        assertTrue(result.isPresent());
        assertEquals("user", result.get().getUsername());
        assertEquals("example.com", result.get().getDomain());
    }

    @Test
    void returnsEmptyForMissingAtSymbol() {
        assertTrue(slicer.slice("noatsymbol").isEmpty());
    }

    @Test
    void returnsEmptyForEmptyInput() {
        assertTrue(slicer.slice("").isEmpty());
    }

    @Test
    void returnsEmptyForWhitespaceOnlyInput() {
        assertTrue(slicer.slice("   ").isEmpty());
    }

    @Test
    void returnsEmptyForNullInput() {
        assertTrue(slicer.slice(null).isEmpty());
    }

    @Test
    void returnsEmptyForEmptyUsername() {
        assertTrue(slicer.slice("@example.com").isEmpty());
    }

    @Test
    void returnsEmptyForEmptyDomain() {
        assertTrue(slicer.slice("user@").isEmpty());
    }

    @Test
    void splitsOnFirstAtSymbolOnly() {
        Optional<EmailSlicer.EmailParts> result = slicer.slice("user@sub@domain.com");
        assertTrue(result.isPresent());
        assertEquals("user", result.get().getUsername());
        assertEquals("sub@domain.com", result.get().getDomain());
    }

    @Test
    void handlesEmailWithSingleCharacterParts() {
        Optional<EmailSlicer.EmailParts> result = slicer.slice("a@b");
        assertTrue(result.isPresent());
        assertEquals("a", result.get().getUsername());
        assertEquals("b", result.get().getDomain());
    }

    @Test
    void returnsEmptyForAtSymbolOnly() {
        assertTrue(slicer.slice("@").isEmpty());
    }
}
