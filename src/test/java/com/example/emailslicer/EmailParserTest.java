package com.example.emailslicer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for EmailParser.
 */
class EmailParserTest {

    // Valid email parsing tests
    @ParameterizedTest
    @CsvSource({
        "avimax37@gmail.com, avimax37, gmail.com",
        "user@domain.co.uk, user, domain.co.uk",
        "test@example.com, test, example.com",
        "john.doe@company.org, john.doe, company.org"
    })
    void testSplitEmailValid(final String email, final String expectedUsername, final String expectedDomain) {
        final String[] result = EmailParser.splitEmail(email);
        assertArrayEquals(new String[]{expectedUsername, expectedDomain}, result);
    }

    @Test
    void testSplitEmailWithWhitespace() {
        final String[] result = EmailParser.splitEmail("  test@example.com  ");
        assertArrayEquals(new String[]{"test", "example.com"}, result);
    }

    @Test
    void testExtractUsername() {
        assertEquals("avimax37", EmailParser.extractUsername("avimax37@gmail.com"));
        assertEquals("test", EmailParser.extractUsername("  test@example.com  "));
    }

    @Test
    void testExtractDomain() {
        assertEquals("gmail.com", EmailParser.extractDomain("avimax37@gmail.com"));
        assertEquals("example.com", EmailParser.extractDomain("  test@example.com  "));
    }

    // Invalid email detection tests
    @ParameterizedTest
    @ValueSource(strings = {
        "userexample.com",      // No @
        "@example.com",         // @ at start
        "user@",                // @ at end
        "user@@example.com",    // Multiple @
        "user@ex@ample.com",    // Multiple @
        "",                     // Empty string
        "   "                   // Whitespace only
    })
    void testIsValidEmailInvalid(final String email) {
        assertFalse(EmailParser.isValidEmail(email));
    }

    @Test
    void testIsValidEmailNull() {
        assertFalse(EmailParser.isValidEmail(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "avimax37@gmail.com",
        "user@domain.co.uk",
        "test@example.com"
    })
    void testIsValidEmailValid(final String email) {
        assertTrue(EmailParser.isValidEmail(email));
    }

    @Test
    void testIsValidEmailWithWhitespace() {
        assertTrue(EmailParser.isValidEmail("  test@example.com  "));
    }

    // Exception behavior tests
    @Test
    void testSplitEmailThrowsExceptionForNull() {
        assertThrows(IllegalArgumentException.class, () -> EmailParser.splitEmail(null));
    }

    @Test
    void testSplitEmailThrowsExceptionForEmpty() {
        assertThrows(IllegalArgumentException.class, () -> EmailParser.splitEmail(""));
    }

    @Test
    void testSplitEmailThrowsExceptionForNoAt() {
        assertThrows(IllegalArgumentException.class, () -> EmailParser.splitEmail("userexample.com"));
    }

    @Test
    void testSplitEmailThrowsExceptionForMultipleAt() {
        assertThrows(IllegalArgumentException.class, () -> EmailParser.splitEmail("user@@example.com"));
    }

    @Test
    void testSplitEmailThrowsExceptionForAtAtStart() {
        assertThrows(IllegalArgumentException.class, () -> EmailParser.splitEmail("@example.com"));
    }

    @Test
    void testSplitEmailThrowsExceptionForAtAtEnd() {
        assertThrows(IllegalArgumentException.class, () -> EmailParser.splitEmail("user@"));
    }

    @Test
    void testExtractUsernameThrowsExceptionForInvalid() {
        assertThrows(IllegalArgumentException.class, () -> EmailParser.extractUsername("invalid"));
    }

    @Test
    void testExtractDomainThrowsExceptionForInvalid() {
        assertThrows(IllegalArgumentException.class, () -> EmailParser.extractDomain("invalid"));
    }

    // Consistency tests
    @ParameterizedTest
    @ValueSource(strings = {
        "avimax37@gmail.com",
        "user@domain.co.uk",
        "test@example.com"
    })
    void testConsistencyBetweenMethods(final String email) {
        assertTrue(EmailParser.isValidEmail(email));

        final String username = EmailParser.extractUsername(email);
        final String domain = EmailParser.extractDomain(email);

        final String reconstructed = username + "@" + domain;
        assertEquals(email.strip(), reconstructed);
    }
}
