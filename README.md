# Email Slicer

A simple command-line tool that parses email addresses into their username (local part) and domain components.

## Overview

Email Slicer is a Java 17 CLI application that takes an email address as input and returns the username (the part before the `@` symbol) and domain (the part after the `@` symbol) as output. This is a migration from the original [Python Email Slicer](https://github.com/Modelcode-ai/email-slicer-python) to Java, maintaining behavioral equivalence while adding robust input validation and comprehensive testing.

### Key Features

- **Interactive CLI**: Prompts for email input and displays parsed results
- **Comprehensive Validation**: Detects and reports invalid email formats gracefully
- **Modern Java 17**: Uses records, try-with-resources, and other Java 17 features
- **Extensively Tested**: 80%+ test coverage with unit and integration tests
- **Executable JAR**: Can be run standalone with just `java -jar`

## Prerequisites

To build and run Email Slicer, you need:

- **Java Development Kit (JDK) 17** or later
- **Maven 3.9+** (or use the included Maven wrapper)

To verify your Java version:
```bash
java -version
```

## Building the Application

### Using Maven Wrapper (Recommended)

The project includes a Maven wrapper, so you don't need Maven installed:

```bash
# Clean and compile
./mvnw clean compile

# Run tests
./mvnw test

# Package executable JAR
./mvnw clean package
```

### Using Installed Maven

If you have Maven installed:

```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package executable JAR
mvn clean package
```

## Running the Application

### Development Mode

Run directly from source using Maven:

```bash
./mvnw exec:java
```

### Production Mode

Run the standalone executable JAR:

```bash
java -jar target/emailslicer-dkasargod.jar
```

## Usage Examples

### Valid Email Input

```
$ java -jar target/emailslicer-dkasargod.jar
Please enter your Email Id:
avimax37@gmail.com
Your username is:  avimax37
Your domain is:  gmail.com
```

### Email with Subdomain

```
$ java -jar target/emailslicer-dkasargod.jar
Please enter your Email Id:
user@mail.example.com
Your username is:  user
Your domain is:  mail.example.com
```

### Invalid Email Input

```
$ java -jar target/emailslicer-dkasargod.jar
Please enter your Email Id:
invalid-email
Please enter a valid Email Id.
```

### Other Invalid Inputs

The application validates and rejects:
- Empty input
- Emails without the `@` symbol
- Emails with multiple `@` symbols
- Emails starting with `@` (empty username)
- Emails ending with `@` (empty domain)

## Architecture

### Project Structure

```
emailslicer-dkasargod/
├── pom.xml                          # Maven build configuration
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── emailslicer/
│   │               └── EmailSlicer.java    # Main application class
│   └── test/
│       └── java/
│           └── com/
│               └── emailslicer/
│                   ├── EmailSlicerTest.java      # Unit tests
│                   └── EmailSlicerCliTest.java   # CLI integration tests
└── README.md
```

### Design Highlights

1. **ParsedEmail Record**: Uses Java 17's record feature to represent parsed email components as an immutable, type-safe data structure with automatic `equals()`, `hashCode()`, and `toString()` implementations.

2. **Validation Strategy**: The `parseEmail()` method performs structural validation using `indexOf()` and `lastIndexOf()` to ensure:
   - Non-null and non-blank input
   - Exactly one `@` symbol
   - Non-empty username (before `@`)
   - Non-empty domain (after `@`)

3. **Error Handling**: Invalid inputs throw `IllegalArgumentException` with descriptive messages. The CLI catches these exceptions and displays a user-friendly error message matching the Python original.

4. **Resource Management**: Uses try-with-resources for `Scanner` to ensure proper cleanup of input resources.

5. **Testing Approach**:
   - **Unit Tests** (`EmailSlicerTest`): Test the parsing logic with valid inputs and all validation failure cases
   - **CLI Integration Tests** (`EmailSlicerCliTest`): Simulate full user interaction by capturing stdin/stdout to verify end-to-end behavior and exact output formatting

### Behavioral Equivalence with Python

The Java implementation preserves the exact output format of the Python original, including:
- Identical prompt message: `Please enter your Email Id:`
- Identical output format with double space after colon: `Your username is:  <username>`
- Identical error message: `Please enter a valid Email Id.`
- Same line-by-line output structure

This equivalence is verified by comprehensive integration tests.

## Development

### Running Tests

```bash
# Run all tests
./mvnw test

# Run with coverage (via IDE or plugins)
./mvnw clean verify
```

### Code Quality

The codebase follows Java best practices:
- Clear, descriptive naming conventions
- Proper documentation with Javadoc comments
- No wildcard imports
- No dead code or unused dependencies
- Consistent formatting and style
- Idiomatic Java 17 usage (records, `var`, `strip()`)

## Migration from Python

This Java implementation is a faithful migration of the [Python Email Slicer](https://github.com/Modelcode-ai/email-slicer-python) with the following improvements:

- **Type Safety**: Java's static typing provides compile-time safety
- **Enhanced Validation**: More comprehensive input validation with specific error cases
- **Comprehensive Testing**: The Python version has no tests; the Java version includes extensive unit and integration tests
- **Production-Ready Packaging**: Executable JAR for easy distribution
- **Modern Language Features**: Leverages Java 17 features like records

The core functionality remains identical: read an email, validate it has exactly one `@`, split into username and domain, and display the results.

## License

This project maintains compatibility with the original Python Email Slicer license. Please refer to the [original repository](https://github.com/Modelcode-ai/email-slicer-python) for license information.

## Contributing

Contributions are welcome! Please ensure:
- All tests pass (`./mvnw test`)
- Code follows existing style conventions
- New features include appropriate tests
- Documentation is updated as needed

## Acknowledgments

Based on the original [Python Email Slicer](https://github.com/Modelcode-ai/email-slicer-python) by Modelcode.ai.
