package emailslicer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link EmailSlicer#sliceEmail(String)}.
 * <p>
 * Covers valid emails, edge cases (empty username/domain, multiple '@' signs),
 * invalid inputs, whitespace handling, and null input.
 */
class EmailSlicerTest {

    @Test
    void validSimpleEmail() {
        EmailSliceResult result = EmailSlicer.sliceEmail("user@domain.com");
        assertTrue(result.isValid());
        assertEquals("user", result.getUsername());
        assertEquals("domain.com", result.getDomain());
    }

    @Test
    void multipleAtSignsUsesFirst() {
        EmailSliceResult result = EmailSlicer.sliceEmail("user@name@domain.com");
        assertTrue(result.isValid());
        assertEquals("user", result.getUsername());
        assertEquals("name@domain.com", result.getDomain());
    }

    @Test
    void noAtSignIsInvalid() {
        EmailSliceResult result = EmailSlicer.sliceEmail("user.domain.com");
        assertFalse(result.isValid());
    }

    @Test
    void leadingAndTrailingWhitespaceIsTrimmed() {
        // sliceEmail expects pre-trimmed input; main() handles trimming.
        // Here we verify that trimmed input parses correctly.
        String trimmed = "  user@domain.com  ".trim();
        EmailSliceResult result = EmailSlicer.sliceEmail(trimmed);
        assertTrue(result.isValid());
        assertEquals("user", result.getUsername());
        assertEquals("domain.com", result.getDomain());
    }

    @Test
    void emptyUsernameIsValid() {
        EmailSliceResult result = EmailSlicer.sliceEmail("@domain.com");
        assertTrue(result.isValid());
        assertEquals("", result.getUsername());
        assertEquals("domain.com", result.getDomain());
    }

    @Test
    void emptyDomainIsValid() {
        EmailSliceResult result = EmailSlicer.sliceEmail("user@");
        assertTrue(result.isValid());
        assertEquals("user", result.getUsername());
        assertEquals("", result.getDomain());
    }

    @Test
    void nullInputIsInvalid() {
        EmailSliceResult result = EmailSlicer.sliceEmail(null);
        assertFalse(result.isValid());
    }

    @Test
    void emptyStringIsInvalid() {
        EmailSliceResult result = EmailSlicer.sliceEmail("");
        assertFalse(result.isValid());
    }

    @Test
    void onlyAtSignIsValid() {
        EmailSliceResult result = EmailSlicer.sliceEmail("@");
        assertTrue(result.isValid());
        assertEquals("", result.getUsername());
        assertEquals("", result.getDomain());
    }

    @Test
    void invalidResultFieldsAreNull() {
        EmailSliceResult result = EmailSlicer.sliceEmail("noemail");
        assertFalse(result.isValid());
        assertNull(result.getUsername());
        assertNull(result.getDomain());
    }
}
