package com.emailslicer;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for EmailParser validation and parsing logic.
 */
class EmailParserTest {

    // ===== Valid Email Tests =====

    @Test
    void testValidEmailWithSimpleDomain() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("user@domain.com");
        assertTrue(result.isPresent());
        assertEquals("user", result.get().getUsername());
        assertEquals("domain.com", result.get().getDomain());
    }

    @Test
    void testValidEmailWithSubdomain() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("first.last@example.co.uk");
        assertTrue(result.isPresent());
        assertEquals("first.last", result.get().getUsername());
        assertEquals("example.co.uk", result.get().getDomain());
    }

    @Test
    void testValidEmailMinimal() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("a@b.c");
        assertTrue(result.isPresent());
        assertEquals("a", result.get().getUsername());
        assertEquals("b.c", result.get().getDomain());
    }

    @Test
    void testValidEmailWithDigits() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("user123@example456.com");
        assertTrue(result.isPresent());
        assertEquals("user123", result.get().getUsername());
        assertEquals("example456.com", result.get().getDomain());
    }

    @Test
    void testValidEmailWithPlusSign() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("user+tag@example.com");
        assertTrue(result.isPresent());
        assertEquals("user+tag", result.get().getUsername());
        assertEquals("example.com", result.get().getDomain());
    }

    @Test
    void testValidEmailWithUnderscore() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("user_name@example.com");
        assertTrue(result.isPresent());
        assertEquals("user_name", result.get().getUsername());
        assertEquals("example.com", result.get().getDomain());
    }

    @Test
    void testValidEmailWithHyphen() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("user-name@example.com");
        assertTrue(result.isPresent());
        assertEquals("user-name", result.get().getUsername());
        assertEquals("example.com", result.get().getDomain());
    }

    @Test
    void testValidEmailWithLeadingAndTrailingWhitespace() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("  user@example.com  ");
        assertTrue(result.isPresent());
        assertEquals("user", result.get().getUsername());
        assertEquals("example.com", result.get().getDomain());
    }

    // ===== Invalid Email Tests: No @ Symbol =====

    @Test
    void testInvalidEmailNoAtSymbol() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("userdomain.com");
        assertFalse(result.isPresent());
    }

    // ===== Invalid Email Tests: Empty/Null/Whitespace =====

    @Test
    void testInvalidEmailNull() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse(null);
        assertFalse(result.isPresent());
    }

    @Test
    void testInvalidEmailEmptyString() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("");
        assertFalse(result.isPresent());
    }

    @Test
    void testInvalidEmailWhitespaceOnly() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("   ");
        assertFalse(result.isPresent());
    }

    // ===== Invalid Email Tests: @ Position =====

    @Test
    void testInvalidEmailAtSymbolAtStart() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("@domain.com");
        assertFalse(result.isPresent());
    }

    @Test
    void testInvalidEmailAtSymbolAtEnd() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("user@");
        assertFalse(result.isPresent());
    }

    @Test
    void testInvalidEmailMultipleAtSymbols() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("user@@example.com");
        assertFalse(result.isPresent());
    }

    @Test
    void testInvalidEmailMultipleAtSymbolsSeparated() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("user@example@com");
        assertFalse(result.isPresent());
    }

    // ===== Invalid Email Tests: Domain Without Dot (Critical Requirement) =====

    @Test
    void testDomainWithoutDotIsInvalid() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("user@domain");
        assertFalse(result.isPresent(), "Email with domain lacking a dot should be invalid");
    }

    @Test
    void testLocalhostIsInvalid() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("user@localhost");
        assertFalse(result.isPresent(), "Email with localhost domain should be invalid");
    }

    @Test
    void testIntranetDomainIsInvalid() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("user@intranet");
        assertFalse(result.isPresent(), "Email with intranet domain should be invalid");
    }

    @Test
    void testDomainWithDotAtStartIsInvalid() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("user@.domain.com");
        assertFalse(result.isPresent(), "Domain with dot at start should be invalid");
    }

    @Test
    void testDomainWithDotAtEndIsInvalid() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("user@domain.com.");
        assertFalse(result.isPresent(), "Domain with dot at end should be invalid");
    }

    @Test
    void testDomainWithOnlyDotIsInvalid() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("user@.");
        assertFalse(result.isPresent(), "Domain with only a dot should be invalid");
    }

    // ===== Invalid Email Tests: Internal Spaces =====

    @Test
    void testInvalidEmailSpaceInUsername() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("user name@example.com");
        assertFalse(result.isPresent());
    }

    @Test
    void testInvalidEmailSpaceInDomain() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("user@example .com");
        assertFalse(result.isPresent());
    }

    // ===== Invalid Email Tests: Non-ASCII Characters (Critical Requirement) =====

    @Test
    void testNonAsciiCharactersInUsernameAreInvalid() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("usér@example.com");
        assertFalse(result.isPresent(), "Email with non-ASCII characters in username should be invalid");
    }

    @Test
    void testNonAsciiCharactersInDomainAreInvalid() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("user@exámple.com");
        assertFalse(result.isPresent(), "Email with non-ASCII characters in domain should be invalid");
    }

    @Test
    void testUnicodeSymbolsAreInvalid() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("user@example😀.com");
        assertFalse(result.isPresent(), "Email with Unicode symbols should be invalid");
    }

    @Test
    void testChineseCharactersAreInvalid() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("用户@example.com");
        assertFalse(result.isPresent(), "Email with Chinese characters should be invalid");
    }

    @Test
    void testCyrillicCharactersAreInvalid() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("пользователь@example.com");
        assertFalse(result.isPresent(), "Email with Cyrillic characters should be invalid");
    }

    @Test
    void testGermanUmlautsAreInvalid() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("müller@example.com");
        assertFalse(result.isPresent(), "Email with German umlauts should be invalid");
    }

    @Test
    void testFrenchAccentsAreInvalid() {
        Optional<EmailParser.EmailParts> result = EmailParser.parse("françois@example.com");
        assertFalse(result.isPresent(), "Email with French accents should be invalid");
    }
}
