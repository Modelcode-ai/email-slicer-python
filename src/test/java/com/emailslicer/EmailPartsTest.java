package com.emailslicer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for EmailParts value object (Java record).
 * Tests basic record functionality including accessors, equality, and toString.
 */
class EmailPartsTest {

    // Accessor Tests

    @Test
    void username_returnsCorrectValue() {
        // Given
        EmailParts emailParts = new EmailParts("user", "example.com");

        // When
        String username = emailParts.username();

        // Then
        assertThat(username).isEqualTo("user");
    }

    @Test
    void domain_returnsCorrectValue() {
        // Given
        EmailParts emailParts = new EmailParts("user", "example.com");

        // When
        String domain = emailParts.domain();

        // Then
        assertThat(domain).isEqualTo("example.com");
    }

    @Test
    void accessors_withComplexValues_returnCorrectValues() {
        // Given
        EmailParts emailParts = new EmailParts(
                "user.name+tag",
                "mail.example.com"
        );

        // When & Then
        assertThat(emailParts.username()).isEqualTo("user.name+tag");
        assertThat(emailParts.domain()).isEqualTo("mail.example.com");
    }

    @Test
    void accessors_withSingleCharacterValues_returnCorrectValues() {
        // Given
        EmailParts emailParts = new EmailParts("a", "b.c");

        // When & Then
        assertThat(emailParts.username()).isEqualTo("a");
        assertThat(emailParts.domain()).isEqualTo("b.c");
    }

    // Equality Tests

    @Test
    void equals_withIdenticalValues_returnsTrue() {
        // Given
        EmailParts parts1 = new EmailParts("user", "example.com");
        EmailParts parts2 = new EmailParts("user", "example.com");

        // When & Then
        assertThat(parts1).isEqualTo(parts2);
        assertThat(parts2).isEqualTo(parts1);
    }

    @Test
    void equals_withSameInstance_returnsTrue() {
        // Given
        EmailParts parts = new EmailParts("user", "example.com");

        // When & Then
        assertThat(parts).isEqualTo(parts);
    }

    @Test
    void equals_withDifferentUsername_returnsFalse() {
        // Given
        EmailParts parts1 = new EmailParts("user1", "example.com");
        EmailParts parts2 = new EmailParts("user2", "example.com");

        // When & Then
        assertThat(parts1).isNotEqualTo(parts2);
    }

    @Test
    void equals_withDifferentDomain_returnsFalse() {
        // Given
        EmailParts parts1 = new EmailParts("user", "example.com");
        EmailParts parts2 = new EmailParts("user", "example.org");

        // When & Then
        assertThat(parts1).isNotEqualTo(parts2);
    }

    @Test
    void equals_withDifferentUsernameAndDomain_returnsFalse() {
        // Given
        EmailParts parts1 = new EmailParts("user1", "example.com");
        EmailParts parts2 = new EmailParts("user2", "example.org");

        // When & Then
        assertThat(parts1).isNotEqualTo(parts2);
    }

    @Test
    void equals_withNull_returnsFalse() {
        // Given
        EmailParts parts = new EmailParts("user", "example.com");

        // When & Then
        assertThat(parts).isNotEqualTo(null);
    }

    @Test
    void equals_withDifferentType_returnsFalse() {
        // Given
        EmailParts parts = new EmailParts("user", "example.com");
        String notEmailParts = "user@example.com";

        // When & Then
        assertThat(parts).isNotEqualTo(notEmailParts);
    }

    // HashCode Tests

    @Test
    void hashCode_withIdenticalValues_returnsSameHashCode() {
        // Given
        EmailParts parts1 = new EmailParts("user", "example.com");
        EmailParts parts2 = new EmailParts("user", "example.com");

        // When & Then
        assertThat(parts1.hashCode()).isEqualTo(parts2.hashCode());
    }

    @Test
    void hashCode_withDifferentValues_mayReturnDifferentHashCode() {
        // Given
        EmailParts parts1 = new EmailParts("user1", "example.com");
        EmailParts parts2 = new EmailParts("user2", "example.com");

        // When & Then
        // Note: Different objects may have the same hash code (collision),
        // but it's highly unlikely with these values
        assertThat(parts1.hashCode()).isNotEqualTo(parts2.hashCode());
    }

    @Test
    void hashCode_calledMultipleTimes_returnsConsistentValue() {
        // Given
        EmailParts parts = new EmailParts("user", "example.com");

        // When
        int hash1 = parts.hashCode();
        int hash2 = parts.hashCode();
        int hash3 = parts.hashCode();

        // Then
        assertThat(hash1).isEqualTo(hash2);
        assertThat(hash2).isEqualTo(hash3);
    }

    // ToString Tests

    @Test
    void toString_returnsNonNullString() {
        // Given
        EmailParts parts = new EmailParts("user", "example.com");

        // When
        String result = parts.toString();

        // Then
        assertThat(result).isNotNull();
    }

    @Test
    void toString_containsUsername() {
        // Given
        EmailParts parts = new EmailParts("user", "example.com");

        // When
        String result = parts.toString();

        // Then
        assertThat(result).contains("user");
    }

    @Test
    void toString_containsDomain() {
        // Given
        EmailParts parts = new EmailParts("user", "example.com");

        // When
        String result = parts.toString();

        // Then
        assertThat(result).contains("example.com");
    }

    @Test
    void toString_producesReadableOutput() {
        // Given
        EmailParts parts = new EmailParts("user", "example.com");

        // When
        String result = parts.toString();

        // Then - record toString typically follows format:
        // EmailParts[username=user, domain=example.com]
        assertThat(result)
                .contains("user")
                .contains("example.com")
                .isNotEmpty();
    }

    @Test
    void toString_withComplexValues_containsBothValues() {
        // Given
        EmailParts parts = new EmailParts(
                "user.name+tag",
                "mail.example.com"
        );

        // When
        String result = parts.toString();

        // Then
        assertThat(result)
                .contains("user.name+tag")
                .contains("mail.example.com");
    }

    // Immutability Tests (implicit with records, but documented for clarity)

    @Test
    void emailParts_isImmutable_cannotModifyAfterCreation() {
        // Given
        String username = "user";
        String domain = "example.com";
        EmailParts parts = new EmailParts(username, domain);

        // When - try to get references to fields
        String retrievedUsername = parts.username();
        String retrievedDomain = parts.domain();

        // Then - modifying the original strings doesn't affect the record
        // (strings are immutable in Java, but this documents the intent)
        assertThat(parts.username()).isEqualTo("user");
        assertThat(parts.domain()).isEqualTo("example.com");
    }

    // Construction Tests

    @Test
    void constructor_withValidValues_createsInstance() {
        // When
        EmailParts parts = new EmailParts("user", "example.com");

        // Then
        assertThat(parts).isNotNull();
    }

    @Test
    void constructor_withNullUsername_createsInstance() {
        // When - records don't prevent null by default
        EmailParts parts = new EmailParts(null, "example.com");

        // Then
        assertThat(parts).isNotNull();
        assertThat(parts.username()).isNull();
        assertThat(parts.domain()).isEqualTo("example.com");
    }

    @Test
    void constructor_withNullDomain_createsInstance() {
        // When - records don't prevent null by default
        EmailParts parts = new EmailParts("user", null);

        // Then
        assertThat(parts).isNotNull();
        assertThat(parts.username()).isEqualTo("user");
        assertThat(parts.domain()).isNull();
    }

    @Test
    void constructor_withEmptyStrings_createsInstance() {
        // When
        EmailParts parts = new EmailParts("", "");

        // Then
        assertThat(parts).isNotNull();
        assertThat(parts.username()).isEmpty();
        assertThat(parts.domain()).isEmpty();
    }
}
