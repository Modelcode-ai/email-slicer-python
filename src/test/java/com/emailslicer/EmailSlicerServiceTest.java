package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for EmailSlicerService.
 * Covers all validation rules and edge cases specified in the modernization spec.
 */
class EmailSlicerServiceTest {
  private EmailSlicerService service;

  @BeforeEach
  void setUp() {
    service = new EmailSlicerService();
  }

  // ========== Valid Email Parsing Tests ==========

  @Test
  @DisplayName("Should extract username and domain from standard email")
  void shouldExtractUsernameAndDomain() {
    EmailComponents result = service.parse("avimax37@gmail.com");

    assertEquals("avimax37", result.username());
    assertEquals("gmail.com", result.domain());
  }

  @Test
  @DisplayName("Should trim leading whitespace before parsing")
  void shouldTrimLeadingWhitespace() {
    EmailComponents result = service.parse("  user@example.com");

    assertEquals("user", result.username());
    assertEquals("example.com", result.domain());
  }

  @Test
  @DisplayName("Should trim trailing whitespace before parsing")
  void shouldTrimTrailingWhitespace() {
    EmailComponents result = service.parse("user@example.com  ");

    assertEquals("user", result.username());
    assertEquals("example.com", result.domain());
  }

  @Test
  @DisplayName("Should trim both leading and trailing whitespace")
  void shouldTrimBothLeadingAndTrailingWhitespace() {
    EmailComponents result = service.parse("  user@example.com  ");

    assertEquals("user", result.username());
    assertEquals("example.com", result.domain());
  }

  @Test
  @DisplayName("Should accept email with dots in username")
  void shouldAcceptEmailWithDotsInUsername() {
    EmailComponents result = service.parse("user.name@example.com");

    assertEquals("user.name", result.username());
    assertEquals("example.com", result.domain());
  }

  @Test
  @DisplayName("Should accept email with plus sign in username")
  void shouldAcceptEmailWithPlusSign() {
    EmailComponents result = service.parse("user+tag@example.com");

    assertEquals("user+tag", result.username());
    assertEquals("example.com", result.domain());
  }

  @Test
  @DisplayName("Should accept email with hyphens")
  void shouldAcceptEmailWithHyphens() {
    EmailComponents result = service.parse("user-name@example-domain.com");

    assertEquals("user-name", result.username());
    assertEquals("example-domain.com", result.domain());
  }

  @Test
  @DisplayName("Should accept email with multiple dots in domain")
  void shouldAcceptEmailWithMultipleDotsInDomain() {
    EmailComponents result = service.parse("user@example.co.uk");

    assertEquals("user", result.username());
    assertEquals("example.co.uk", result.domain());
  }

  @Test
  @DisplayName("Should accept complex email with special characters")
  void shouldAcceptComplexEmailWithSpecialCharacters() {
    EmailComponents result = service.parse("user.name+tag@example.co.uk");

    assertEquals("user.name+tag", result.username());
    assertEquals("example.co.uk", result.domain());
  }

  // ========== Invalid Structure Tests - Missing @ ==========

  @Test
  @DisplayName("Should fail when no @ symbol is present")
  void shouldFailWhenNoAtSymbolPresent() {
    InvalidEmailException exception = assertThrows(
        InvalidEmailException.class,
        () -> service.parse("userexample.com")
    );

    assertTrue(exception.getMessage().contains("exactly one '@' symbol"));
  }

  @Test
  @DisplayName("Should fail when email is just a username without @")
  void shouldFailWhenEmailIsJustUsername() {
    InvalidEmailException exception = assertThrows(
        InvalidEmailException.class,
        () -> service.parse("username")
    );

    assertTrue(exception.getMessage().contains("exactly one '@' symbol"));
  }

  // ========== Invalid Structure Tests - Multiple @ ==========

  @Test
  @DisplayName("Should fail when multiple @ symbols are present (consecutive)")
  void shouldFailWhenMultipleAtSymbolsConsecutive() {
    InvalidEmailException exception = assertThrows(
        InvalidEmailException.class,
        () -> service.parse("user@@example.com")
    );

    assertTrue(exception.getMessage().contains("exactly one '@' symbol"));
  }

  @Test
  @DisplayName("Should fail when multiple @ symbols are present (separated)")
  void shouldFailWhenMultipleAtSymbolsSeparated() {
    InvalidEmailException exception = assertThrows(
        InvalidEmailException.class,
        () -> service.parse("user@sub@domain.com")
    );

    assertTrue(exception.getMessage().contains("exactly one '@' symbol"));
  }

  @Test
  @DisplayName("Should fail when three @ symbols are present")
  void shouldFailWhenThreeAtSymbolsPresent() {
    InvalidEmailException exception = assertThrows(
        InvalidEmailException.class,
        () -> service.parse("user@sub@domain@com")
    );

    assertTrue(exception.getMessage().contains("exactly one '@' symbol"));
  }

  // ========== Empty Parts Validation Tests ==========

  @Test
  @DisplayName("Should fail when username is empty")
  void shouldFailWhenUsernameIsEmpty() {
    InvalidEmailException exception = assertThrows(
        InvalidEmailException.class,
        () -> service.parse("@example.com")
    );

    assertTrue(exception.getMessage().contains("Username"));
    assertTrue(exception.getMessage().contains("must not be empty"));
  }

  @Test
  @DisplayName("Should fail when domain is empty")
  void shouldFailWhenDomainIsEmpty() {
    InvalidEmailException exception = assertThrows(
        InvalidEmailException.class,
        () -> service.parse("user@")
    );

    assertTrue(exception.getMessage().contains("Domain"));
    assertTrue(exception.getMessage().contains("must not be empty"));
  }

  @Test
  @DisplayName("Should fail when both username and domain are empty")
  void shouldFailWhenBothPartsAreEmpty() {
    InvalidEmailException exception = assertThrows(
        InvalidEmailException.class,
        () -> service.parse("@")
    );

    // Should fail on username check first
    assertTrue(exception.getMessage().contains("Username"));
    assertTrue(exception.getMessage().contains("must not be empty"));
  }

  // ========== Edge Case Tests ==========

  @Test
  @DisplayName("Should fail when email is null")
  void shouldFailWhenEmailIsNull() {
    InvalidEmailException exception = assertThrows(
        InvalidEmailException.class,
        () -> service.parse(null)
    );

    assertTrue(exception.getMessage().contains("must not be null"));
  }

  @Test
  @DisplayName("Should fail when email is empty string")
  void shouldFailWhenEmailIsEmptyString() {
    InvalidEmailException exception = assertThrows(
        InvalidEmailException.class,
        () -> service.parse("")
    );

    assertTrue(exception.getMessage().contains("must not be empty"));
  }

  @Test
  @DisplayName("Should fail when email is whitespace only")
  void shouldFailWhenEmailIsWhitespaceOnly() {
    InvalidEmailException exception = assertThrows(
        InvalidEmailException.class,
        () -> service.parse("   ")
    );

    assertTrue(exception.getMessage().contains("must not be empty"));
  }

  @ParameterizedTest
  @ValueSource(strings = {" ", "  ", "   ", "\t", "\n", "\r\n", " \t \n "})
  @DisplayName("Should fail for various whitespace-only inputs")
  void shouldFailForVariousWhitespaceOnlyInputs(String whitespace) {
    InvalidEmailException exception = assertThrows(
        InvalidEmailException.class,
        () -> service.parse(whitespace)
    );

    assertTrue(exception.getMessage().contains("must not be empty"));
  }

  // ========== Additional Edge Cases ==========

  @Test
  @DisplayName("Should handle single character username and domain")
  void shouldHandleSingleCharacterUsernameAndDomain() {
    EmailComponents result = service.parse("a@b");

    assertEquals("a", result.username());
    assertEquals("b", result.domain());
  }

  @Test
  @DisplayName("Should handle very long email addresses")
  void shouldHandleVeryLongEmailAddresses() {
    String longUsername = "a".repeat(100);
    String longDomain = "b".repeat(100) + ".com";
    String email = longUsername + "@" + longDomain;

    EmailComponents result = service.parse(email);

    assertEquals(longUsername, result.username());
    assertEquals(longDomain, result.domain());
  }

  @Test
  @DisplayName("Should handle email with numbers")
  void shouldHandleEmailWithNumbers() {
    EmailComponents result = service.parse("user123@example456.com");

    assertEquals("user123", result.username());
    assertEquals("example456.com", result.domain());
  }

  @Test
  @DisplayName("Should handle email with underscores")
  void shouldHandleEmailWithUnderscores() {
    EmailComponents result = service.parse("user_name@example_domain.com");

    assertEquals("user_name", result.username());
    assertEquals("example_domain.com", result.domain());
  }
}
