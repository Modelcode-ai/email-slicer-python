package com.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link EmailSlicer#parse(String)}.
 *
 * <p>Covers all test categories from the testing strategy: standard valid
 * emails, whitespace handling, missing {@code @} symbol, {@code @} at invalid
 * positions, multiple {@code @} characters, and null input.</p>
 */
class EmailSlicerTest {

    // ---- Standard valid emails ----

    @Test
    void parsesStandardGmailAddress() {
        EmailComponents result = EmailSlicer.parse("avimax37@gmail.com");

        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    void parsesStandardOrgAddress() {
        EmailComponents result = EmailSlicer.parse("user@example.org");

        assertEquals("user", result.username());
        assertEquals("example.org", result.domain());
    }

    // ---- Whitespace handling ----

    @Test
    void trimsLeadingWhitespace() {
        EmailComponents result = EmailSlicer.parse("  user@example.com");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    void trimsTrailingWhitespace() {
        EmailComponents result = EmailSlicer.parse("user@example.com  ");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    void trimsLeadingAndTrailingWhitespace() {
        EmailComponents result = EmailSlicer.parse("  user@example.com  ");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    void trimsTabWhitespace() {
        EmailComponents result = EmailSlicer.parse("\tuser@example.com\t");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    // ---- Missing @ symbol ----

    @Test
    void throwsOnMissingAtSymbol() {
        InvalidEmailException exception = assertThrows(
                InvalidEmailException.class,
                () -> EmailSlicer.parse("userexample.com")
        );
        assertNotNull(exception.getMessage());
    }

    @Test
    void throwsOnEmptyString() {
        InvalidEmailException exception = assertThrows(
                InvalidEmailException.class,
                () -> EmailSlicer.parse("")
        );
        assertNotNull(exception.getMessage());
    }

    @Test
    void throwsOnWhitespaceOnlyString() {
        InvalidEmailException exception = assertThrows(
                InvalidEmailException.class,
                () -> EmailSlicer.parse("   ")
        );
        assertNotNull(exception.getMessage());
    }

    // ---- @ at invalid positions ----

    @Test
    void throwsOnAtAsFirstCharacter() {
        InvalidEmailException exception = assertThrows(
                InvalidEmailException.class,
                () -> EmailSlicer.parse("@example.com")
        );
        assertNotNull(exception.getMessage());
    }

    @Test
    void throwsOnAtAsLastCharacter() {
        InvalidEmailException exception = assertThrows(
                InvalidEmailException.class,
                () -> EmailSlicer.parse("user@")
        );
        assertNotNull(exception.getMessage());
    }

    // ---- Multiple @ characters ----

    @Test
    void splitsOnFirstAtWithMultipleAtSymbols() {
        EmailComponents result = EmailSlicer.parse("user@sub@example.com");

        assertEquals("user", result.username());
        assertEquals("sub@example.com", result.domain());
    }

    // ---- Null input handling ----

    @Test
    void throwsOnNullInput() {
        InvalidEmailException exception = assertThrows(
                InvalidEmailException.class,
                () -> EmailSlicer.parse(null)
        );
        assertNotNull(exception.getMessage());
    }
}
