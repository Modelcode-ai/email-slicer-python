package com.emailslicer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link EmailParser}.
 *
 * <p>Verifies that email addresses are correctly split into username
 * and domain components, matching the behavior of the original Python
 * implementation's string slicing.
 */
class EmailParserTest {

    @ParameterizedTest(name = "parse \"{0}\" -> username=\"{1}\", domain=\"{2}\"")
    @DisplayName("Should correctly parse email into username and domain")
    @CsvSource({
            "avimax37@gmail.com,        avimax37,       gmail.com",
            "user@mail.example.com,     user,           mail.example.com",
            "john.doe123@company.co.uk, john.doe123,    company.co.uk",
            "a@b,                       a,              b",
            "test.user+tag@example.org, test.user+tag,  example.org",
            "x@y,                       x,              y",
            "hello@world,               hello,          world"
    })
    void shouldParseEmailCorrectly(String email, String expectedUsername, String expectedDomain) {
        EmailParser.EmailParts parts = EmailParser.parse(email);

        assertEquals(expectedUsername, parts.getUsername(),
                "Username mismatch for: " + email);
        assertEquals(expectedDomain, parts.getDomain(),
                "Domain mismatch for: " + email);
    }

    @ParameterizedTest(name = "parse with whitespace: \"{0}\"")
    @DisplayName("Should trim whitespace before parsing")
    @CsvSource({
            "' avimax37@gmail.com ',  avimax37,   gmail.com",
            "'  a@b  ',              a,           b"
    })
    void shouldTrimWhitespaceBeforeParsing(String email, String expectedUsername, String expectedDomain) {
        EmailParser.EmailParts parts = EmailParser.parse(email);

        assertEquals(expectedUsername, parts.getUsername(),
                "Username mismatch after trimming for: " + email);
        assertEquals(expectedDomain, parts.getDomain(),
                "Domain mismatch after trimming for: " + email);
    }
}
