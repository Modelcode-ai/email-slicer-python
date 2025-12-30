package com.emailslicer.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Comprehensive unit tests for EmailValidator.
 * Tests both valid email scenarios and various invalid input cases.
 */
class EmailValidatorTest {

    private EmailValidator validator;

    @BeforeEach
    void setUp() {
        validator = new EmailValidator();
    }

    // Valid Email Test Cases

    @Test
    void validate_withStandardEmail_returnsNormalizedEmail() {
        // Given
        String email = "user@example.com";

        // When
        String result = validator.validate(email);

        // Then
        assertThat(result).isEqualTo("user@example.com");
    }

    @Test
    void validate_withLeadingWhitespace_returnsTrimmedEmail() {
        // Given
        String email = "  user@example.com";

        // When
        String result = validator.validate(email);

        // Then
        assertThat(result).isEqualTo("user@example.com");
    }

    @Test
    void validate_withTrailingWhitespace_returnsTrimmedEmail() {
        // Given
        String email = "user@example.com  ";

        // When
        String result = validator.validate(email);

        // Then
        assertThat(result).isEqualTo("user@example.com");
    }

    @Test
    void validate_withLeadingAndTrailingWhitespace_returnsTrimmedEmail() {
        // Given
        String email = "  user@example.com  ";

        // When
        String result = validator.validate(email);

        // Then
        assertThat(result).isEqualTo("user@example.com");
    }

    @Test
    void validate_withSubdomain_returnsNormalizedEmail() {
        // Given
        String email = "user@mail.example.com";

        // When
        String result = validator.validate(email);

        // Then
        assertThat(result).isEqualTo("user@mail.example.com");
    }

    @Test
    void validate_withComplexUsername_returnsNormalizedEmail() {
        // Given
        String email = "user.name+tag@example.com";

        // When
        String result = validator.validate(email);

        // Then
        assertThat(result).isEqualTo("user.name+tag@example.com");
    }

    @Test
    void validate_withSingleCharacterUsername_returnsNormalizedEmail() {
        // Given
        String email = "a@example.com";

        // When
        String result = validator.validate(email);

        // Then
        assertThat(result).isEqualTo("a@example.com");
    }

    @Test
    void validate_withSingleCharacterDomain_returnsNormalizedEmail() {
        // Given
        String email = "user@e.com";

        // When
        String result = validator.validate(email);

        // Then
        assertThat(result).isEqualTo("user@e.com");
    }

    // Invalid Email Test Cases - Null and Empty

    @Test
    void validate_withNullEmail_throwsInvalidEmailException() {
        assertThatThrownBy(() -> validator.validate(null))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must not be null.");
    }

    @Test
    void validate_withEmptyString_throwsInvalidEmailException() {
        assertThatThrownBy(() -> validator.validate(""))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must not be empty.");
    }

    @Test
    void validate_withWhitespaceOnly_throwsInvalidEmailException() {
        assertThatThrownBy(() -> validator.validate("   "))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must not be empty.");
    }

    @Test
    void validate_withTabsOnly_throwsInvalidEmailException() {
        assertThatThrownBy(() -> validator.validate("\t\t"))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must not be empty.");
    }

    // Invalid Email Test Cases - Missing '@'

    @Test
    void validate_withNoAtSymbol_throwsInvalidEmailException() {
        assertThatThrownBy(() -> validator.validate("userexample.com"))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must contain exactly one '@' symbol.");
    }

    @Test
    void validate_withNoAtSymbolButHasDot_throwsInvalidEmailException() {
        assertThatThrownBy(() -> validator.validate("user.example.com"))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must contain exactly one '@' symbol.");
    }

    // Invalid Email Test Cases - Multiple '@'

    @Test
    void validate_withTwoAtSymbols_throwsInvalidEmailException() {
        assertThatThrownBy(() -> validator.validate("user@@example.com"))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must contain exactly one '@' symbol.");
    }

    @Test
    void validate_withMultipleAtSymbols_throwsInvalidEmailException() {
        assertThatThrownBy(() -> validator.validate("user@domain@com"))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must contain exactly one '@' symbol.");
    }

    @Test
    void validate_withThreeAtSymbols_throwsInvalidEmailException() {
        assertThatThrownBy(() -> validator.validate("user@domain@com@net"))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must contain exactly one '@' symbol.");
    }

    // Invalid Email Test Cases - Empty Username

    @Test
    void validate_withEmptyUsername_throwsInvalidEmailException() {
        assertThatThrownBy(() -> validator.validate("@example.com"))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must have a non-empty username.");
    }

    @Test
    void validate_withWhitespaceBeforeAt_throwsInvalidEmailException() {
        // After trimming, there's no leading whitespace, but if the username
        // itself is whitespace before the @, it would fail
        assertThatThrownBy(() -> validator.validate("  @example.com"))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must have a non-empty username.");
    }

    // Invalid Email Test Cases - Empty Domain

    @Test
    void validate_withEmptyDomain_throwsInvalidEmailException() {
        assertThatThrownBy(() -> validator.validate("user@"))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must have a non-empty domain.");
    }

    @Test
    void validate_withWhitespaceAfterAt_throwsInvalidEmailException() {
        assertThatThrownBy(() -> validator.validate("user@  "))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must have a non-empty domain.");
    }

    @Test
    void validate_withOnlyAtSymbol_throwsInvalidEmailException() {
        // This is a special case: just '@' with no username or domain
        assertThatThrownBy(() -> validator.validate("@"))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must have a non-empty username.");
    }

    // Parameterized Test for Multiple Invalid Formats

    @ParameterizedTest
    @ValueSource(strings = {
            "plaintext",
            "missing.at.symbol",
            "@",
            "user@",
            "@domain.com",
            "user@@domain.com",
            "user@domain@com",
            "",
            "   "
    })
    void validate_withInvalidFormats_throwsInvalidEmailException(String invalidEmail) {
        assertThatThrownBy(() -> validator.validate(invalidEmail))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessageContaining("Email address");
    }

    // Exception Message Quality Tests

    @Test
    void validate_nullEmail_providesSpecificErrorMessage() {
        try {
            validator.validate(null);
        } catch (InvalidEmailException e) {
            assertThat(e.getMessage())
                    .isNotEmpty()
                    .contains("null");
        }
    }

    @Test
    void validate_emptyEmail_providesSpecificErrorMessage() {
        try {
            validator.validate("");
        } catch (InvalidEmailException e) {
            assertThat(e.getMessage())
                    .isNotEmpty()
                    .contains("empty");
        }
    }

    @Test
    void validate_noAtSymbol_providesSpecificErrorMessage() {
        try {
            validator.validate("userexample.com");
        } catch (InvalidEmailException e) {
            assertThat(e.getMessage())
                    .isNotEmpty()
                    .contains("@");
        }
    }

    @Test
    void validate_multipleAtSymbols_providesSpecificErrorMessage() {
        try {
            validator.validate("user@@example.com");
        } catch (InvalidEmailException e) {
            assertThat(e.getMessage())
                    .isNotEmpty()
                    .contains("@")
                    .containsAnyOf("exactly one", "one");
        }
    }

    @Test
    void validate_emptyUsername_providesSpecificErrorMessage() {
        try {
            validator.validate("@example.com");
        } catch (InvalidEmailException e) {
            assertThat(e.getMessage())
                    .isNotEmpty()
                    .contains("username");
        }
    }

    @Test
    void validate_emptyDomain_providesSpecificErrorMessage() {
        try {
            validator.validate("user@");
        } catch (InvalidEmailException e) {
            assertThat(e.getMessage())
                    .isNotEmpty()
                    .contains("domain");
        }
    }
}
