package com.modelcode.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for EmailParser.
 * Tests cover valid emails, whitespace handling, invalid inputs, and boundary cases.
 * Target: >90% line coverage of EmailParser logic.
 */
class EmailParserTest {

    private EmailParser parser;

    @BeforeEach
    void setUp() {
        parser = new EmailParser();
    }

    // ========== Valid Email Tests ==========

    @Test
    @DisplayName("parse() with typical email should extract username and domain correctly")
    void testParse_TypicalEmail_ExtractsUsernameAndDomain() {
        EmailResult result = parser.parse("avimax37@gmail.com");

        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    @DisplayName("parse() with email containing dots should parse correctly")
    void testParse_EmailWithDots_ParsesCorrectly() {
        EmailResult result = parser.parse("john.doe@example.co.uk");

        assertEquals("john.doe", result.username());
        assertEquals("example.co.uk", result.domain());
    }

    @Test
    @DisplayName("parse() with email containing underscores should parse correctly")
    void testParse_EmailWithUnderscores_ParsesCorrectly() {
        EmailResult result = parser.parse("user_name@domain_name.com");

        assertEquals("user_name", result.username());
        assertEquals("domain_name.com", result.domain());
    }

    @Test
    @DisplayName("parse() with email containing hyphens should parse correctly")
    void testParse_EmailWithHyphens_ParsesCorrectly() {
        EmailResult result = parser.parse("first-last@my-domain.org");

        assertEquals("first-last", result.username());
        assertEquals("my-domain.org", result.domain());
    }

    @Test
    @DisplayName("parse() with email containing digits should parse correctly")
    void testParse_EmailWithDigits_ParsesCorrectly() {
        EmailResult result = parser.parse("user123@domain456.net");

        assertEquals("user123", result.username());
        assertEquals("domain456.net", result.domain());
    }

    // ========== Boundary Case Tests ==========

    @Test
    @DisplayName("parse() with single-character username and domain should parse correctly")
    void testParse_SingleCharacterUsernameAndDomain_ParsesCorrectly() {
        EmailResult result = parser.parse("a@b");

        assertEquals("a", result.username());
        assertEquals("b", result.domain());
    }

    @Test
    @DisplayName("parse() with very long username should parse correctly")
    void testParse_LongUsername_ParsesCorrectly() {
        String longUsername = "verylongusernamethatcontainsmanycharsanddigits123";
        EmailResult result = parser.parse(longUsername + "@example.com");

        assertEquals(longUsername, result.username());
        assertEquals("example.com", result.domain());
    }

    // ========== Whitespace Handling Tests ==========

    @Test
    @DisplayName("parse() with leading spaces should trim and parse correctly")
    void testParse_LeadingSpaces_TrimsAndParsesCorrectly() {
        EmailResult result = parser.parse("   user@example.com");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    @DisplayName("parse() with trailing spaces should trim and parse correctly")
    void testParse_TrailingSpaces_TrimsAndParsesCorrectly() {
        EmailResult result = parser.parse("user@example.com   ");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    @DisplayName("parse() with leading and trailing spaces should trim and parse correctly")
    void testParse_LeadingAndTrailingSpaces_TrimsAndParsesCorrectly() {
        EmailResult result = parser.parse("  user@domain.com  ");

        assertEquals("user", result.username());
        assertEquals("domain.com", result.domain());
    }

    // ========== Invalid Input Tests - Null and Empty ==========

    @Test
    @DisplayName("parse() with null input should throw IllegalArgumentException")
    void testParse_NullInput_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse(null)
        );

        assertEquals("Email cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("parse() with empty string should throw IllegalArgumentException")
    void testParse_EmptyString_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse("")
        );

        assertEquals("Email cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("parse() with only spaces should throw IllegalArgumentException")
    void testParse_OnlySpaces_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse("   ")
        );

        assertEquals("Email cannot be empty", exception.getMessage());
    }

    // ========== Invalid Input Tests - Missing @ Symbol ==========

    @Test
    @DisplayName("parse() with no @ symbol should throw IllegalArgumentException")
    void testParse_NoAtSymbol_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse("userexample.com")
        );

        assertEquals("Email must contain @ symbol", exception.getMessage());
    }

    // ========== Invalid Input Tests - Multiple @ Symbols ==========

    @Test
    @DisplayName("parse() with multiple @ symbols should throw IllegalArgumentException")
    void testParse_MultipleAtSymbols_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse("user@@example.com")
        );

        assertEquals("Email must contain exactly one @ symbol", exception.getMessage());
    }

    @Test
    @DisplayName("parse() with two @ symbols in different positions should throw IllegalArgumentException")
    void testParse_TwoAtSymbolsDifferentPositions_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse("user@domain@com")
        );

        assertEquals("Email must contain exactly one @ symbol", exception.getMessage());
    }

    // ========== Invalid Input Tests - Empty Username ==========

    @Test
    @DisplayName("parse() with empty username should throw IllegalArgumentException")
    void testParse_EmptyUsername_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse("@example.com")
        );

        assertEquals("Username (before @) cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("parse() with @ at start after trimming should throw IllegalArgumentException")
    void testParse_AtSymbolAtStartAfterTrimming_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse("  @domain.com")
        );

        assertEquals("Username (before @) cannot be empty", exception.getMessage());
    }

    // ========== Invalid Input Tests - Empty Domain ==========

    @Test
    @DisplayName("parse() with empty domain should throw IllegalArgumentException")
    void testParse_EmptyDomain_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse("user@")
        );

        assertEquals("Domain (after @) cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("parse() with @ at end after trimming should throw IllegalArgumentException")
    void testParse_AtSymbolAtEndAfterTrimming_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse("user@  ")
        );

        assertEquals("Domain (after @) cannot be empty", exception.getMessage());
    }
}
