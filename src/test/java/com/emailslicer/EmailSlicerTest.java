package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer}.
 *
 * <p>Covers valid parsing, whitespace trimming, and all invalid-input
 * scenarios defined in the modernization specification.</p>
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    // ---------------------------------------------------------------
    // Valid email scenarios
    // ---------------------------------------------------------------

    @Test
    void validEmail_typicalCase() {
        Optional<EmailParts> result = slicer.slice("avimax37@gmail.com");

        assertTrue(result.isPresent(), "Expected a valid result");
        assertEquals("avimax37", result.get().getUsername());
        assertEquals("gmail.com", result.get().getDomain());
    }

    @Test
    void validEmail_withSurroundingWhitespace() {
        Optional<EmailParts> result = slicer.slice("  user@example.com  ");

        assertTrue(result.isPresent(), "Expected a valid result after trimming");
        assertEquals("user", result.get().getUsername());
        assertEquals("example.com", result.get().getDomain());
    }

    @Test
    void validEmail_withLeadingWhitespace() {
        Optional<EmailParts> result = slicer.slice("   test@domain.org");

        assertTrue(result.isPresent());
        assertEquals("test", result.get().getUsername());
        assertEquals("domain.org", result.get().getDomain());
    }

    @Test
    void validEmail_withTrailingWhitespace() {
        Optional<EmailParts> result = slicer.slice("admin@server.net   ");

        assertTrue(result.isPresent());
        assertEquals("admin", result.get().getUsername());
        assertEquals("server.net", result.get().getDomain());
    }

    @Test
    void validEmail_subdomainInDomain() {
        Optional<EmailParts> result = slicer.slice("info@mail.example.co.uk");

        assertTrue(result.isPresent());
        assertEquals("info", result.get().getUsername());
        assertEquals("mail.example.co.uk", result.get().getDomain());
    }

    // ---------------------------------------------------------------
    // Invalid email scenarios
    // ---------------------------------------------------------------

    @Test
    void invalidEmail_missingAtSign() {
        Optional<EmailParts> result = slicer.slice("invalid.email");

        assertTrue(result.isEmpty(), "Email without @ should be invalid");
    }

    @Test
    void invalidEmail_multipleAtCharacters() {
        Optional<EmailParts> result = slicer.slice("user@@example.com");

        assertTrue(result.isEmpty(), "Email with multiple @ should be invalid");
    }

    @Test
    void invalidEmail_multipleAtCharacters_separated() {
        Optional<EmailParts> result = slicer.slice("user@name@example.com");

        assertTrue(result.isEmpty(), "Email with two separate @ should be invalid");
    }

    @Test
    void invalidEmail_atSignAtStart() {
        Optional<EmailParts> result = slicer.slice("@example.com");

        assertTrue(result.isEmpty(), "Email starting with @ should be invalid");
    }

    @Test
    void invalidEmail_atSignAtEnd() {
        Optional<EmailParts> result = slicer.slice("user@");

        assertTrue(result.isEmpty(), "Email ending with @ should be invalid");
    }

    @Test
    void invalidEmail_emptyString() {
        Optional<EmailParts> result = slicer.slice("");

        assertTrue(result.isEmpty(), "Empty string should be invalid");
    }

    @Test
    void invalidEmail_whitespaceOnly() {
        Optional<EmailParts> result = slicer.slice("   ");

        assertTrue(result.isEmpty(), "Whitespace-only input should be invalid");
    }

    @Test
    void invalidEmail_nullInput() {
        Optional<EmailParts> result = slicer.slice(null);

        assertTrue(result.isEmpty(), "Null input should be invalid");
    }
}
