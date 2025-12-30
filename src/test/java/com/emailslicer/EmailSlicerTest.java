package com.emailslicer;

import com.emailslicer.validator.EmailValidator;
import com.emailslicer.validator.InvalidEmailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for EmailSlicer service class.
 * Tests the email slicing logic and integration with EmailValidator.
 */
class EmailSlicerTest {

    private EmailSlicer emailSlicer;

    @BeforeEach
    void setUp() {
        // Use a real EmailValidator instance for integration testing
        EmailValidator validator = new EmailValidator();
        emailSlicer = new EmailSlicer(validator);
    }

    // Successful Slicing Test Cases

    @Test
    void slice_withStandardEmail_returnsCorrectParts() {
        // Given
        String email = "user@example.com";

        // When
        EmailParts result = emailSlicer.slice(email);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo("user");
        assertThat(result.domain()).isEqualTo("example.com");
    }

    @Test
    void slice_withEmailWithSubdomain_returnsCorrectParts() {
        // Given
        String email = "user@mail.example.com";

        // When
        EmailParts result = emailSlicer.slice(email);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo("user");
        assertThat(result.domain()).isEqualTo("mail.example.com");
    }

    @Test
    void slice_withComplexUsername_returnsCorrectParts() {
        // Given
        String email = "user.name+tag@example.com";

        // When
        EmailParts result = emailSlicer.slice(email);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo("user.name+tag");
        assertThat(result.domain()).isEqualTo("example.com");
    }

    @Test
    void slice_withSingleCharacterUsername_returnsCorrectParts() {
        // Given
        String email = "a@example.com";

        // When
        EmailParts result = emailSlicer.slice(email);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo("a");
        assertThat(result.domain()).isEqualTo("example.com");
    }

    @Test
    void slice_withSingleCharacterDomain_returnsCorrectParts() {
        // Given
        String email = "user@e.com";

        // When
        EmailParts result = emailSlicer.slice(email);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo("user");
        assertThat(result.domain()).isEqualTo("e.com");
    }

    @Test
    void slice_withNumericUsername_returnsCorrectParts() {
        // Given
        String email = "12345@example.com";

        // When
        EmailParts result = emailSlicer.slice(email);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo("12345");
        assertThat(result.domain()).isEqualTo("example.com");
    }

    @Test
    void slice_withLongUsername_returnsCorrectParts() {
        // Given
        String email = "very.long.username.with.many.parts@example.com";

        // When
        EmailParts result = emailSlicer.slice(email);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo("very.long.username.with.many.parts");
        assertThat(result.domain()).isEqualTo("example.com");
    }

    @Test
    void slice_withLongDomain_returnsCorrectParts() {
        // Given
        String email = "user@very.long.subdomain.example.com";

        // When
        EmailParts result = emailSlicer.slice(email);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo("user");
        assertThat(result.domain()).isEqualTo("very.long.subdomain.example.com");
    }

    @Test
    void slice_withLeadingWhitespace_trimsAndReturnsCorrectParts() {
        // Given - validator should trim this
        String email = "  user@example.com";

        // When
        EmailParts result = emailSlicer.slice(email);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo("user");
        assertThat(result.domain()).isEqualTo("example.com");
    }

    @Test
    void slice_withTrailingWhitespace_trimsAndReturnsCorrectParts() {
        // Given - validator should trim this
        String email = "user@example.com  ";

        // When
        EmailParts result = emailSlicer.slice(email);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo("user");
        assertThat(result.domain()).isEqualTo("example.com");
    }

    @Test
    void slice_withLeadingAndTrailingWhitespace_trimsAndReturnsCorrectParts() {
        // Given - validator should trim this
        String email = "  user@example.com  ";

        // When
        EmailParts result = emailSlicer.slice(email);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo("user");
        assertThat(result.domain()).isEqualTo("example.com");
    }

    // Exception Propagation Test Cases

    @Test
    void slice_withNullEmail_throwsInvalidEmailException() {
        assertThatThrownBy(() -> emailSlicer.slice(null))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must not be null.");
    }

    @Test
    void slice_withEmptyEmail_throwsInvalidEmailException() {
        assertThatThrownBy(() -> emailSlicer.slice(""))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must not be empty.");
    }

    @Test
    void slice_withNoAtSymbol_throwsInvalidEmailException() {
        assertThatThrownBy(() -> emailSlicer.slice("userexample.com"))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must contain exactly one '@' symbol.");
    }

    @Test
    void slice_withMultipleAtSymbols_throwsInvalidEmailException() {
        assertThatThrownBy(() -> emailSlicer.slice("user@@example.com"))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must contain exactly one '@' symbol.");
    }

    @Test
    void slice_withEmptyUsername_throwsInvalidEmailException() {
        assertThatThrownBy(() -> emailSlicer.slice("@example.com"))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must have a non-empty username.");
    }

    @Test
    void slice_withEmptyDomain_throwsInvalidEmailException() {
        assertThatThrownBy(() -> emailSlicer.slice("user@"))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must have a non-empty domain.");
    }

    @Test
    void slice_withWhitespaceOnly_throwsInvalidEmailException() {
        assertThatThrownBy(() -> emailSlicer.slice("   "))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("Email address must not be empty.");
    }

    // EmailParts Object Verification

    @Test
    void slice_returnsNonNullEmailParts() {
        // Given
        String email = "user@example.com";

        // When
        EmailParts result = emailSlicer.slice(email);

        // Then
        assertThat(result).isNotNull();
    }

    @Test
    void slice_returnsEmailPartsWithBothFields() {
        // Given
        String email = "user@example.com";

        // When
        EmailParts result = emailSlicer.slice(email);

        // Then
        assertThat(result.username()).isNotNull();
        assertThat(result.domain()).isNotNull();
    }

    @Test
    void slice_multipleCalls_returnsConsistentResults() {
        // Given
        String email = "user@example.com";

        // When
        EmailParts result1 = emailSlicer.slice(email);
        EmailParts result2 = emailSlicer.slice(email);

        // Then - should get identical results
        assertThat(result1.username()).isEqualTo(result2.username());
        assertThat(result1.domain()).isEqualTo(result2.domain());
        assertThat(result1).isEqualTo(result2);
    }

    // Edge Cases

    @Test
    void slice_withMinimalEmail_returnsCorrectParts() {
        // Given - minimal valid email: one char username, one char domain
        String email = "a@b.c";

        // When
        EmailParts result = emailSlicer.slice(email);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo("a");
        assertThat(result.domain()).isEqualTo("b.c");
    }

    @Test
    void slice_withUnderscoreInUsername_returnsCorrectParts() {
        // Given
        String email = "user_name@example.com";

        // When
        EmailParts result = emailSlicer.slice(email);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo("user_name");
        assertThat(result.domain()).isEqualTo("example.com");
    }

    @Test
    void slice_withHyphenInDomain_returnsCorrectParts() {
        // Given
        String email = "user@mail-server.example.com";

        // When
        EmailParts result = emailSlicer.slice(email);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo("user");
        assertThat(result.domain()).isEqualTo("mail-server.example.com");
    }
}
