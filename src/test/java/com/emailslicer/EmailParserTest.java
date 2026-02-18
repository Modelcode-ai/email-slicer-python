package com.emailslicer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for EmailParser.
 * <p>
 * Test categories:
 * 1. Valid emails - standard format, case preservation, whitespace handling
 * 2. Invalid emails - null input, empty string, whitespace-only, missing '@'
 * 3. Edge cases - empty username, empty domain, multiple '@' symbols
 */
class EmailParserTest {

    // ===== Valid Email Tests =====

    @Test
    @DisplayName("parse() with valid standard email returns correct username and domain")
    void testParse_ValidStandardEmail_ReturnsCorrectParts() {
        EmailParts result = EmailParser.parse("user@example.com");

        assertNotNull(result, "Expected non-null result for valid email");
        assertEquals("user", result.username(), "Username should match");
        assertEquals("example.com", result.domain(), "Domain should match");
    }

    @Test
    @DisplayName("parse() preserves case in username and domain")
    void testParse_MixedCase_PreservesCase() {
        EmailParts result = EmailParser.parse("USER@EXAMPLE.COM");

        assertNotNull(result, "Expected non-null result for valid email");
        assertEquals("USER", result.username(), "Username case should be preserved");
        assertEquals("EXAMPLE.COM", result.domain(), "Domain case should be preserved");
    }

    @Test
    @DisplayName("parse() with mixed case preserves exact case")
    void testParse_MixedCaseVariations_PreservesCase() {
        EmailParts result = EmailParser.parse("User123@Example.Com");

        assertNotNull(result, "Expected non-null result for valid email");
        assertEquals("User123", result.username(), "Username case should be preserved");
        assertEquals("Example.Com", result.domain(), "Domain case should be preserved");
    }

    @Test
    @DisplayName("parse() trims leading whitespace before parsing")
    void testParse_LeadingWhitespace_TrimmedCorrectly() {
        EmailParts result = EmailParser.parse("   user@example.com");

        assertNotNull(result, "Expected non-null result after trimming leading whitespace");
        assertEquals("user", result.username(), "Username should match after trim");
        assertEquals("example.com", result.domain(), "Domain should match after trim");
    }

    @Test
    @DisplayName("parse() trims trailing whitespace before parsing")
    void testParse_TrailingWhitespace_TrimmedCorrectly() {
        EmailParts result = EmailParser.parse("user@example.com   ");

        assertNotNull(result, "Expected non-null result after trimming trailing whitespace");
        assertEquals("user", result.username(), "Username should match after trim");
        assertEquals("example.com", result.domain(), "Domain should match after trim");
    }

    @Test
    @DisplayName("parse() trims both leading and trailing whitespace")
    void testParse_BothLeadingAndTrailingWhitespace_TrimmedCorrectly() {
        EmailParts result = EmailParser.parse("  user@example.com  ");

        assertNotNull(result, "Expected non-null result after trimming");
        assertEquals("user", result.username(), "Username should match after trim");
        assertEquals("example.com", result.domain(), "Domain should match after trim");
    }

    @Test
    @DisplayName("parse() handles tabs and other whitespace characters")
    void testParse_TabsAndWhitespace_TrimmedCorrectly() {
        EmailParts result = EmailParser.parse("\t\nuser@example.com\t\n");

        assertNotNull(result, "Expected non-null result after trimming tabs and newlines");
        assertEquals("user", result.username(), "Username should match after trim");
        assertEquals("example.com", result.domain(), "Domain should match after trim");
    }

    // ===== Invalid Email Tests =====

    @Test
    @DisplayName("parse() with null input returns null")
    void testParse_NullInput_ReturnsNull() {
        EmailParts result = EmailParser.parse(null);

        assertNull(result, "Expected null result for null input");
    }

    @Test
    @DisplayName("parse() with empty string returns null")
    void testParse_EmptyString_ReturnsNull() {
        EmailParts result = EmailParser.parse("");

        assertNull(result, "Expected null result for empty string");
    }

    @Test
    @DisplayName("parse() with whitespace-only string returns null")
    void testParse_WhitespaceOnly_ReturnsNull() {
        EmailParts result = EmailParser.parse("   ");

        assertNull(result, "Expected null result for whitespace-only string");
    }

    @Test
    @DisplayName("parse() with tabs and newlines only returns null")
    void testParse_TabsAndNewlinesOnly_ReturnsNull() {
        EmailParts result = EmailParser.parse("\t\n  \n\t");

        assertNull(result, "Expected null result for tabs and newlines only");
    }

    @Test
    @DisplayName("parse() with missing '@' symbol returns null")
    void testParse_MissingAtSymbol_ReturnsNull() {
        EmailParts result = EmailParser.parse("userexample.com");

        assertNull(result, "Expected null result for email without '@' symbol");
    }

    @Test
    @DisplayName("parse() with complex string but no '@' returns null")
    void testParse_ComplexStringNoAt_ReturnsNull() {
        EmailParts result = EmailParser.parse("user.name.example.com");

        assertNull(result, "Expected null result for string without '@' symbol");
    }

    // ===== Edge Case Tests =====

    @Test
    @DisplayName("parse() with '@' at start (empty username) is valid")
    void testParse_EmptyUsername_ReturnsValidParts() {
        EmailParts result = EmailParser.parse("@domain.com");

        assertNotNull(result, "Expected non-null result for email with empty username");
        assertEquals("", result.username(), "Username should be empty string");
        assertEquals("domain.com", result.domain(), "Domain should match");
    }

    @Test
    @DisplayName("parse() with '@' at end (empty domain) is valid")
    void testParse_EmptyDomain_ReturnsValidParts() {
        EmailParts result = EmailParser.parse("user@");

        assertNotNull(result, "Expected non-null result for email with empty domain");
        assertEquals("user", result.username(), "Username should match");
        assertEquals("", result.domain(), "Domain should be empty string");
    }

    @Test
    @DisplayName("parse() with only '@' (both empty) is valid")
    void testParse_OnlyAtSymbol_ReturnsEmptyParts() {
        EmailParts result = EmailParser.parse("@");

        assertNotNull(result, "Expected non-null result for single '@' symbol");
        assertEquals("", result.username(), "Username should be empty string");
        assertEquals("", result.domain(), "Domain should be empty string");
    }

    @Test
    @DisplayName("parse() with multiple '@' splits on first '@' only")
    void testParse_MultipleAtSymbols_SplitsOnFirst() {
        EmailParts result = EmailParser.parse("user@sub@domain.com");

        assertNotNull(result, "Expected non-null result for email with multiple '@' symbols");
        assertEquals("user", result.username(), "Username should be before first '@'");
        assertEquals("sub@domain.com", result.domain(), "Domain should include remaining '@' symbols");
    }

    @Test
    @DisplayName("parse() with three '@' symbols splits on first only")
    void testParse_ThreeAtSymbols_SplitsOnFirst() {
        EmailParts result = EmailParser.parse("user@sub@domain@com");

        assertNotNull(result, "Expected non-null result for email with three '@' symbols");
        assertEquals("user", result.username(), "Username should be before first '@'");
        assertEquals("sub@domain@com", result.domain(), "Domain should include all remaining '@' symbols");
    }

    @Test
    @DisplayName("parse() with complex username containing dots and numbers")
    void testParse_ComplexUsername_ParsedCorrectly() {
        EmailParts result = EmailParser.parse("first.last123@example.com");

        assertNotNull(result, "Expected non-null result for complex username");
        assertEquals("first.last123", result.username(), "Complex username should be preserved");
        assertEquals("example.com", result.domain(), "Domain should match");
    }

    @Test
    @DisplayName("parse() with subdomain in domain part")
    void testParse_Subdomain_ParsedCorrectly() {
        EmailParts result = EmailParser.parse("user@mail.example.com");

        assertNotNull(result, "Expected non-null result for subdomain");
        assertEquals("user", result.username(), "Username should match");
        assertEquals("mail.example.com", result.domain(), "Full subdomain should be preserved");
    }

    @Test
    @DisplayName("parse() with plus addressing (Gmail-style)")
    void testParse_PlusAddressing_ParsedCorrectly() {
        EmailParts result = EmailParser.parse("user+tag@example.com");

        assertNotNull(result, "Expected non-null result for plus addressing");
        assertEquals("user+tag", result.username(), "Username with plus should be preserved");
        assertEquals("example.com", result.domain(), "Domain should match");
    }
}
