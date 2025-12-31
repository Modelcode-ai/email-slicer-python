package com.modelcode.emailslicer.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Comprehensive JUnit 5 test suite for EmailSlicer.
 * Tests cover valid email formats, invalid email formats, edge cases,
 * and exception behavior to achieve >90% line and branch coverage.
 */
class EmailSlicerTest {

    private final EmailSlicer slicer = new EmailSlicer();

    // ==================== Valid Email Tests ====================

    /**
     * Test parsing of a standard email format.
     */
    @Test
    void testValidStandardEmail() throws InvalidEmailException {
        EmailComponents result = slicer.slice("avimax37@gmail.com");

        assertNotNull(result);
        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    /**
     * Test parsing of email with dots and numbers in username.
     */
    @Test
    void testValidEmailWithDotsAndNumbers() throws InvalidEmailException {
        EmailComponents result = slicer.slice("user.name123@example.co.uk");

        assertNotNull(result);
        assertEquals("user.name123", result.username());
        assertEquals("example.co.uk", result.domain());
    }

    /**
     * Test parsing of email with leading whitespace (should be trimmed).
     */
    @Test
    void testValidEmailWithLeadingWhitespace() throws InvalidEmailException {
        EmailComponents result = slicer.slice("   user@example.com");

        assertNotNull(result);
        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    /**
     * Test parsing of email with trailing whitespace (should be trimmed).
     */
    @Test
    void testValidEmailWithTrailingWhitespace() throws InvalidEmailException {
        EmailComponents result = slicer.slice("user@example.com   ");

        assertNotNull(result);
        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    /**
     * Test parsing of email with both leading and trailing whitespace.
     */
    @Test
    void testValidEmailWithLeadingAndTrailingWhitespace()
            throws InvalidEmailException {
        EmailComponents result = slicer.slice("  user@example.com  ");

        assertNotNull(result);
        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    /**
     * Parameterized test for multiple valid email formats.
     */
    @ParameterizedTest
    @CsvSource({
        "simple@example.com, simple, example.com",
        "a@b.c, a, b.c",
        "test.email@domain.org, test.email, domain.org",
        "user+tag@mail.co.uk, user+tag, mail.co.uk",
        "name_123@test-domain.com, name_123, test-domain.com"
    })
    void testValidEmailVariations(String email, String expectedUsername,
                                   String expectedDomain)
            throws InvalidEmailException {
        EmailComponents result = slicer.slice(email);

        assertNotNull(result);
        assertEquals(expectedUsername, result.username());
        assertEquals(expectedDomain, result.domain());
    }

    // ==================== Invalid Email Tests ====================

    /**
     * Test that empty string input throws InvalidEmailException.
     */
    @Test
    void testEmptyStringThrowsException() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> slicer.slice("")
        );

        assertNotNull(exception.getMessage());
    }

    /**
     * Test that whitespace-only input throws InvalidEmailException.
     */
    @Test
    void testWhitespaceOnlyThrowsException() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> slicer.slice("   ")
        );

        assertNotNull(exception.getMessage());
    }

    /**
     * Test that input with tabs and spaces only throws InvalidEmailException.
     */
    @Test
    void testTabsAndSpacesOnlyThrowsException() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> slicer.slice("  \t  ")
        );

        assertNotNull(exception.getMessage());
    }

    /**
     * Test that input without '@' symbol throws InvalidEmailException.
     */
    @Test
    void testNoAtSymbolThrowsException() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> slicer.slice("username.domain.com")
        );

        assertNotNull(exception.getMessage());
    }

    /**
     * Test that input with multiple '@' symbols throws InvalidEmailException.
     */
    @Test
    void testMultipleAtSymbolsThrowsException() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> slicer.slice("user@@example.com")
        );

        assertNotNull(exception.getMessage());
    }

    /**
     * Test another variation of multiple '@' symbols.
     */
    @Test
    void testMultipleAtSymbolsVariation() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> slicer.slice("user@domain@com")
        );

        assertNotNull(exception.getMessage());
    }

    /**
     * Test that input with missing username throws InvalidEmailException.
     */
    @Test
    void testMissingUsernameThrowsException() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> slicer.slice("@example.com")
        );

        assertNotNull(exception.getMessage());
    }

    /**
     * Test that input with missing domain throws InvalidEmailException.
     */
    @Test
    void testMissingDomainThrowsException() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> slicer.slice("user@")
        );

        assertNotNull(exception.getMessage());
    }

    /**
     * Test that input with username but whitespace after '@' throws exception.
     */
    @Test
    void testMissingDomainWithWhitespaceThrowsException() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> slicer.slice("user@   ")
        );

        assertNotNull(exception.getMessage());
    }

    /**
     * Test that input with only '@' symbol throws InvalidEmailException.
     */
    @Test
    void testOnlyAtSymbolThrowsException() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> slicer.slice("@")
        );

        assertNotNull(exception.getMessage());
    }

    /**
     * Parameterized test for multiple invalid email formats.
     */
    @ParameterizedTest
    @ValueSource(strings = {
        "",
        "   ",
        "noatsign",
        "multiple@at@symbols",
        "@nodomain",
        "nousername@",
        "@",
        "  @  ",
        "user@@domain.com",
        "user@",
        "@domain.com"
    })
    void testInvalidEmailVariations(String invalidEmail) {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> slicer.slice(invalidEmail)
        );

        assertNotNull(exception.getMessage());
    }

    // ==================== Edge Cases ====================

    /**
     * Test minimal valid email (shortest possible valid format).
     */
    @Test
    void testMinimalValidEmail() throws InvalidEmailException {
        EmailComponents result = slicer.slice("a@b");

        assertNotNull(result);
        assertEquals("a", result.username());
        assertEquals("b", result.domain());
    }

    /**
     * Test that exception message is meaningful.
     */
    @Test
    void testExceptionMessageIsMeaningful() {
        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> slicer.slice("invalid")
        );

        String message = exception.getMessage();
        assertNotNull(message);
        // Verify message contains key terms
        assert message.toLowerCase().contains("invalid")
                || message.toLowerCase().contains("email");
    }
}
