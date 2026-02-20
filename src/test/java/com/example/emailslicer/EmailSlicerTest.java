package com.example.emailslicer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link EmailSlicer}.
 *
 * <p>Covers valid parsing scenarios, whitespace handling, and all specified
 * invalid-input edge cases.</p>
 */
class EmailSlicerTest {

    // -----------------------------------------------------------------------
    // Happy-path tests
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("Valid email with simple domain")
    void parseValidSimpleDomain() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parse("user@example.com");

        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }

    @Test
    @DisplayName("Valid email with subdomain")
    void parseValidSubdomain() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parse("user@mail.example.com");

        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals("mail.example.com", result.getDomain());
    }

    @Test
    @DisplayName("Leading and trailing whitespace is trimmed")
    void parseTrimsWhitespace() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parse("   user@example.com  ");

        assertEquals("user", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }

    @Test
    @DisplayName("Convenience method getUsername returns correct value")
    void getUsernameConvenience() {
        assertEquals("alice", EmailSlicer.getUsername("alice@domain.org"));
    }

    @Test
    @DisplayName("Convenience method getDomain returns correct value")
    void getDomainConvenience() {
        assertEquals("domain.org", EmailSlicer.getDomain("alice@domain.org"));
    }

    @Test
    @DisplayName("ParsedEmail toString reconstructs the email")
    void parsedEmailToString() {
        EmailSlicer.ParsedEmail result = EmailSlicer.parse("user@example.com");
        assertEquals("user@example.com", result.toString());
    }

    // -----------------------------------------------------------------------
    // Error-path tests
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("Null input throws IllegalArgumentException")
    void parseNullInput() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse(null)
        );
        assertNotNull(ex.getMessage());
    }

    @Test
    @DisplayName("Empty string throws IllegalArgumentException")
    void parseEmptyString() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("")
        );
        assertNotNull(ex.getMessage());
    }

    @Test
    @DisplayName("Whitespace-only string throws IllegalArgumentException")
    void parseWhitespaceOnly() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("   ")
        );
        assertNotNull(ex.getMessage());
    }

    @Test
    @DisplayName("Missing '@' throws IllegalArgumentException")
    void parseMissingAtSign() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("userexample.com")
        );
        assertNotNull(ex.getMessage());
    }

    @Test
    @DisplayName("'@' at the beginning throws IllegalArgumentException")
    void parseAtSignAtBeginning() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("@example.com")
        );
        assertNotNull(ex.getMessage());
    }

    @Test
    @DisplayName("'@' at the end throws IllegalArgumentException")
    void parseAtSignAtEnd() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("user@")
        );
        assertNotNull(ex.getMessage());
    }

    @Test
    @DisplayName("Multiple '@' signs (consecutive) throws IllegalArgumentException")
    void parseMultipleAtSignsConsecutive() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("user@@example.com")
        );
        assertNotNull(ex.getMessage());
    }

    @Test
    @DisplayName("Multiple '@' signs (separated) throws IllegalArgumentException")
    void parseMultipleAtSignsSeparated() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("user@sub@example.com")
        );
        assertNotNull(ex.getMessage());
    }
}
