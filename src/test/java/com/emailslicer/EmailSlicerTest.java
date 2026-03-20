package com.emailslicer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer}.
 *
 * <p>Tests cover valid email splitting and basic invalid inputs.
 * The current milestone validates splitting on the first '@',
 * matching the Python original's behavior.</p>
 */
class EmailSlicerTest {

    @ParameterizedTest(name = "slice(\"{0}\") -> username={1}, domain={2}")
    @DisplayName("Should return username and domain for valid emails")
    @CsvSource({
            "avimax37@gmail.com,       avimax37,       gmail.com",
            "first.last@example.org,   first.last,     example.org",
            "user@mail.example.co.uk,  user,           mail.example.co.uk",
    })
    void shouldReturnUsernameAndDomain_whenEmailIsValid(String email,
                                                         String expectedUsername,
                                                         String expectedDomain) {
        Optional<EmailSlicerResult> result = EmailSlicer.slice(email);

        assertTrue(result.isPresent(), "Expected a result for: " + email);
        assertEquals(expectedUsername, result.get().username());
        assertEquals(expectedDomain, result.get().domain());
    }

    @Test
    @DisplayName("Should strip whitespace before slicing")
    void shouldStripWhitespace_whenEmailHasSurroundingSpaces() {
        Optional<EmailSlicerResult> result = EmailSlicer.slice("  user@test.com  ");

        assertTrue(result.isPresent());
        assertEquals("user", result.get().username());
        assertEquals("test.com", result.get().domain());
    }

    @Test
    @DisplayName("Should split on the first '@' when multiple '@' signs are present")
    void shouldSplitOnFirstAt_whenMultipleAtSigns() {
        // Matches Python behavior: index("@") returns the first occurrence
        Optional<EmailSlicerResult> result = EmailSlicer.slice("a@b@c.com");

        assertTrue(result.isPresent());
        assertEquals("a", result.get().username());
        assertEquals("b@c.com", result.get().domain());
    }

    @ParameterizedTest(name = "slice(\"{0}\") -> empty")
    @DisplayName("Should return empty for emails missing '@'")
    @ValueSource(strings = {"invalidemail.com", "justtext"})
    void shouldReturnEmpty_whenNoAtSign(String email) {
        Optional<EmailSlicerResult> result = EmailSlicer.slice(email);

        assertTrue(result.isEmpty(), "Expected empty for: " + email);
    }

    @ParameterizedTest(name = "slice({0}) -> empty")
    @DisplayName("Should return empty for null and empty inputs")
    @NullAndEmptySource
    void shouldReturnEmpty_whenNullOrEmpty(String email) {
        Optional<EmailSlicerResult> result = EmailSlicer.slice(email);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return empty for blank/whitespace-only input")
    void shouldReturnEmpty_whenBlankInput() {
        Optional<EmailSlicerResult> result = EmailSlicer.slice("   ");

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should handle '@' at the start of input")
    void shouldHandleAtAtStart() {
        Optional<EmailSlicerResult> result = EmailSlicer.slice("@domain.com");

        assertTrue(result.isPresent());
        assertEquals("", result.get().username());
        assertEquals("domain.com", result.get().domain());
    }

    @Test
    @DisplayName("Should handle '@' at the end of input")
    void shouldHandleAtAtEnd() {
        Optional<EmailSlicerResult> result = EmailSlicer.slice("user@");

        assertTrue(result.isPresent());
        assertEquals("user", result.get().username());
        assertEquals("", result.get().domain());
    }

    @Test
    @DisplayName("Should handle only '@' character")
    void shouldHandleOnlyAtSign() {
        // Python behavior: "@" contains '@', so it splits into empty username and empty domain
        Optional<EmailSlicerResult> result = EmailSlicer.slice("@");

        assertTrue(result.isPresent());
        assertEquals("", result.get().username());
        assertEquals("", result.get().domain());
    }
}
