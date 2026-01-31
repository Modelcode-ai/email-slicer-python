# Email Slicer (Java 17)

A simple command-line tool that parses email addresses into username and domain components. This is a Java 17 migration of the original Python Email Slicer tool.

## Overview

Email Slicer prompts the user to enter an email address, validates the input, and extracts the username (the part before the `@` symbol) and the domain (the part after the `@` symbol).

## Prerequisites

Before building and running Email Slicer, ensure you have the following installed:

- **Java Development Kit (JDK) 17** or later
  - Verify installation: `java -version`
  - Should display Java version 17 or higher

- **Apache Maven 3.9.x** or later
  - Verify installation: `mvn -version`
  - Should display Maven version 3.9.x or higher

- **Git** (for cloning the repository)

## Building the Project

To build the project and run all unit tests:

```bash
mvn clean package
```

This command will:
1. Clean any previous build artifacts
2. Compile the Java source files
3. Run all unit tests
4. Create a JAR file in the `target/` directory

### Running Tests Only

To run just the unit tests without building the JAR:

```bash
mvn test
```

## Running the Application

After building the project, you can run the Email Slicer application using the following command:

```bash
java -cp target/email-slicer-java-1.0.0-SNAPSHOT.jar com.modelcode.emailslicer.EmailSlicer
```

Alternatively, since the JAR manifest includes the main class, you can run:

```bash
java -jar target/email-slicer-java-1.0.0-SNAPSHOT.jar
```

## Usage Examples

### Valid Email Address

```
$ java -jar target/email-slicer-java-1.0.0-SNAPSHOT.jar
Please enter your Email Id:
avimax37@gmail.com
Your username is:  avimax37
Your domain is:  gmail.com
```

### Email with Special Characters

The tool correctly handles usernames and domains containing dots, underscores, and hyphens:

```
$ java -jar target/email-slicer-java-1.0.0-SNAPSHOT.jar
Please enter your Email Id:
user.name_test-123@sub.domain.com
Your username is:  user.name_test-123
Your domain is:  sub.domain.com
```

### Invalid Email Address

```
$ java -jar target/email-slicer-java-1.0.0-SNAPSHOT.jar
Please enter your Email Id:
not-an-email
Please enter a valid Email Id.
```

### Whitespace Handling

The tool automatically trims leading and trailing whitespace:

```
$ java -jar target/email-slicer-java-1.0.0-SNAPSHOT.jar
Please enter your Email Id:
  user@example.com
Your username is:  user
Your domain is:  example.com
```

## Project Structure

```
email-slicer-java/
├── pom.xml                                          # Maven build configuration
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/modelcode/emailslicer/
│   │           ├── EmailSlicer.java                # CLI entry point
│   │           ├── EmailParser.java                # Parsing and validation logic
│   │           └── EmailResult.java                # Data record for parsed results
│   └── test/
│       └── java/
│           └── com/modelcode/emailslicer/
│               └── EmailParserTest.java            # Unit tests
└── README.md
```

## Implementation Details

### Architecture

The application follows a three-class design that separates concerns:

- **EmailSlicer**: The main CLI entry point that handles console I/O and user interaction
- **EmailParser**: Pure parsing logic with input validation (stateless, no side effects)
- **EmailResult**: Java 17 record that holds the parsed username and domain

### Validation Rules

The parser validates email addresses according to these rules:

1. Input must not be `null`
2. After trimming whitespace, input must not be empty
3. The email must contain exactly one `@` character
4. The username (before `@`) must be non-empty
5. The domain (after `@`) must be non-empty

Invalid inputs result in a user-friendly error message without exposing technical details.

### Testing

The project includes comprehensive unit tests with >90% line coverage for the parsing logic, covering:

- Valid email addresses with various formats
- Whitespace handling (leading/trailing spaces)
- Invalid inputs (null, empty, missing `@`, multiple `@` symbols)
- Boundary cases (single-character usernames/domains)
- Special characters (dots, underscores, hyphens)

## Original Python Version

This Java implementation is a modernized migration of the original Python Email Slicer tool, preserving the user experience while adding:

- Stronger input validation
- Separation of parsing logic from I/O for testability
- Comprehensive unit test coverage
- Type safety with Java 17 records

The original Python version can be found at: https://github.com/Modelcode-ai/email-slicer-python

## License

This project maintains the same licensing as the original Python implementation.
