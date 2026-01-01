# Email Slicer

A simple Java 17 CLI tool that parses email addresses into username and domain components.

## Overview

Email Slicer is an educational Java application that demonstrates the migration of a simple Python script to an idiomatic Java CLI application. It extracts the username (local part before '@') and domain (part after '@') from email addresses using string slicing operations.

## Features

- **Command-line argument mode**: Pass email as an argument
- **Piped input mode**: Read email from stdin without prompts
- **Interactive mode**: Get prompted for email input
- **Input validation**: Ensures email format is valid
- **Clear error messages**: User-friendly validation errors

## Building

Build the project using Maven:

```bash
mvn clean package
```

This produces an executable JAR at `target/emailslicer-1.0.0.jar`.

## Running

### Command-line argument mode

```bash
java -jar target/emailslicer-1.0.0.jar user@example.com
```

Output:
```
Your username is: user
Your domain is: example.com
```

### Piped input mode

```bash
echo "john.doe@example.com" | java -jar target/emailslicer-1.0.0.jar
```

Output:
```
Your username is: john.doe
Your domain is: example.com
```

### Interactive mode

```bash
java -jar target/emailslicer-1.0.0.jar
```

You'll be prompted:
```
Please enter your Email Id: user@example.com
Your username is: user
Your domain is: example.com
```

## Testing

Run the test suite:

```bash
mvn test
```

The project includes:
- **20 unit tests** in `EmailSlicerTest` covering validation and parsing logic
- **4 integration tests** in `EmailSlicerCLITest` verifying CLI behavior

## Project Structure

```
src/
├── main/java/com/emailslicer/
│   ├── EmailSlicer.java           # Core parsing service
│   ├── EmailResult.java           # Result record (Java 17)
│   ├── InvalidEmailException.java # Custom validation exception
│   └── EmailSlicerCLI.java        # CLI entry point
└── test/java/com/emailslicer/
    ├── EmailSlicerTest.java       # Unit tests
    └── EmailSlicerCLITest.java    # Integration tests
```

## Requirements

- Java 17 or later
- Maven 3.6+ (for building)

## Validation Rules

The application validates email addresses with the following rules:

- Email must not be null or empty
- Leading and trailing whitespace is automatically trimmed
- Email must contain exactly one '@' symbol
- '@' symbol must not be at the beginning (empty username)
- '@' symbol must not be at the end (empty domain)

**Note**: This is an educational tool and does not implement full RFC-compliant email validation.

## Exit Codes

- `0`: Success
- `1`: Invalid email address
- `2`: Unexpected error (I/O error, etc.)

## Educational Purpose

This project demonstrates:

- Migration from Python to Java
- Java 17 features (records, text blocks in tests)
- Separation of concerns (CLI vs. business logic)
- Exception handling patterns (checked exceptions)
- String manipulation using `indexOf()` and `substring()` (analogous to Python slicing)
- Maven project structure and configuration
- JUnit 5 testing with stream redirection

## License

This is an educational example project.
