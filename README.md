# Email Slicer - Java 17

A simple command-line tool that parses email addresses into username and domain components.

Migrated from Python to Java 17 with idiomatic Java structure, enhanced validation, and comprehensive testing.

## Overview

Email Slicer is an educational tool that demonstrates basic string manipulation and parsing concepts. Given an email address like `avimax37@gmail.com`, it extracts:
- **Username**: `avimax37` (the part before the `@`)
- **Domain**: `gmail.com` (the part after the `@`)

This Java implementation is a faithful migration of the [original Python Email Slicer](https://github.com/Modelcode-ai/email-slicer-python) by Avinaba Bera, with improved input validation and robust error handling.

## Technology Stack

- **Language**: Java 17 LTS
- **Build Tool**: Apache Maven 3.9.x
- **Testing Framework**: JUnit 5 (Jupiter)
- **Packaging**: Executable JAR with manifest-configured Main-Class
- **Dependencies**: None (runtime) - pure Java standard library

## Build Instructions

### Prerequisites
- Java 17 or later (JDK)
- Maven 3.9.x (or use the included Maven wrapper)

### Build the Project

```bash
# Using Maven wrapper (recommended)
./mvnw clean package

# Or using system Maven
mvn clean package
```

This will:
1. Compile the source code
2. Run all unit and integration tests (34 tests)
3. Create an executable JAR at `target/emailslicer-cli-1.0.0.jar`

### Run Tests Only

```bash
./mvnw clean test
```

Expected output: `Tests run: 34, Failures: 0, Errors: 0, Skipped: 0`

## Usage

The application supports two modes of operation:

### Interactive Mode

Run without arguments to enter interactive mode:

```bash
java -jar target/emailslicer-cli-1.0.0.jar
```

You'll be prompted to enter an email address:

```
Please enter your Email Id: avimax37@gmail.com
Your username is:  avimax37
Your domain is:  gmail.com
```

### Command-Line Argument Mode

Pass an email address as an argument:

```bash
java -jar target/emailslicer-cli-1.0.0.jar "user@example.com"
```

Output:

```
Your username is:  user
Your domain is:  example.com
```

### Additional Examples

```bash
# Single-character username
java -jar target/emailslicer-cli-1.0.0.jar "a@domain.com"
# Output:
# Your username is:  a
# Your domain is:  domain.com

# Complex email with special characters
java -jar target/emailslicer-cli-1.0.0.jar "test.user+tag@subdomain.domain.com"
# Output:
# Your username is:  test.user+tag
# Your domain is:  subdomain.domain.com

# Whitespace is automatically trimmed
java -jar target/emailslicer-cli-1.0.0.jar "  user@example.com  "
# Output:
# Your username is:  user
# Your domain is:  example.com
```

### Invalid Input Handling

The Java version includes enhanced validation compared to the Python original:

```bash
# Missing @ symbol
java -jar target/emailslicer-cli-1.0.0.jar "invalidemail"
# Output: Please enter a valid Email Id.
# Exit code: 1

# Empty username
java -jar target/emailslicer-cli-1.0.0.jar "@domain.com"
# Output: Please enter a valid Email Id.
# Exit code: 1

# Empty domain
java -jar target/emailslicer-cli-1.0.0.jar "user@"
# Output: Please enter a valid Email Id.
# Exit code: 1

# Multiple @ symbols
java -jar target/emailslicer-cli-1.0.0.jar "user@@domain.com"
# Output: Please enter a valid Email Id.
# Exit code: 1
```

**Note**: The Python original accepts some of these edge cases (like `user@` or `@domain.com`), but the Java version correctly rejects them. This is an intentional improvement to prevent processing invalid emails.

## Project Structure

```
emailslicer-dkasargod/
├── pom.xml                          # Maven build configuration
├── README.md                        # This file
├── LICENSE.md                       # MIT License
├── .gitignore                       # Git ignore patterns
└── src/
    ├── main/java/com/emailslicer/
    │   ├── EmailSlicer.java         # Main CLI class with hybrid input mode
    │   ├── EmailParser.java         # Core parsing and validation logic
    │   ├── EmailResult.java         # Record for username/domain pair (Java 17)
    │   └── EmailValidationException.java # Custom unchecked exception
    └── test/java/com/emailslicer/
        ├── EmailParserTest.java     # Unit tests for parser (19 tests)
        └── EmailSlicerCliTest.java  # Integration tests for CLI (15 tests)
```

## Design Decisions

During the migration from Python to Java, three key design decisions were made:

### DD#1: Input Acquisition Strategy - Hybrid Mode

**Decision**: Implement hybrid mode supporting both interactive and command-line argument input.

**Rationale**:
- The Python original is purely interactive (prompts via stdin)
- Java users often expect command-line argument support for scripting
- Hybrid mode provides flexibility without breaking the original interactive workflow

**Implementation**:
- `args.length == 0`: Interactive mode (prompts user via stdin)
- `args.length == 1`: Command-line mode (uses `args[0]` as email)
- `args.length > 1`: Displays usage message and exits with error code 1

**Impact**: Enhanced usability for batch processing and automation scenarios.

### DD#2: Exception Handling Strategy - Single Unchecked Exception

**Decision**: Use a single `EmailValidationException` (unchecked) with descriptive messages for all validation failures.

**Rationale**:
- Keeps the implementation simple and educational (matches the tool's purpose)
- Unchecked exception avoids verbose `throws` clauses throughout the codebase
- Different error messages provide context without requiring an exception hierarchy
- Aligns with Java best practices for business logic validation

**Implementation**:
```java
public class EmailValidationException extends RuntimeException {
    public EmailValidationException(String message) {
        super(message);
    }
}
```

**Validation checks**:
- Email must not be null
- Email must not be empty (after trimming)
- Email must contain exactly one `@` character
- Username (before `@`) must be non-empty
- Domain (after `@`) must be non-empty

**Impact**: Clear, maintainable error handling with user-friendly error messages.

### DD#3: String Manipulation Pattern - Direct Substring Operations

**Decision**: Use `indexOf()`, `lastIndexOf()`, and `substring()` for email parsing.

**Rationale**:
- Mirrors the Python original's slicing semantics (`email[:idx]` and `email[idx+1:]`)
- Maintains the educational focus on basic string manipulation
- More explicit and readable than regex or `split()` approaches
- Provides fine-grained control for validation (detecting empty username/domain)

**Implementation**:
```java
int atIndex = email.indexOf('@');
String username = email.substring(0, atIndex);
String domain = email.substring(atIndex + 1);
```

**Impact**: Code is easy to understand for learners studying string manipulation concepts.

## Behavior Parity with Python Version

The Java implementation has been thoroughly tested for behavior parity:

### Valid Emails - Identical Output

All valid emails produce **character-for-character identical output** to the Python version:

| Input | Username | Domain |
|-------|----------|--------|
| `avimax37@gmail.com` | `avimax37` | `gmail.com` |
| `user@example.com` | `user` | `example.com` |
| `a@b.co` | `a` | `b.co` |
| `test.user+tag@subdomain.domain.com` | `test.user+tag` | `subdomain.domain.com` |
| `  spaces@example.com  ` | `spaces` | `example.com` (trimmed) |

### Invalid Emails - Enhanced Validation

The Java version includes **improved validation** that rejects edge cases the Python original accepts:

| Input | Python Behavior | Java Behavior | Reason |
|-------|----------------|---------------|---------|
| `nodomain` | ❌ Error | ❌ Error | Both reject (no `@`) |
| `user@` | ✅ Accepts (empty domain) | ❌ Rejects | Enhanced: requires non-empty domain |
| `@domain.com` | ✅ Accepts (empty username) | ❌ Rejects | Enhanced: requires non-empty username |
| `user@@domain.com` | ✅ Accepts (`@domain.com` as domain) | ❌ Rejects | Enhanced: requires exactly one `@` |
| Empty string | ❌ Error | ❌ Rejects | Enhanced: explicit empty check |

**Note**: These differences are **intentional improvements** to prevent processing malformed email addresses. The spec explicitly calls for "slightly more robust input validation" while preserving the educational nature of the tool.

## Testing

The project includes comprehensive test coverage (>90% for core logic):

### Unit Tests (EmailParserTest)
- Valid email parsing (5 test cases)
- Validation error cases (8 test cases)
- Edge cases: whitespace, special characters, Unicode (6 test cases)
- Total: **19 tests**

### Integration Tests (EmailSlicerCliTest)
- Interactive mode behavior
- Command-line argument mode
- Error handling and exit codes
- Output format verification
- Total: **15 tests**

Run tests with:
```bash
./mvnw clean test
```

## Exit Codes

The application follows Unix conventions:
- **0**: Success (valid email parsed)
- **1**: Error (invalid email or incorrect usage)

Example:
```bash
java -jar target/emailslicer-cli-1.0.0.jar "user@example.com"
echo $?  # Output: 0

java -jar target/emailslicer-cli-1.0.0.jar "invalid"
echo $?  # Output: 1
```

## License and Attribution

This project is licensed under the **MIT License** - see [LICENSE.md](LICENSE.md) for details.

### Original Author

The original Python Email Slicer was created by **Avinaba Bera**:
- GitHub: [avimax37/email-slicer-python](https://github.com/avimax37/email-slicer-python)
- LinkedIn: [Avinaba Bera](https://www.linkedin.com/in/avinaba-bera)

### Java Migration

This Java 17 migration was created as part of a language modernization project, preserving the original tool's simplicity and educational value while adding:
- Idiomatic Java structure with separation of concerns
- Enhanced input validation
- Comprehensive test coverage (34 automated tests)
- Modern Java 17 features (records, try-with-resources)
- Maven build infrastructure
- Dual operation modes (interactive and command-line)

## Contributing

This is an educational project demonstrating Python-to-Java migration patterns. Suggestions for improvements are welcome, keeping in mind the goal is to maintain simplicity and educational clarity.

## Acknowledgments

- Original concept and implementation by Avinaba Bera
- Inspired by the educational approach of teaching string manipulation through practical examples
- Built with Java 17 LTS and Maven for modern Java development practices
