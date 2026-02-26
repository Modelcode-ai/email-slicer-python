package com.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link EmailSlicer} parsing logic.
 * Covers valid emails, invalid inputs, whitespace trimming, and edge cases.
 */
class EmailSlicerTest {

    @Test
    void validEmail_baselineExample() throws InvalidEmailException {
        EmailSlicer.EmailParts parts = EmailSlicer.slice("avimax37@gmail.com");

        assertEquals("avimax37", parts.username());
        assertEquals("gmail.com", parts.domain());
    }

    @Test
    void validEmail_splitsAtFirstAtSymbol() throws InvalidEmailException {
        EmailSlicer.EmailParts parts = EmailSlicer.slice("user@sub@domain.com");

        assertEquals("user", parts.username());
        assertEquals("sub@domain.com", parts.domain());
    }

    @Test
    void validEmail_withLeadingAndTrailingWhitespace() throws InvalidEmailException {
        EmailSlicer.EmailParts parts = EmailSlicer.slice("   user@example.com  ");

        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    @Test
    void invalidEmail_missingAtSymbol() {
        assertThrows(InvalidEmailException.class, () ->
                EmailSlicer.slice("not-an-email"));
    }

    @Test
    void invalidEmail_nullInput() {
        assertThrows(InvalidEmailException.class, () ->
                EmailSlicer.slice(null));
    }

    @Test
    void invalidEmail_emptyString() {
        assertThrows(InvalidEmailException.class, () ->
                EmailSlicer.slice(""));
    }

    @Test
    void invalidEmail_whitespaceOnly() {
        assertThrows(InvalidEmailException.class, () ->
                EmailSlicer.slice("   "));
    }

    @Test
    void validEmail_atSymbolAtStart() throws InvalidEmailException {
        // Edge case: @ at the very beginning → empty username, full domain
        EmailSlicer.EmailParts parts = EmailSlicer.slice("@domain.com");

        assertEquals("", parts.username());
        assertEquals("domain.com", parts.domain());
    }

    @Test
    void validEmail_atSymbolAtEnd() throws InvalidEmailException {
        // Edge case: @ at the very end → full username, empty domain
        EmailSlicer.EmailParts parts = EmailSlicer.slice("user@");

        assertEquals("user", parts.username());
        assertEquals("", parts.domain());
    }
}
