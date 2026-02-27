package com.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link EmailSlicer#slice(String)}.
 *
 * <p>Covers valid parsing, whitespace trimming, missing {@code @},
 * edge positions of {@code @}, multiple {@code @} characters, and
 * blank/null input.</p>
 */
class EmailSlicerTest {

    // ---- Valid email parsing ----

    @Test
    void slice_validEmail_returnsExpectedParts() {
        EmailParts parts = EmailSlicer.slice("avimax37@gmail.com");

        assertEquals("avimax37", parts.username());
        assertEquals("gmail.com", parts.domain());
    }

    @Test
    void slice_validEmailWithSubdomain_returnsExpectedParts() {
        EmailParts parts = EmailSlicer.slice("user@mail.example.com");

        assertEquals("user", parts.username());
        assertEquals("mail.example.com", parts.domain());
    }

    // ---- Whitespace trimming ----

    @Test
    void slice_inputWithLeadingAndTrailingWhitespace_trimsThenParses() {
        EmailParts parts = EmailSlicer.slice("  user@example.com  ");

        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    @Test
    void slice_inputWithTabsAndNewlines_trimsThenParses() {
        EmailParts parts = EmailSlicer.slice("\t user@example.com \n");

        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    // ---- Missing '@' character ----

    @Test
    void slice_missingAtCharacter_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.slice("invalid.email.com")
        );

        assertEquals("Invalid email address", ex.getMessage());
    }

    // ---- '@' at the beginning ----

    @Test
    void slice_atAtBeginning_returnsEmptyUsername() {
        EmailParts parts = EmailSlicer.slice("@example.com");

        assertEquals("", parts.username());
        assertEquals("example.com", parts.domain());
    }

    // ---- '@' at the end ----

    @Test
    void slice_atAtEnd_returnsEmptyDomain() {
        EmailParts parts = EmailSlicer.slice("user@");

        assertEquals("user", parts.username());
        assertEquals("", parts.domain());
    }

    // ---- Multiple '@' characters ----

    @Test
    void slice_multipleAtCharacters_splitsOnFirstOccurrence() {
        EmailParts parts = EmailSlicer.slice("user@sub@example.com");

        assertEquals("user", parts.username());
        assertEquals("sub@example.com", parts.domain());
    }

    // ---- Empty and blank input ----

    @Test
    void slice_emptyString_throwsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.slice("")
        );
    }

    @Test
    void slice_whitespaceOnlyInput_throwsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.slice("   ")
        );
    }

    // ---- Null input ----

    @Test
    void slice_nullInput_throwsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.slice(null)
        );
    }
}
