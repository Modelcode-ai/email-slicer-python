package emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link EmailSlicer} covering valid emails, whitespace handling,
 * missing {@code @}, edge cases, and null input.
 */
class EmailSlicerTest {

    // ---- Valid email tests ----

    @Test
    void parse_validEmail_returnsUsernameAndDomain() {
        EmailParts result = EmailSlicer.parse("avimax37@gmail.com");

        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    void parse_simpleValidEmail_returnsUsernameAndDomain() {
        EmailParts result = EmailSlicer.parse("user@example.com");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    void parse_emailWithTagsAndSubdomain_returnsUsernameAndDomain() {
        EmailParts result = EmailSlicer.parse("user.name+tag@sub.domain.org");

        assertEquals("user.name+tag", result.username());
        assertEquals("sub.domain.org", result.domain());
    }

    // ---- Whitespace handling tests ----

    @Test
    void parse_emailWithLeadingAndTrailingSpaces_trimmedAndParsed() {
        EmailParts result = EmailSlicer.parse("  user@example.com  ");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    void parse_whitespaceOnlyInput_throwsInvalidEmailException() {
        assertThrows(InvalidEmailException.class, () ->
                EmailSlicer.parse("  \t \n  "));
    }

    // ---- Missing '@' tests ----

    @Test
    void parse_noAtSign_throwsInvalidEmailException() {
        InvalidEmailException ex = assertThrows(InvalidEmailException.class, () ->
                EmailSlicer.parse("invalid.email.com"));

        assertNotNull(ex.getMessage());
    }

    @Test
    void parse_emptyString_throwsInvalidEmailException() {
        assertThrows(InvalidEmailException.class, () ->
                EmailSlicer.parse(""));
    }

    @Test
    void parse_nullInput_throwsInvalidEmailException() {
        InvalidEmailException ex = assertThrows(InvalidEmailException.class, () ->
                EmailSlicer.parse(null));

        assertEquals("Email must not be null", ex.getMessage());
    }

    // ---- Edge cases with '@' position ----

    @Test
    void parse_atSignAtStart_throwsInvalidEmailException() {
        assertThrows(InvalidEmailException.class, () ->
                EmailSlicer.parse("@example.com"));
    }

    @Test
    void parse_atSignAtEnd_throwsInvalidEmailException() {
        assertThrows(InvalidEmailException.class, () ->
                EmailSlicer.parse("user@"));
    }

    @Test
    void parse_multipleAtSigns_usesFirstAtSign() {
        EmailParts result = EmailSlicer.parse("user@sub@example.com");

        assertEquals("user", result.username());
        assertEquals("sub@example.com", result.domain());
    }
}
