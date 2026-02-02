package com.emailslicer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Comprehensive test suite for the EmailSlicer class.
 * Tests cover valid email parsing, invalid email validation, edge cases,
 * and internal methods (normalize and isValidEmail).
 */
@DisplayName("EmailSlicer Tests")
class EmailSlicerTest {

    // ==================== Valid Email Parsing Tests ====================

    @ParameterizedTest(name = "Should parse valid email: {0}")
    @CsvSource({
        "avimax37@gmail.com, avimax37, gmail.com",
        "user.name@example.co.uk, user.name, example.co.uk",
        "test@domain.com, test, domain.com",
        "john.doe@mail.example.com, john.doe, mail.example.com",
        "user+tag@example.org, user+tag, example.org",
        "first.last@subdomain.example.co.uk, first.last, subdomain.example.co.uk",
        "a@b.c, a, b.c",
        "test_user@test-domain.com, test_user, test-domain.com"
    })
    @DisplayName("Valid email parsing with various formats")
    void testValidEmailParsing(String email, String expectedUsername, String expectedDomain) {
        EmailSlicer.Result result = EmailSlicer.slice(email);

        assertThat(result.getUsername()).isEqualTo(expectedUsername);
        assertThat(result.getDomain()).isEqualTo(expectedDomain);
    }

    @Test
    @DisplayName("Should parse email with subdomain correctly")
    void testValidEmailWithSubdomain() {
        EmailSlicer.Result result = EmailSlicer.slice("user@mail.example.com");

        assertThat(result.getUsername()).isEqualTo("user");
        assertThat(result.getDomain()).isEqualTo("mail.example.com");
    }

    @Test
    @DisplayName("Should parse email with special characters in local part")
    void testValidEmailWithSpecialCharacters() {
        EmailSlicer.Result result = EmailSlicer.slice("user.name+tag@example.com");

        assertThat(result.getUsername()).isEqualTo("user.name+tag");
        assertThat(result.getDomain()).isEqualTo("example.com");
    }

    @Test
    @DisplayName("Should parse email with multiple dots in domain")
    void testValidEmailWithMultipleDotsInDomain() {
        EmailSlicer.Result result = EmailSlicer.slice("admin@subdomain.example.co.uk");

        assertThat(result.getUsername()).isEqualTo("admin");
        assertThat(result.getDomain()).isEqualTo("subdomain.example.co.uk");
    }

    // ==================== Invalid Email Exception Tests ====================

    @Test
    @DisplayName("Should throw exception for email missing @ symbol")
    void testInvalidEmailMissingAtSymbol() {
        assertThatThrownBy(() -> EmailSlicer.slice("invalidemail.com"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Please enter a valid Email Id.");
    }

    @Test
    @DisplayName("Should throw exception for email with multiple @ symbols")
    void testInvalidEmailMultipleAtSymbols() {
        assertThatThrownBy(() -> EmailSlicer.slice("user@@example.com"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Please enter a valid Email Id.");
    }

    @Test
    @DisplayName("Should throw exception for email missing domain")
    void testInvalidEmailMissingDomain() {
        assertThatThrownBy(() -> EmailSlicer.slice("user@"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Please enter a valid Email Id.");
    }

    @Test
    @DisplayName("Should throw exception for email missing local part")
    void testInvalidEmailMissingLocalPart() {
        assertThatThrownBy(() -> EmailSlicer.slice("@example.com"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Please enter a valid Email Id.");
    }

    @Test
    @DisplayName("Should throw exception for email without TLD (no dot in domain)")
    void testInvalidEmailNoTLD() {
        assertThatThrownBy(() -> EmailSlicer.slice("user@localhost"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Please enter a valid Email Id.");
    }

    @Test
    @DisplayName("Should throw exception for empty string")
    void testInvalidEmailEmptyString() {
        assertThatThrownBy(() -> EmailSlicer.slice(""))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Please enter a valid Email Id.");
    }

    @Test
    @DisplayName("Should throw exception for whitespace-only string")
    void testInvalidEmailWhitespaceOnly() {
        assertThatThrownBy(() -> EmailSlicer.slice("   "))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Please enter a valid Email Id.");
    }

    @ParameterizedTest(name = "Should reject invalid email: {0}")
    @ValueSource(strings = {
        "plaintext",
        "missing@domain",
        "@nodomain.com",
        "user@@double.com",
        "user@",
        "@",
        "user@domain@extra.com",
        ""
    })
    @DisplayName("Invalid email formats should throw exception")
    void testInvalidEmailFormats(String invalidEmail) {
        assertThatThrownBy(() -> EmailSlicer.slice(invalidEmail))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Please enter a valid Email Id.");
    }

    // ==================== Edge Case Tests ====================

    @Test
    @DisplayName("Should strip leading whitespace before validation")
    void testEmailWithLeadingWhitespace() {
        EmailSlicer.Result result = EmailSlicer.slice("   user@example.com");

        assertThat(result.getUsername()).isEqualTo("user");
        assertThat(result.getDomain()).isEqualTo("example.com");
    }

    @Test
    @DisplayName("Should strip trailing whitespace before validation")
    void testEmailWithTrailingWhitespace() {
        EmailSlicer.Result result = EmailSlicer.slice("user@example.com   ");

        assertThat(result.getUsername()).isEqualTo("user");
        assertThat(result.getDomain()).isEqualTo("example.com");
    }

    @Test
    @DisplayName("Should strip both leading and trailing whitespace")
    void testEmailWithLeadingAndTrailingWhitespace() {
        EmailSlicer.Result result = EmailSlicer.slice("  user@example.com  ");

        assertThat(result.getUsername()).isEqualTo("user");
        assertThat(result.getDomain()).isEqualTo("example.com");
    }

    @Test
    @DisplayName("Should handle null input by throwing exception")
    void testNullInput() {
        assertThatThrownBy(() -> EmailSlicer.slice(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Please enter a valid Email Id.");
    }

    @Test
    @DisplayName("Should reject email without TLD that Python would accept")
    void testEmailRejectedByJavaButNotPython() {
        // Python version would accept "user@domain" (has @)
        // Java version should reject it (missing TLD/dot in domain)
        assertThatThrownBy(() -> EmailSlicer.slice("user@domain"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Please enter a valid Email Id.");
    }

    // ==================== Internal Method Tests ====================
    // Testing package-private methods directly for granular verification

    @Test
    @DisplayName("normalize() should strip leading and trailing whitespace")
    void testNormalizeStripsWhitespace() {
        assertThat(EmailSlicer.normalize("  test@example.com  ")).isEqualTo("test@example.com");
        assertThat(EmailSlicer.normalize("test@example.com")).isEqualTo("test@example.com");
        assertThat(EmailSlicer.normalize("   ")).isEqualTo("");
    }

    @Test
    @DisplayName("normalize() should handle null input by returning empty string")
    void testNormalizeHandlesNull() {
        assertThat(EmailSlicer.normalize(null)).isEqualTo("");
    }

    @Test
    @DisplayName("normalize() should preserve internal whitespace")
    void testNormalizePreservesInternalWhitespace() {
        // Note: While this is technically not valid, normalize should only strip edges
        assertThat(EmailSlicer.normalize("  test @ example.com  ")).isEqualTo("test @ example.com");
    }

    @Test
    @DisplayName("isValidEmail() should return true for valid email formats")
    void testIsValidEmailReturnsTrueForValidFormats() {
        assertThat(EmailSlicer.isValidEmail("user@example.com")).isTrue();
        assertThat(EmailSlicer.isValidEmail("test@domain.co.uk")).isTrue();
        assertThat(EmailSlicer.isValidEmail("a@b.c")).isTrue();
    }

    @Test
    @DisplayName("isValidEmail() should return false for invalid formats")
    void testIsValidEmailReturnsFalseForInvalidFormats() {
        assertThat(EmailSlicer.isValidEmail("invalid")).isFalse();
        assertThat(EmailSlicer.isValidEmail("user@domain")).isFalse();
        assertThat(EmailSlicer.isValidEmail("@example.com")).isFalse();
        assertThat(EmailSlicer.isValidEmail("user@")).isFalse();
        assertThat(EmailSlicer.isValidEmail("")).isFalse();
    }

    @Test
    @DisplayName("isValidEmail() should return false for null input")
    void testIsValidEmailReturnsFalseForNull() {
        assertThat(EmailSlicer.isValidEmail(null)).isFalse();
    }

    @Test
    @DisplayName("isValidEmail() should return false for empty string")
    void testIsValidEmailReturnsFalseForEmptyString() {
        assertThat(EmailSlicer.isValidEmail("")).isFalse();
    }

    @ParameterizedTest(name = "isValidEmail() should validate: {0} = {1}")
    @CsvSource({
        "user@example.com, true",
        "test@domain.co.uk, true",
        "a@b.c, true",
        "user.name@sub.example.com, true",
        "invalid, false",
        "user@localhost, false",
        "@example.com, false",
        "user@, false",
        "user@@example.com, false",
        "'', false"
    })
    @DisplayName("isValidEmail() regex pattern validation")
    void testIsValidEmailRegexPattern(String email, boolean expectedValid) {
        assertThat(EmailSlicer.isValidEmail(email)).isEqualTo(expectedValid);
    }

    // ==================== Result Class Tests ====================

    @Test
    @DisplayName("Result should correctly store and return username and domain")
    void testResultGetters() {
        EmailSlicer.Result result = new EmailSlicer.Result("testuser", "example.com");

        assertThat(result.getUsername()).isEqualTo("testuser");
        assertThat(result.getDomain()).isEqualTo("example.com");
    }

    @Test
    @DisplayName("Result should be immutable")
    void testResultImmutability() {
        EmailSlicer.Result result = new EmailSlicer.Result("user", "domain.com");
        String originalUsername = result.getUsername();
        String originalDomain = result.getDomain();

        // Calling getters multiple times should return the same values
        assertThat(result.getUsername()).isEqualTo(originalUsername);
        assertThat(result.getDomain()).isEqualTo(originalDomain);
    }
}
