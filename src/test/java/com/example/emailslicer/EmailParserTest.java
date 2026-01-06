package com.example.emailslicer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link EmailParser}.
 * Tests validate that the Java implementation preserves exact behavioral parity
 * with the original Python script.
 */
class EmailParserTest {

    // Valid Email Tests

    @Test
    @DisplayName("Parse standard format email")
    void parseStandardFormatEmail() {
        EmailResult result = EmailParser.parse("avimax37@gmail.com");

        assertNotNull(result);
        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    @DisplayName("Parse email with leading and trailing whitespace")
    void parseEmailWithWhitespace() {
        EmailResult result = EmailParser.parse("  user@domain.com  ");

        assertNotNull(result);
        assertEquals("user", result.username());
        assertEquals("domain.com", result.domain());
    }

    @Test
    @DisplayName("Parse email with multiple @ symbols")
    void parseEmailWithMultipleAtSymbols() {
        // Behavior matches Python's indexOf('@') which returns first occurrence
        // Username is everything before first @, domain is everything after
        EmailResult result = EmailParser.parse("user@sub@domain.com");

        assertNotNull(result);
        assertEquals("user", result.username());
        assertEquals("sub@domain.com", result.domain());
    }

    @Test
    @DisplayName("Parse email with dots and numbers")
    void parseComplexEmail() {
        EmailResult result = EmailParser.parse("john.doe123@example.co.uk");

        assertNotNull(result);
        assertEquals("john.doe123", result.username());
        assertEquals("example.co.uk", result.domain());
    }

    // Invalid Email Tests

    @Test
    @DisplayName("Throw exception for email without @ symbol")
    void throwExceptionForMissingAtSymbol() {
        assertThrows(IllegalArgumentException.class,
            () -> EmailParser.parse("userdomain.com"));
    }

    @Test
    @DisplayName("Throw exception for @ at start")
    void throwExceptionForAtSymbolAtStart() {
        assertThrows(IllegalArgumentException.class,
            () -> EmailParser.parse("@domain.com"));
    }

    @Test
    @DisplayName("Throw exception for @ at end")
    void throwExceptionForAtSymbolAtEnd() {
        assertThrows(IllegalArgumentException.class,
            () -> EmailParser.parse("user@"));
    }

    @Test
    @DisplayName("Throw exception for empty string")
    void throwExceptionForEmptyString() {
        assertThrows(IllegalArgumentException.class,
            () -> EmailParser.parse(""));
    }

    @Test
    @DisplayName("Throw exception for whitespace-only string")
    void throwExceptionForWhitespaceOnlyString() {
        assertThrows(IllegalArgumentException.class,
            () -> EmailParser.parse("   "));
    }

    @Test
    @DisplayName("Throw exception for null input")
    void throwExceptionForNullInput() {
        assertThrows(IllegalArgumentException.class,
            () -> EmailParser.parse(null));
    }

    @Test
    @DisplayName("Throw exception for @ surrounded by whitespace")
    void throwExceptionForAtOnlyWithWhitespace() {
        assertThrows(IllegalArgumentException.class,
            () -> EmailParser.parse("  @  "));
    }

    @Test
    @DisplayName("Throw exception for whitespace before @")
    void throwExceptionForWhitespaceBeforeAt() {
        // After trimming, this becomes "@domain.com" which is invalid
        assertThrows(IllegalArgumentException.class,
            () -> EmailParser.parse("  @domain.com"));
    }

    @Test
    @DisplayName("Throw exception for whitespace after @")
    void throwExceptionForWhitespaceAfterAt() {
        // After trimming, this becomes "user@" which is invalid
        assertThrows(IllegalArgumentException.class,
            () -> EmailParser.parse("user@  "));
    }
}
