package com.emailslicer;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer#slice(String)}.
 * Covers valid emails, invalid inputs, and edge cases.
 */
class EmailSlicerTest {

    @Test
    void validStandardEmail() {
        Optional<EmailParts> result = EmailSlicer.slice("avimax37@gmail.com");
        assertTrue(result.isPresent());
        assertEquals("avimax37", result.get().username());
        assertEquals("gmail.com", result.get().domain());
    }

    @Test
    void validEmailWithSubdomain() {
        Optional<EmailParts> result = EmailSlicer.slice("user@mail.example.com");
        assertTrue(result.isPresent());
        assertEquals("user", result.get().username());
        assertEquals("mail.example.com", result.get().domain());
    }

    @Test
    void missingAtSymbol() {
        Optional<EmailParts> result = EmailSlicer.slice("invalidemail.com");
        assertTrue(result.isEmpty());
    }

    @Test
    void emptyString() {
        Optional<EmailParts> result = EmailSlicer.slice("");
        assertTrue(result.isEmpty());
    }

    @Test
    void onlyAtSymbol() {
        Optional<EmailParts> result = EmailSlicer.slice("@");
        assertTrue(result.isEmpty());
    }

    @Test
    void atSymbolAtStart() {
        Optional<EmailParts> result = EmailSlicer.slice("@domain.com");
        assertTrue(result.isEmpty());
    }

    @Test
    void atSymbolAtEnd() {
        Optional<EmailParts> result = EmailSlicer.slice("user@");
        assertTrue(result.isEmpty());
    }

    @Test
    void leadingAndTrailingWhitespace() {
        Optional<EmailParts> result = EmailSlicer.slice("  user@domain.com  ");
        assertTrue(result.isPresent());
        assertEquals("user", result.get().username());
        assertEquals("domain.com", result.get().domain());
    }

    @Test
    void nullInput() {
        Optional<EmailParts> result = EmailSlicer.slice(null);
        assertTrue(result.isEmpty());
    }
}
