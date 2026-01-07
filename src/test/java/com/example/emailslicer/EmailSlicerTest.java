package com.example.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for EmailSlicer class.
 * Tests verify behavioral parity with the Python version.
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    // ==================== Valid Email Parsing Tests ====================

    @Test
    @DisplayName("Should parse the README example: avimax37@gmail.com")
    void testReadmeExample() {
        EmailResult result = slicer.parseEmail("avimax37@gmail.com");

        assertNotNull(result);
        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    @DisplayName("Should validate the README example as valid")
    void testReadmeExampleValidation() {
        assertTrue(slicer.isValidEmail("avimax37@gmail.com"));
    }

    @Test
    @DisplayName("Should parse email with numbers in username")
    void testEmailWithNumbers() {
        EmailResult result = slicer.parseEmail("user123@example.com");

        assertEquals("user123", result.username());
        assertEquals("example.com", result.domain());
        assertTrue(slicer.isValidEmail("user123@example.com"));
    }

    @Test
    @DisplayName("Should parse email with subdomain")
    void testEmailWithSubdomain() {
        EmailResult result = slicer.parseEmail("admin@mail.company.com");

        assertEquals("admin", result.username());
        assertEquals("mail.company.com", result.domain());
        assertTrue(slicer.isValidEmail("admin@mail.company.com"));
    }

    @Test
    @DisplayName("Should parse email with dots in username")
    void testEmailWithDotsInUsername() {
        EmailResult result = slicer.parseEmail("john.doe@example.org");

        assertEquals("john.doe", result.username());
        assertEquals("example.org", result.domain());
        assertTrue(slicer.isValidEmail("john.doe@example.org"));
    }

    @Test
    @DisplayName("Should parse email with hyphens")
    void testEmailWithHyphens() {
        EmailResult result = slicer.parseEmail("test-user@test-domain.com");

        assertEquals("test-user", result.username());
        assertEquals("test-domain.com", result.domain());
        assertTrue(slicer.isValidEmail("test-user@test-domain.com"));
    }

    @Test
    @DisplayName("Should parse email with plus sign in username")
    void testEmailWithPlusSign() {
        EmailResult result = slicer.parseEmail("user+tag@example.com");

        assertEquals("user+tag", result.username());
        assertEquals("example.com", result.domain());
        assertTrue(slicer.isValidEmail("user+tag@example.com"));
    }

    // ==================== Whitespace Handling Tests ====================

    @Test
    @DisplayName("Should trim leading whitespace")
    void testLeadingWhitespace() {
        EmailResult result = slicer.parseEmail("  user@domain.com");

        assertEquals("user", result.username());
        assertEquals("domain.com", result.domain());
        assertTrue(slicer.isValidEmail("  user@domain.com"));
    }

    @Test
    @DisplayName("Should trim trailing whitespace")
    void testTrailingWhitespace() {
        EmailResult result = slicer.parseEmail("user@domain.com  ");

        assertEquals("user", result.username());
        assertEquals("domain.com", result.domain());
        assertTrue(slicer.isValidEmail("user@domain.com  "));
    }

    @Test
    @DisplayName("Should trim both leading and trailing whitespace")
    void testBothLeadingAndTrailingWhitespace() {
        EmailResult result = slicer.parseEmail("  user@domain.com  ");

        assertEquals("user", result.username());
        assertEquals("domain.com", result.domain());
        assertTrue(slicer.isValidEmail("  user@domain.com  "));
    }

    @Test
    @DisplayName("Should trim tabs and other whitespace characters")
    void testVariousWhitespaceCharacters() {
        EmailResult result = slicer.parseEmail("\t\nuser@domain.com\r\n");

        assertEquals("user", result.username());
        assertEquals("domain.com", result.domain());
        assertTrue(slicer.isValidEmail("\t\nuser@domain.com\r\n"));
    }

    // ==================== Invalid Input Tests ====================

    @Test
    @DisplayName("Should reject email without @ symbol")
    void testMissingAtSymbol() {
        assertFalse(slicer.isValidEmail("nodomain"));
        assertFalse(slicer.isValidEmail("user.domain.com"));
    }

    @Test
    @DisplayName("Should throw exception when parsing email without @")
    void testParseEmailWithoutAtThrowsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> slicer.parseEmail("nodomain")
        );
        assertEquals("email must contain '@'", exception.getMessage());
    }

    @Test
    @DisplayName("Should reject empty string")
    void testEmptyString() {
        assertFalse(slicer.isValidEmail(""));
    }

    @Test
    @DisplayName("Should reject whitespace-only string")
    void testWhitespaceOnly() {
        assertFalse(slicer.isValidEmail("   "));
        assertFalse(slicer.isValidEmail("\t\n\r"));
    }

    // ==================== Edge Case Tests ====================

    @Test
    @DisplayName("Should parse email with multiple @ symbols (use first @)")
    void testMultipleAtSymbols() {
        EmailResult result = slicer.parseEmail("a@b@c");

        assertEquals("a", result.username());
        assertEquals("b@c", result.domain());
        assertTrue(slicer.isValidEmail("a@b@c"));
    }

    @Test
    @DisplayName("Should parse email with @ at start (empty username)")
    void testAtSymbolAtStart() {
        EmailResult result = slicer.parseEmail("@domain.com");

        assertEquals("", result.username());
        assertEquals("domain.com", result.domain());
        assertTrue(slicer.isValidEmail("@domain.com"));
    }

    @Test
    @DisplayName("Should parse email with @ at end (empty domain)")
    void testAtSymbolAtEnd() {
        EmailResult result = slicer.parseEmail("user@");

        assertEquals("user", result.username());
        assertEquals("", result.domain());
        assertTrue(slicer.isValidEmail("user@"));
    }

    @Test
    @DisplayName("Should parse single @ symbol (both username and domain empty)")
    void testSingleAtSymbol() {
        EmailResult result = slicer.parseEmail("@");

        assertEquals("", result.username());
        assertEquals("", result.domain());
        assertTrue(slicer.isValidEmail("@"));
    }

    @Test
    @DisplayName("Should parse complex email with multiple @ after trimming whitespace")
    void testMultipleAtWithWhitespace() {
        EmailResult result = slicer.parseEmail("  user@domain@test  ");

        assertEquals("user", result.username());
        assertEquals("domain@test", result.domain());
        assertTrue(slicer.isValidEmail("  user@domain@test  "));
    }

    // ==================== Null Input Tests ====================

    @Test
    @DisplayName("Should return false for null input in isValidEmail")
    void testIsValidEmailWithNull() {
        assertFalse(slicer.isValidEmail(null));
    }

    @Test
    @DisplayName("Should throw exception for null input in parseEmail")
    void testParseEmailWithNull() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> slicer.parseEmail(null)
        );
        assertEquals("email must not be null", exception.getMessage());
    }

    // ==================== Additional Validation Coverage ====================

    @Test
    @DisplayName("Should validate various valid email formats")
    void testVariousValidEmailFormats() {
        assertTrue(slicer.isValidEmail("a@b"));
        assertTrue(slicer.isValidEmail("test@test"));
        assertTrue(slicer.isValidEmail("1@2"));
        assertTrue(slicer.isValidEmail("_@_"));
        assertTrue(slicer.isValidEmail("user@localhost"));
    }

    @Test
    @DisplayName("Should reject various invalid formats")
    void testVariousInvalidFormats() {
        assertFalse(slicer.isValidEmail(""));
        assertFalse(slicer.isValidEmail("   "));
        assertFalse(slicer.isValidEmail("user"));
        assertFalse(slicer.isValidEmail("user.domain"));
        assertFalse(slicer.isValidEmail("user#domain.com"));
    }

    // ==================== EmailResult Record Tests ====================

    @Test
    @DisplayName("EmailResult should properly store username and domain")
    void testEmailResultRecord() {
        EmailResult result = new EmailResult("testuser", "testdomain.com");

        assertEquals("testuser", result.username());
        assertEquals("testdomain.com", result.domain());
    }

    @Test
    @DisplayName("EmailResult should support empty username")
    void testEmailResultWithEmptyUsername() {
        EmailResult result = new EmailResult("", "domain.com");

        assertEquals("", result.username());
        assertEquals("domain.com", result.domain());
    }

    @Test
    @DisplayName("EmailResult should support empty domain")
    void testEmailResultWithEmptyDomain() {
        EmailResult result = new EmailResult("user", "");

        assertEquals("user", result.username());
        assertEquals("", result.domain());
    }

    @Test
    @DisplayName("EmailResult should support both empty username and domain")
    void testEmailResultWithBothEmpty() {
        EmailResult result = new EmailResult("", "");

        assertEquals("", result.username());
        assertEquals("", result.domain());
    }
}
