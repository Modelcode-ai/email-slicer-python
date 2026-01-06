# Email Slicer

Email Slicer is a simple command-line tool that parses email addresses into their username and domain components. This is a Java 17 port of the [original Python implementation](https://github.com/Modelcode-ai/email-slicer-python).

## Overview

The tool takes an email address as input and extracts two components:
- **Username**: The part before the `@` symbol
- **Domain**: The part after the `@` symbol

This Java implementation maintains behavioral parity with the original Python version, including identical prompt text, output format, error messages, and validation rules.

## Requirements

- Java 17 or higher
- Apache Maven 3.6 or higher

## Building the Project

To build the project, run:

```bash
mvn clean package
```

This command will:
- Compile the Java source files
- Run the unit tests
- Package the application as an executable JAR file at `target/email-slicer-1.0.0.jar`

## Running the Application

After building, you can run the Email Slicer tool with:

```bash
java -jar target/email-slicer-1.0.0.jar
```

## Example Usage

```text
Please enter your Email Id:
avimax37@gmail.com
Your username is: avimax37
Your domain is: gmail.com
```

### Example with Invalid Input

```text
Please enter your Email Id:
invalidemail
Please enter a valid Email Id.
```

## Validation Rules

The tool performs minimal validation to maintain parity with the Python version:
- The email must contain an `@` symbol
- The `@` symbol cannot be at the beginning (there must be a username)
- The `@` symbol cannot be at the end (there must be a domain)
- Leading and trailing whitespace is automatically trimmed

Note: This tool does not perform RFC 5322 compliant email validation. It uses simple string parsing to extract username and domain components.

## Testing

To run the unit tests:

```bash
mvn test
```

The test suite includes validation of:
- Valid email formats with various structures
- Invalid emails (missing `@`, `@` at incorrect positions, empty input)
- Whitespace handling
- Edge cases (multiple `@` symbols, etc.)

## Project Structure

```
email-slicer-java/
├── pom.xml                          # Maven build configuration
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/example/emailslicer/
│   │           ├── EmailSlicer.java    # CLI entry point
│   │           ├── EmailParser.java    # Parsing and validation logic
│   │           ├── EmailResult.java    # Result record (username, domain)
│   │           └── Messages.java       # User-facing text constants
│   └── test/
│       └── java/
│           └── com/example/emailslicer/
│               └── EmailParserTest.java # JUnit 5 tests
└── README.md
```

## Behavioral Parity with Python Version

This Java implementation maintains exact behavioral parity with the original Python script:
- Identical prompt text: `"Please enter your Email Id:"`
- Identical error message: `"Please enter a valid Email Id."`
- Identical output format with the same labels and spacing
- Same validation rules (only checks for `@` position, no advanced validation)
- Same exit codes (0 for success, 1 for invalid input)

## License

This project is a migration of the original Python Email Slicer tool. See the [original repository](https://github.com/Modelcode-ai/email-slicer-python) for license information.
