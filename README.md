# Email Slicer - Java 17

A command-line email parsing tool that extracts username and domain components from email addresses.

## Overview

This project is a Java 17 migration of the original [Email Slicer Python tool](https://github.com/avimax37/email-slicer-python) by Avinaba Bera. The Java version preserves the core functionality while introducing proper object-oriented structure, comprehensive validation, and full test coverage.

The application takes an email address as input and returns the username (local part) and domain as separate components, similar to the Python version's string slicing behavior.

## Prerequisites

Before building and running this application, ensure you have the following installed:

- **Java 17** (LTS) - [Download Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- **Maven 3.9.x or higher** - [Download Maven](https://maven.apache.org/download.cgi)

Verify your installations:
```bash
java -version    # Should show Java 17
mvn -version     # Should show Maven 3.9.x or higher
```

## Building the Project

To build the project and create an executable JAR file:

```bash
mvn clean package
```

Or to build and install to your local Maven repository:

```bash
mvn clean install
```

The build process will:
- Compile all Java source files
- Run all unit tests
- Create an executable JAR at `target/email-slicer-java-1.0.0.jar`

## Running the Application

The Email Slicer supports two modes of operation:

### Interactive Mode

Run without arguments to be prompted for input:

```bash
java -jar target/email-slicer-java-1.0.0.jar
```

**Example Session:**
```
Please enter your Email Id:
avimax37@gmail.com
Username: avimax37
Domain: gmail.com
```

### Command-Line Mode

Pass the email address as a command-line argument:

```bash
java -jar target/email-slicer-java-1.0.0.jar user@example.com
```

**Output:**
```
Username: user
Domain: example.com
```

### Error Handling Example

Invalid email addresses will display an error message:

```bash
java -jar target/email-slicer-java-1.0.0.jar invalid-email
```

**Output:**
```
Error: Email address must contain exactly one '@' character.
```

The application exits with code 0 on success and code 1 on validation errors.

## Validation Behavior

The Java implementation performs **basic structural validation** to ensure email addresses are well-formed:

### Validation Rules
- Input must not be null or empty (after trimming whitespace)
- Must contain exactly one `@` character
- Username (part before `@`) must be non-empty
- Domain (part after `@`) must be non-empty
- Domain must contain at least one `.` (dot) character
- Domain must not contain whitespace

### Important Note

This is **not fully RFC-compliant** email validation. The tool performs simple structural checks suitable for basic email parsing and educational purposes. For production email validation, consider using a dedicated library like Jakarta Mail or Apache Commons Validator.

## Behavioral Differences from Python Version

The Java version maintains functional compatibility with the Python version while adding enhanced validation:

### Enhancements
1. **Null and whitespace validation**: The Java version explicitly rejects null inputs and empty strings after trimming
2. **Multiple `@` symbol detection**: Rejects emails with more than one `@` symbol
3. **Empty part validation**: Explicitly validates that both username and domain are non-empty
4. **Domain format validation**: Requires domains to contain at least one dot (e.g., `user@domain` is rejected)

### Output Format Differences
The Java version uses slightly different output messages for clarity:
- **Python**: `"Your username is: "`
- **Java**: `"Username: "`

- **Python**: `"Your domain is: "`
- **Java**: `"Domain: "`

### Examples of Additional Strictness

Inputs **accepted by Python** but **rejected by Java**:

| Input | Reason for Rejection |
|-------|---------------------|
| `user@domain` | Domain must contain at least one dot |
| `user@@example.com` | Must contain exactly one @ character |
| `@example.com` | Username must be non-empty |
| `user@` | Domain must be non-empty |
| `user @example.com` | Domain contains whitespace (after trimming, domain would be `@example.com`) |

For all other inputs that the Python version treats as valid (single `@` with non-empty username and domain), the Java version produces **identical username and domain results**.

## Testing

Run the test suite:

```bash
mvn test
```

The project includes comprehensive unit tests with:
- Parameterized tests for valid email formats
- Exception validation tests for invalid inputs
- Coverage of edge cases (whitespace, empty strings, null inputs, multiple @ symbols)
- Greater than 90% line and branch coverage

### Running Checkstyle

The project uses Checkstyle for code quality checks:

```bash
mvn checkstyle:check
```

## Project Structure

```
email-slicer-python/
├── pom.xml
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/emailslicer/
│   │           ├── Main.java                    # CLI entry point
│   │           ├── EmailSlicer.java             # Parsing and validation logic
│   │           ├── EmailAddress.java            # Immutable value object
│   │           └── EmailValidationException.java # Custom exception
│   └── test/
│       └── java/
│           └── com/emailslicer/
│               └── EmailSlicerTest.java         # Comprehensive test suite
└── README.md
```

## Architecture

The application follows clean separation of concerns:

- **Main**: Handles all user I/O and program flow
- **EmailSlicer**: Core parsing and validation logic
- **EmailAddress**: Immutable value object representing a parsed email
- **EmailValidationException**: Signals invalid email inputs with clear messages

## License

Distributed under the MIT License. See `LICENSE.md` for more information.

## Acknowledgments

This Java implementation is based on the original [Email Slicer Python tool](https://github.com/avimax37/email-slicer-python) created by Avinaba Bera (Copyright 2022).

The Java migration was created as an educational project demonstrating Python-to-Java migration patterns, modern Java 17 features, and best practices in:
- Object-oriented design
- Input validation and error handling
- Unit testing with JUnit 5
- Maven project structure
- Javadoc documentation

## Contact

For issues or questions about this Java implementation, please open an issue in the repository.

For the original Python version, visit [avimax37/email-slicer-python](https://github.com/avimax37/email-slicer-python).
