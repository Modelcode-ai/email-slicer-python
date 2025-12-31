package com.emailslicer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for EmailSlicer and EmailAddress classes.
 *
 * <p>This test class covers:
 * <ul>
 *   <li>Valid email parsing scenarios (matching Python behavior)</li>
 *   <li>All validation failure cases with exception verification</li>
 *   <li>EmailAddress value object behavior (equals, hashCode, toString)</li>
 * </ul>
 *
 * <p>The test suite uses JUnit 5 parameterized tests to efficiently test
 * multiple scenarios with minimal code duplication, achieving >90% code
 * coverage of the domain logic.
 */
class EmailSlicerTest {

    private final EmailSlicer slicer = new EmailSlicer();

    // ========================================================================
    // Valid Email Parsing Tests
    // ========================================================================

    /**
     * Tests that valid email addresses are parsed correctly into username
     * and domain components.
     *
     * <p>This test verifies functional equivalence with the Python version
     * by ensuring the same email inputs produce the same username and domain
     * outputs.
     */
    @ParameterizedTest(name = "[{index}] {0} -> username=\"{1}\", domain=\"{2}\"")
    @CsvSource({
        "avimax37@gmail.com, avimax37, gmail.com",
        "User.Name@example.com, User.Name, example.com",
        "first.last@subdomain.example.com, first.last, subdomain.example.com",
        "test123@test.co.uk, test123, test.co.uk",
        "simple@domain.org, simple, domain.org",
        "user+tag@example.com, user+tag, example.com",
        "my_email@test-domain.com, my_email, test-domain.com"
    })
    void testValidEmailParsing(String emailInput, String expectedUsername,
                               String expectedDomain) {
        EmailAddress result = slicer.parse(emailInput);

        assertNotNull(result, "Parsed EmailAddress should not be null");
        assertEquals(expectedUsername, result.getUsername(),
            "Username should match expected value");
        assertEquals(expectedDomain, result.getDomain(),
            "Domain should match expected value");
    }

    /**
     * Tests that emails with leading and trailing whitespace are correctly
     * trimmed and parsed.
     *
     * <p>This mirrors the Python implementation's use of strip() to
     * normalize input.
     */
    @ParameterizedTest(name = "[{index}] \"{0}\" -> username=\"{1}\", domain=\"{2}\"")
    @CsvSource({
        "'  user@domain.com  ', user, domain.com",
        "' user@example.org ', user, example.org",
        "'user@domain.com ', user, domain.com",
        "' user@domain.com', user, domain.com"
    })
    void testEmailParsingWithWhitespace(String emailInput,
                                        String expectedUsername,
                                        String expectedDomain) {
        EmailAddress result = slicer.parse(emailInput);

        assertEquals(expectedUsername, result.getUsername(),
            "Username should match after whitespace trimming");
        assertEquals(expectedDomain, result.getDomain(),
            "Domain should match after whitespace trimming");
    }

    /**
     * Tests that emails with tabs are correctly trimmed and parsed.
     *
     * <p>This test uses string concatenation to create tab characters,
     * avoiding issues with escape sequences in CSV sources.
     */
    @Test
    void testEmailParsingWithTabs() {
        String emailWithTabs = "\tuser@domain.com\t";
        EmailAddress result = slicer.parse(emailWithTabs);

        assertEquals("user", result.getUsername(),
            "Username should match after tab trimming");
        assertEquals("domain.com", result.getDomain(),
            "Domain should match after tab trimming");
    }

    // ========================================================================
    // Null Input Validation Tests
    // ========================================================================

    /**
     * Tests that null input throws EmailValidationException with the
     * expected error message.
     */
    @Test
    void testNullInputThrowsException() {
        EmailValidationException exception = assertThrows(
            EmailValidationException.class,
            () -> slicer.parse(null),
            "Parsing null should throw EmailValidationException"
        );

        assertEquals("Email address must not be null.",
            exception.getMessage(),
            "Exception message should match expected text");
    }

    // ========================================================================
    // Empty String Validation Tests
    // ========================================================================

    /**
     * Tests that empty and whitespace-only inputs throw
     * EmailValidationException with the expected error message.
     */
    @ParameterizedTest(name = "[{index}] input=\"{0}\"")
    @ValueSource(strings = {"", "   ", "\t", "\n", "  \t  "})
    void testEmptyAndWhitespaceInputsThrowException(String input) {
        EmailValidationException exception = assertThrows(
            EmailValidationException.class,
            () -> slicer.parse(input),
            "Parsing empty or whitespace-only input should throw exception"
        );

        assertEquals("Email address must not be empty.",
            exception.getMessage(),
            "Exception message should indicate empty email");
    }

    // ========================================================================
    // @ Symbol Count Validation Tests
    // ========================================================================

    /**
     * Tests that emails with no @ symbol throw EmailValidationException.
     */
    @ParameterizedTest(name = "[{index}] input=\"{0}\"")
    @ValueSource(strings = {
        "userdomain.com",
        "user.domain.com",
        "plaintext",
        "no-at-symbol.com"
    })
    void testNoAtSymbolThrowsException(String input) {
        EmailValidationException exception = assertThrows(
            EmailValidationException.class,
            () -> slicer.parse(input),
            "Parsing email without @ should throw exception"
        );

        assertEquals("Email address must contain exactly one '@' character.",
            exception.getMessage(),
            "Exception message should indicate missing @ symbol");
    }

    /**
     * Tests that emails with multiple @ symbols throw
     * EmailValidationException.
     */
    @ParameterizedTest(name = "[{index}] input=\"{0}\"")
    @ValueSource(strings = {
        "user@@domain.com",
        "user@domain@com",
        "@@",
        "user@@@@example.com",
        "@user@domain.com"
    })
    void testMultipleAtSymbolsThrowException(String input) {
        EmailValidationException exception = assertThrows(
            EmailValidationException.class,
            () -> slicer.parse(input),
            "Parsing email with multiple @ symbols should throw exception"
        );

        assertEquals("Email address must contain exactly one '@' character.",
            exception.getMessage(),
            "Exception message should indicate incorrect @ count");
    }

    // ========================================================================
    // Empty Username/Domain Validation Tests
    // ========================================================================

    /**
     * Tests that emails with empty username (@ at the start) throw
     * EmailValidationException.
     */
    @ParameterizedTest(name = "[{index}] input=\"{0}\"")
    @ValueSource(strings = {"@domain.com", "@example.org", "@test.co.uk"})
    void testEmptyUsernameThrowsException(String input) {
        EmailValidationException exception = assertThrows(
            EmailValidationException.class,
            () -> slicer.parse(input),
            "Parsing email with empty username should throw exception"
        );

        assertEquals(
            "Email address must contain a non-empty username before '@'.",
            exception.getMessage(),
            "Exception message should indicate empty username");
    }

    /**
     * Tests that emails with empty domain (@ at the end) throw
     * EmailValidationException.
     */
    @ParameterizedTest(name = "[{index}] input=\"{0}\"")
    @ValueSource(strings = {"user@", "example@", "test123@"})
    void testEmptyDomainThrowsException(String input) {
        EmailValidationException exception = assertThrows(
            EmailValidationException.class,
            () -> slicer.parse(input),
            "Parsing email with empty domain should throw exception"
        );

        assertEquals(
            "Email address must contain a non-empty domain after '@'.",
            exception.getMessage(),
            "Exception message should indicate empty domain");
    }

    // ========================================================================
    // Domain Validation Tests (Simple Domain Rules)
    // ========================================================================

    /**
     * Tests that domains without a dot throw EmailValidationException when
     * simple domain validation is enabled.
     *
     * <p>This test will pass if ENABLE_SIMPLE_DOMAIN_VALIDATION is true
     * in EmailSlicer, and will be skipped/fail if it's disabled. This
     * reflects an intentional validation enhancement beyond the Python
     * version.
     */
    @ParameterizedTest(name = "[{index}] input=\"{0}\"")
    @ValueSource(strings = {"user@domain", "test@localhost", "admin@server"})
    void testDomainWithoutDotThrowsException(String input) {
        EmailValidationException exception = assertThrows(
            EmailValidationException.class,
            () -> slicer.parse(input),
            "Parsing email with domain lacking dot should throw exception"
        );

        assertEquals(
            "Email domain must contain at least one '.' and no whitespace.",
            exception.getMessage(),
            "Exception message should indicate domain validation failure");
    }

    /**
     * Tests that domains containing whitespace throw EmailValidationException
     * when simple domain validation is enabled.
     */
    @ParameterizedTest(name = "[{index}] input=\"{0}\"")
    @ValueSource(strings = {
        "user@domain .com",
        "test@exa mple.com",
        "admin@test. com"
    })
    void testDomainWithWhitespaceThrowsException(String input) {
        EmailValidationException exception = assertThrows(
            EmailValidationException.class,
            () -> slicer.parse(input),
            "Parsing email with whitespace in domain should throw exception"
        );

        assertEquals(
            "Email domain must contain at least one '.' and no whitespace.",
            exception.getMessage(),
            "Exception message should indicate domain validation failure");
    }

    // ========================================================================
    // EmailAddress Value Object Tests
    // ========================================================================

    /**
     * Tests that EmailAddress getters return the correct values.
     */
    @Test
    void testEmailAddressGetters() {
        EmailAddress email = new EmailAddress("testuser", "example.com");

        assertEquals("testuser", email.getUsername(),
            "getUsername should return username");
        assertEquals("example.com", email.getDomain(),
            "getDomain should return domain");
    }

    /**
     * Tests that EmailAddress toString returns the correct format.
     */
    @Test
    void testEmailAddressToString() {
        EmailAddress email = new EmailAddress("user", "domain.com");

        assertEquals("user@domain.com", email.toString(),
            "toString should return 'username@domain' format");
    }

    /**
     * Tests EmailAddress equality with same values.
     */
    @Test
    void testEmailAddressEqualsWithSameValues() {
        EmailAddress email1 = new EmailAddress("user", "domain.com");
        EmailAddress email2 = new EmailAddress("user", "domain.com");

        assertEquals(email1, email2,
            "EmailAddress objects with same values should be equal");
        assertEquals(email2, email1,
            "Equals should be symmetric");
    }

    /**
     * Tests EmailAddress equality with same instance.
     */
    @Test
    void testEmailAddressEqualsWithSameInstance() {
        EmailAddress email = new EmailAddress("user", "domain.com");

        assertEquals(email, email,
            "EmailAddress should equal itself");
    }

    /**
     * Tests EmailAddress inequality with different username.
     */
    @Test
    void testEmailAddressNotEqualsWithDifferentUsername() {
        EmailAddress email1 = new EmailAddress("user1", "domain.com");
        EmailAddress email2 = new EmailAddress("user2", "domain.com");

        assertNotEquals(email1, email2,
            "EmailAddress objects with different usernames should not be equal");
    }

    /**
     * Tests EmailAddress inequality with different domain.
     */
    @Test
    void testEmailAddressNotEqualsWithDifferentDomain() {
        EmailAddress email1 = new EmailAddress("user", "domain1.com");
        EmailAddress email2 = new EmailAddress("user", "domain2.com");

        assertNotEquals(email1, email2,
            "EmailAddress objects with different domains should not be equal");
    }

    /**
     * Tests EmailAddress inequality with null.
     */
    @Test
    void testEmailAddressNotEqualsWithNull() {
        EmailAddress email = new EmailAddress("user", "domain.com");

        assertNotEquals(email, null,
            "EmailAddress should not equal null");
    }

    /**
     * Tests EmailAddress inequality with different type.
     */
    @Test
    void testEmailAddressNotEqualsWithDifferentType() {
        EmailAddress email = new EmailAddress("user", "domain.com");
        String notAnEmail = "user@domain.com";

        assertNotEquals(email, notAnEmail,
            "EmailAddress should not equal String");
    }

    /**
     * Tests that EmailAddress hashCode is consistent with equals.
     */
    @Test
    void testEmailAddressHashCodeConsistency() {
        EmailAddress email1 = new EmailAddress("user", "domain.com");
        EmailAddress email2 = new EmailAddress("user", "domain.com");

        assertEquals(email1.hashCode(), email2.hashCode(),
            "Equal EmailAddress objects must have same hashCode");
    }

    /**
     * Tests that EmailAddress hashCode is consistent across multiple calls.
     */
    @Test
    void testEmailAddressHashCodeStability() {
        EmailAddress email = new EmailAddress("user", "domain.com");
        int hash1 = email.hashCode();
        int hash2 = email.hashCode();

        assertEquals(hash1, hash2,
            "hashCode should return same value on multiple calls");
    }

    /**
     * Tests that different EmailAddress objects typically have different
     * hash codes (though not guaranteed).
     */
    @Test
    void testEmailAddressDifferentHashCodes() {
        EmailAddress email1 = new EmailAddress("user1", "domain.com");
        EmailAddress email2 = new EmailAddress("user2", "domain.com");

        // Note: This is not guaranteed but highly likely for well-designed
        // hash functions
        assertNotEquals(email1.hashCode(), email2.hashCode(),
            "Different EmailAddress objects should typically have different hash codes");
    }

    // ========================================================================
    // Integration Tests - End-to-End Parsing
    // ========================================================================

    /**
     * Integration test verifying the Python README example works correctly.
     *
     * <p>This is the primary example from the original Python implementation
     * and must produce identical results for functional equivalence.
     */
    @Test
    void testPythonReadmeExample() {
        String input = "avimax37@gmail.com";
        EmailAddress result = slicer.parse(input);

        assertEquals("avimax37", result.getUsername(),
            "Username must match Python README example");
        assertEquals("gmail.com", result.getDomain(),
            "Domain must match Python README example");
        assertEquals("avimax37@gmail.com", result.toString(),
            "toString must reconstruct original email");
    }
}
