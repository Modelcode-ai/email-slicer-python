package com.emailslicer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer#slice(String)}.
 */
class EmailSlicerTest {

    // ---- Valid emails ----

    @ParameterizedTest(name = "valid email: {0} -> username={1}, domain={2}")
    @CsvSource({
            "user@domain.com,       user,       domain.com",
            "first.last@sub.domain.org, first.last, sub.domain.org",
            "alice@example.io,      alice,      example.io",
            "a@b,                   a,          b"
    })
    void slice_validEmail_returnsExpectedParts(String email, String expectedUser, String expectedDomain) {
        Optional<EmailResult> result = EmailSlicer.slice(email);

        assertTrue(result.isPresent(), "Expected a result for: " + email);
        assertEquals(expectedUser, result.get().username());
        assertEquals(expectedDomain, result.get().domain());
    }

    // ---- Whitespace trimming ----

    @Test
    void slice_leadingAndTrailingWhitespace_isTrimmed() {
        Optional<EmailResult> result = EmailSlicer.slice("  user@domain.com  ");

        assertTrue(result.isPresent());
        assertEquals("user", result.get().username());
        assertEquals("domain.com", result.get().domain());
    }

    @Test
    void slice_tabsAndNewlines_areTrimmed() {
        Optional<EmailResult> result = EmailSlicer.slice("\t user@domain.com \n");

        assertTrue(result.isPresent());
        assertEquals("user", result.get().username());
        assertEquals("domain.com", result.get().domain());
    }

    // ---- Invalid emails ----

    @ParameterizedTest(name = "invalid email: \"{0}\" -> empty")
    @ValueSource(strings = {
            "invalidemail",
            "",
            "   ",
            "noatsign"
    })
    void slice_noAtSign_returnsEmpty(String email) {
        Optional<EmailResult> result = EmailSlicer.slice(email);

        assertTrue(result.isEmpty(), "Expected empty for: \"" + email + "\"");
    }

    @ParameterizedTest(name = "null input -> empty")
    @NullSource
    void slice_null_returnsEmpty(String email) {
        Optional<EmailResult> result = EmailSlicer.slice(email);

        assertTrue(result.isEmpty());
    }

    // ---- Edge cases matching Python behavior ----
    // The Python original only checks for @ presence; these inputs are accepted
    // by the Python version and should also be accepted here for behavioral parity.

    @Test
    void slice_emptyUsername_returnsResult() {
        // Python: "@domain.com" -> username="", domain="domain.com"
        Optional<EmailResult> result = EmailSlicer.slice("@domain.com");

        assertTrue(result.isPresent());
        assertEquals("", result.get().username());
        assertEquals("domain.com", result.get().domain());
    }

    @Test
    void slice_emptyDomain_returnsResult() {
        // Python: "user@" -> username="user", domain=""
        Optional<EmailResult> result = EmailSlicer.slice("user@");

        assertTrue(result.isPresent());
        assertEquals("user", result.get().username());
        assertEquals("", result.get().domain());
    }

    @Test
    void slice_multipleAtSigns_splitsOnFirst() {
        // Python: "a@b@c" -> username="a", domain="b@c" (uses index of first @)
        Optional<EmailResult> result = EmailSlicer.slice("a@b@c");

        assertTrue(result.isPresent());
        assertEquals("a", result.get().username());
        assertEquals("b@c", result.get().domain());
    }

    // ---- EmailResult record ----

    @Test
    void emailResult_equalsAndHashCode() {
        EmailResult a = new EmailResult("user", "domain.com");
        EmailResult b = new EmailResult("user", "domain.com");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void emailResult_toString_containsFields() {
        EmailResult result = new EmailResult("user", "domain.com");
        String str = result.toString();

        assertTrue(str.contains("user"));
        assertTrue(str.contains("domain.com"));
    }
}
