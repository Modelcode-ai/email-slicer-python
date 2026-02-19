package com.emailslicer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer#parse(String)}.
 *
 * <p>Covers valid emails, whitespace trimming, and various invalid input scenarios.
 * The Java version intentionally enforces stricter validation than the original Python
 * implementation (e.g., rejecting multiple {@code @} characters, empty username/domain).</p>
 */
class EmailSlicerTest {

    // -------------------------------------------------------------------------
    // Valid email tests
    // -------------------------------------------------------------------------

    @ParameterizedTest(name = "parse(\"{0}\") -> username=\"{1}\", domain=\"{2}\"")
    @CsvSource({
            "user@example.com,        user,        example.com",
            "alice@gmail.com,         alice,       gmail.com",
            "avimax37@gmail.com,      avimax37,    gmail.com",
            "user@mail.example.co.uk, user,        mail.example.co.uk",
            "john.doe@company.org,    john.doe,    company.org",
            "a@b.com,                 a,           b.com"
    })
    @DisplayName("Valid emails should be parsed into username and domain")
    void testValidEmails(String email, String expectedUsername, String expectedDomain) {
        EmailParts parts = EmailSlicer.parse(email);
        assertEquals(expectedUsername, parts.username());
        assertEquals(expectedDomain, parts.domain());
    }

    @Test
    @DisplayName("Leading and trailing whitespace should be trimmed before parsing")
    void testWhitespaceTrimming() {
        EmailParts parts = EmailSlicer.parse("  user@example.com  ");
        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    @Test
    @DisplayName("Tabs and mixed whitespace should be trimmed before parsing")
    void testMixedWhitespaceTrimming() {
        EmailParts parts = EmailSlicer.parse("\t user@example.com \t");
        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    @Test
    @DisplayName("EmailParts record should provide correct accessors")
    void testEmailPartsRecord() {
        EmailParts parts = new EmailParts("alice", "example.com");
        assertEquals("alice", parts.username());
        assertEquals("example.com", parts.domain());
    }

    // -------------------------------------------------------------------------
    // Invalid input tests — null and empty
    // -------------------------------------------------------------------------

    @ParameterizedTest(name = "parse(\"{0}\") should throw IllegalArgumentException")
    @NullAndEmptySource
    @DisplayName("Null and empty input should be rejected")
    void testNullAndEmpty(String input) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse(input)
        );
        assertTrue(ex.getMessage().contains("null or empty"));
    }

    @ParameterizedTest(name = "parse(\"{0}\") should throw for blank input")
    @ValueSource(strings = {"   ", "\t", "\n", " \t\n "})
    @DisplayName("Whitespace-only input should be rejected")
    void testBlankInput(String input) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse(input)
        );
        assertTrue(ex.getMessage().contains("null or empty"));
    }

    // -------------------------------------------------------------------------
    // Invalid input tests — structural issues
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("Email without @ symbol should be rejected")
    void testMissingAtSymbol() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("userexample.com")
        );
        assertTrue(ex.getMessage().contains("missing '@'"));
    }

    @Test
    @DisplayName("Email with multiple @ symbols should be rejected")
    void testMultipleAtSymbols() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("a@b@c.com")
        );
        assertTrue(ex.getMessage().contains("multiple '@'"));
    }

    @Test
    @DisplayName("Email with empty username (@domain) should be rejected")
    void testEmptyUsername() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("@example.com")
        );
        assertTrue(ex.getMessage().contains("username portion is empty"));
    }

    @Test
    @DisplayName("Email with empty domain (user@) should be rejected")
    void testEmptyDomain() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("user@")
        );
        assertTrue(ex.getMessage().contains("domain portion is empty"));
    }

    @Test
    @DisplayName("Lone @ symbol should be rejected")
    void testLoneAtSymbol() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("@")
        );
        assertTrue(ex.getMessage().contains("username portion is empty"));
    }
}
