package com.emailslicer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for EmailSlicer core parsing logic.
 * Uses parameterized tests to cover valid and invalid email scenarios.
 */
class EmailSlicerTest {

    @ParameterizedTest
    @CsvSource({
        "avimax37@gmail.com, avimax37, gmail.com",
        "user@sub.domain.com, user, sub.domain.com",
        "test@example.org, test, example.org",
        "john.doe@company.co.uk, john.doe, company.co.uk"
    })
    void parsesValidEmails(String input, String expectedUser, String expectedDomain) {
        EmailSlicer.EmailParts parts = EmailSlicer.parse(input);
        assertEquals(expectedUser, parts.getUsername());
        assertEquals(expectedDomain, parts.getDomain());
    }

    @ParameterizedTest
    @CsvSource({
        "'  avimax37@gmail.com  ', avimax37, gmail.com",
        "'  user@example.com', user, example.com",
        "'user@example.com  ', user, example.com"
    })
    void parsesEmailsWithSurroundingWhitespace(String input, String expectedUser, String expectedDomain) {
        EmailSlicer.EmailParts parts = EmailSlicer.parse(input);
        assertEquals(expectedUser, parts.getUsername());
        assertEquals(expectedDomain, parts.getDomain());
    }

    @ParameterizedTest
    @CsvSource({
        "user name@example.com, user name, example.com",
        "'user@ example.com', user, ' example.com'"
    })
    void parsesEmailsWithInternalWhitespace(String input, String expectedUser, String expectedDomain) {
        EmailSlicer.EmailParts parts = EmailSlicer.parse(input);
        assertEquals(expectedUser, parts.getUsername());
        assertEquals(expectedDomain, parts.getDomain());
    }

    @Test
    void parsesEmailWithMultipleAtSymbols() {
        EmailSlicer.EmailParts parts = EmailSlicer.parse("user@domain@extra.com");
        assertEquals("user", parts.getUsername());
        assertEquals("domain@extra.com", parts.getDomain());
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid-email", "nodomain", "noatsymbol"})
    void rejectsEmailsWithoutAtSymbol(String input) {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t", "\n"})
    void rejectsEmptyOrWhitespaceOnlyInputs(String input) {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse(input));
    }

    @ParameterizedTest
    @NullSource
    void rejectsNullInput(String input) {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse(input));
    }

    @Test
    void rejectsEmailWithEmptyUsername() {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse("@domain.com"));
    }

    @Test
    void rejectsEmailWithEmptyDomain() {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse("user@"));
    }

    @Test
    void emailPartsHasCorrectAccessors() {
        EmailSlicer.EmailParts parts = new EmailSlicer.EmailParts("testuser", "testdomain.com");
        assertEquals("testuser", parts.getUsername());
        assertEquals("testdomain.com", parts.getDomain());
        assertEquals("testuser", parts.username());
        assertEquals("testdomain.com", parts.domain());
    }
}
