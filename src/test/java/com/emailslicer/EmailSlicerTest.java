package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit test suite for the {@link EmailSlicer} class.
 *
 * <p>This test class covers all validation rules and parsing scenarios
 * specified in the modernization spec, including:</p>
 * <ul>
 *   <li>Valid email parsing with various formats</li>
 *   <li>Whitespace handling (leading/trailing spaces)</li>
 *   <li>Null and empty input validation</li>
 *   <li>Missing '@' symbol validation</li>
 *   <li>'@' position validation (at start or end)</li>
 *   <li>Multiple '@' symbol validation</li>
 * </ul>
 */
@DisplayName("EmailSlicer Unit Tests")
class EmailSlicerTest {

    private EmailSlicer emailSlicer;

    @BeforeEach
    void setUp() {
        emailSlicer = new EmailSlicer();
    }

    // ========== Valid Email Parsing Tests ==========

    @Test
    @DisplayName("Should parse simple email with username and domain")
    void testParseSimpleEmail() throws InvalidEmailException {
        EmailResult result = emailSlicer.parse("avimax37@gmail.com");

        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    @DisplayName("Should parse email with subdomain")
    void testParseEmailWithSubdomain() throws InvalidEmailException {
        EmailResult result = emailSlicer.parse("user@mail.example.co.uk");

        assertEquals("user", result.username());
        assertEquals("mail.example.co.uk", result.domain());
    }

    @Test
    @DisplayName("Should parse email with mixed case and preserve case")
    void testParseEmailWithMixedCase() throws InvalidEmailException {
        EmailResult result = emailSlicer.parse("John.Doe@Example.COM");

        assertEquals("John.Doe", result.username());
        assertEquals("Example.COM", result.domain());
    }

    @Test
    @DisplayName("Should parse email with numeric components")
    void testParseEmailWithNumericComponents() throws InvalidEmailException {
        EmailResult result = emailSlicer.parse("user123@456domain.com");

        assertEquals("user123", result.username());
        assertEquals("456domain.com", result.domain());
    }

    @Test
    @DisplayName("Should parse email with dots in username")
    void testParseEmailWithDotsInUsername() throws InvalidEmailException {
        EmailResult result = emailSlicer.parse("first.last@example.org");

        assertEquals("first.last", result.username());
        assertEquals("example.org", result.domain());
    }

    // ========== Whitespace Handling Tests ==========

    @Test
    @DisplayName("Should trim leading whitespace before parsing")
    void testParseEmailWithLeadingWhitespace() throws InvalidEmailException {
        EmailResult result = emailSlicer.parse("  user@example.com");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    @DisplayName("Should trim trailing whitespace before parsing")
    void testParseEmailWithTrailingWhitespace() throws InvalidEmailException {
        EmailResult result = emailSlicer.parse("user@example.com  ");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    @DisplayName("Should trim both leading and trailing whitespace")
    void testParseEmailWithBothLeadingAndTrailingWhitespace() throws InvalidEmailException {
        EmailResult result = emailSlicer.parse("  user@example.com  ");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    // ========== Null and Empty Input Validation Tests ==========

    @Test
    @DisplayName("Should throw InvalidEmailException when email is null")
    void testParseNullEmail() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> emailSlicer.parse(null)
        );

        assertEquals("Email address must not be null.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidEmailException when email is empty string")
    void testParseEmptyEmail() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> emailSlicer.parse("")
        );

        assertEquals("Email address must not be empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidEmailException when email is only whitespace")
    void testParseWhitespaceOnlyEmail() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> emailSlicer.parse("   ")
        );

        assertEquals("Email address must not be empty.", exception.getMessage());
    }

    // ========== Missing '@' Symbol Validation Tests ==========

    @Test
    @DisplayName("Should throw InvalidEmailException when '@' is missing")
    void testParseMissingAtSymbol() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> emailSlicer.parse("userexample.com")
        );

        assertEquals("Email address must contain a single '@' symbol.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidEmailException when email has no '@' symbol")
    void testParseNoAtSymbol() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> emailSlicer.parse("user.example.com")
        );

        assertEquals("Email address must contain a single '@' symbol.", exception.getMessage());
    }

    // ========== '@' Position Validation Tests ==========

    @Test
    @DisplayName("Should throw InvalidEmailException when '@' is at the start")
    void testParseAtSymbolAtStart() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> emailSlicer.parse("@example.com")
        );

        assertEquals("Email username must not be empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidEmailException when '@' is at the end")
    void testParseAtSymbolAtEnd() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> emailSlicer.parse("user@")
        );

        assertEquals("Email domain must not be empty.", exception.getMessage());
    }

    // ========== Multiple '@' Symbol Validation Tests ==========

    @Test
    @DisplayName("Should throw InvalidEmailException when email has multiple '@' symbols")
    void testParseMultipleAtSymbols() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> emailSlicer.parse("user@@example.com")
        );

        assertEquals("Email address must contain exactly one '@' symbol.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidEmailException when email has three '@' symbols")
    void testParseThreeAtSymbols() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> emailSlicer.parse("user@test@example.com")
        );

        assertEquals("Email address must contain exactly one '@' symbol.", exception.getMessage());
    }

    // ========== Additional Edge Case Tests ==========

    @Test
    @DisplayName("Should parse email with single character username")
    void testParseSingleCharacterUsername() throws InvalidEmailException {
        EmailResult result = emailSlicer.parse("a@example.com");

        assertEquals("a", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    @DisplayName("Should parse email with single character domain")
    void testParseSingleCharacterDomain() throws InvalidEmailException {
        EmailResult result = emailSlicer.parse("user@d");

        assertEquals("user", result.username());
        assertEquals("d", result.domain());
    }

    @Test
    @DisplayName("Should parse minimal valid email with two characters")
    void testParseMinimalEmail() throws InvalidEmailException {
        EmailResult result = emailSlicer.parse("a@b");

        assertEquals("a", result.username());
        assertEquals("b", result.domain());
    }
}
