package com.emailslicer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailSlicerTest {

    // --- Valid email parsing tests ---

    @Test
    void parseStandardEmail() {
        EmailSlicer.EmailParts parts = EmailSlicer.parse("avimax37@gmail.com");
        assertEquals("avimax37", parts.username());
        assertEquals("gmail.com", parts.domain());
    }

    @ParameterizedTest
    @CsvSource({
            "alice@example.org,   alice,    example.org",
            "bob.smith@company.co.uk, bob.smith, company.co.uk",
            "test+tag@mail.com,  test+tag, mail.com"
    })
    void parseVariousValidEmails(String email, String expectedUsername, String expectedDomain) {
        EmailSlicer.EmailParts parts = EmailSlicer.parse(email);
        assertEquals(expectedUsername.trim(), parts.username());
        assertEquals(expectedDomain.trim(), parts.domain());
    }

    @Test
    void parseEmailWithMultipleAtSigns() {
        EmailSlicer.EmailParts parts = EmailSlicer.parse("user@sub@domain.com");
        assertEquals("user", parts.username());
        assertEquals("sub@domain.com", parts.domain());
    }

    @Test
    void parseEmailWithEmptyUsername() {
        EmailSlicer.EmailParts parts = EmailSlicer.parse("@domain.com");
        assertEquals("", parts.username());
        assertEquals("domain.com", parts.domain());
    }

    @Test
    void parseEmailWithEmptyDomain() {
        EmailSlicer.EmailParts parts = EmailSlicer.parse("user@");
        assertEquals("user", parts.username());
        assertEquals("", parts.domain());
    }

    @Test
    void parseEmailWithOnlyAtSign() {
        EmailSlicer.EmailParts parts = EmailSlicer.parse("@");
        assertEquals("", parts.username());
        assertEquals("", parts.domain());
    }

    // --- Validation tests ---

    @Test
    void parseRejectsEmailWithNoAtSign() {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse("invalidemail.com"));
    }

    @Test
    void parseRejectsEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse(""));
    }

    @Test
    void parseRejectsNull() {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"   ", "\t", "\n", "  \n\t  "})
    void parseRejectsWhitespaceOnlyInput(String input) {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"nope", "justastring", "missing-at-sign.com"})
    void parseRejectsInputsWithoutAt(String input) {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse(input));
    }

    // --- Error message verification ---

    @Test
    void parseInvalidEmailHasExpectedMessage() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("invalidemail.com")
        );
        assertEquals("Please enter a valid Email Id.", ex.getMessage());
    }
}
