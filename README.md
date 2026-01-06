# Email Slicer - Java 17 Implementation

Email Slicer is a simple command-line tool that takes an email address as input and returns the username (local part) and domain as output. This is a Java 17 implementation migrated from the original Python version, featuring improved input validation, proper error handling, and clean object-oriented design.

## Overview

This tool demonstrates basic string parsing and manipulation using Java 17 features. It extracts the username and domain components from an email address using string slicing operations (`String.indexOf` and `String.substring`).

**Key Features:**
- Clean separation between parsing logic and CLI interface
- Strict input validation with user-friendly error messages
- Support for both interactive and command-line argument modes
- Java 17 records for immutable data representation
- Comprehensive unit tests with JUnit 5

## Prerequisites

- **JDK 17** or higher (Java 17 LTS is required)
- **Apache Maven** 3.6+ for building the project

To verify your installation:
```bash
java -version   # Should show Java 17 or higher
mvn -version    # Should show Maven 3.6 or higher
```

## Build Instructions

Clone or navigate to the repository and build using Maven:

```bash
# Navigate to the Java implementation directory
cd /path/to/email-slicer-python

# Clean and build the project
mvn clean package

# This will:
# - Compile the Java source code
# - Run all unit tests
# - Create an executable JAR: target/emailslicer-1.0.0.jar
```

Expected output:
```
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

## Usage

The Email Slicer can be run in two modes:

### Interactive Mode

Run without arguments to be prompted for input:

```bash
java -jar target/emailslicer-1.0.0.jar
```

Example session:
```
Enter your email address: user@example.com
Username: user
Domain: example.com
```

### Argument Mode

Pass the email address as a command-line argument:

```bash
java -jar target/emailslicer-1.0.0.jar user@example.com
```

Output:
```
Username: user
Domain: example.com
```

### More Examples

**Valid email addresses:**
```bash
# Simple email
java -jar target/emailslicer-1.0.0.jar john.doe@company.com
Username: john.doe
Domain: company.com

# Subdomain
java -jar target/emailslicer-1.0.0.jar admin@mail.example.co.uk
Username: admin
Domain: mail.example.co.uk

# Numbers in username
java -jar target/emailslicer-1.0.0.jar user123@test.org
Username: user123
Domain: test.org

# Email with leading/trailing spaces (automatically trimmed)
java -jar target/emailslicer-1.0.0.jar " user@domain.com "
Username: user
Domain: domain.com
```

**Invalid email addresses:**
```bash
# No @ symbol
java -jar target/emailslicer-1.0.0.jar invalid-email
Invalid email address: Email must contain exactly one '@' character

# Multiple @ symbols
java -jar target/emailslicer-1.0.0.jar user@@domain.com
Invalid email address: Email must contain exactly one '@' character

# Empty username
java -jar target/emailslicer-1.0.0.jar @domain.com
Invalid email address: Email must have a non-empty username before '@'

# Empty domain
java -jar target/emailslicer-1.0.0.jar user@
Invalid email address: Email must have a non-empty domain after '@'

# Empty input
java -jar target/emailslicer-1.0.0.jar ""
Invalid email address: Email cannot be empty
```

## Validation Rules

The Email Slicer performs basic structural validation:

1. **Non-null and non-empty:** The input must not be null or empty (after trimming whitespace)
2. **Exactly one @ symbol:** The email must contain exactly one `@` character
3. **Non-empty username:** The part before `@` must not be empty
4. **Non-empty domain:** The part after `@` must not be empty
5. **Whitespace handling:** Leading and trailing whitespace is automatically trimmed

**Note:** This tool performs only basic validation for educational purposes. It does **not** implement full RFC 5322 email validation, and does not check for valid domain formats or DNS records.

## Exit Codes

The application uses the following exit codes:

- **0** - Successful parsing and output
- **2** - Input validation error (invalid email format)
- **1** - Unexpected internal failure

These distinct exit codes allow shell scripts and test harnesses to distinguish between validation failures and bugs.

## Running Tests

Execute the test suite with Maven:

```bash
mvn test
```

This runs comprehensive unit tests covering:
- Valid email formats (simple, with dots, subdomains, numbers)
- Invalid formats (no @, multiple @, empty parts)
- Edge cases (whitespace, null input)
- Exception handling and error messages

## Project Structure

```
email-slicer-python/
├── pom.xml                              # Maven build configuration
├── README.md                            # This file (Java documentation)
├── LICENSE.md                           # MIT License
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── modelcode/
│   │               └── emailslicer/
│   │                   ├── EmailSlicerApp.java          # CLI entry point
│   │                   ├── EmailParser.java             # Core parsing logic
│   │                   ├── EmailParts.java              # Result record
│   │                   └── InvalidEmailException.java   # Custom exception
│   └── test/
│       └── java/
│           └── com/
│               └── modelcode/
│                   └── emailslicer/
│                       └── EmailParserTest.java         # Unit tests
├── emailSlicer.py                       # Original Python implementation
└── (original Python README and documentation files)
```

## Differences from Python Version

This Java implementation intentionally improves upon the original Python version in several ways:

### Stricter Validation

**Python behavior** (permissive):
- Accepts emails with multiple `@` symbols (e.g., `user@@domain.com`)
- Accepts emails with empty username (e.g., `@domain.com`)
- Accepts emails with empty domain (e.g., `user@`)
- For these cases, the Python version would parse them without error, potentially producing unexpected results

**Java behavior** (strict):
- **Rejects** emails with multiple `@` symbols → throws `InvalidEmailException`
- **Rejects** emails with empty username → throws `InvalidEmailException`
- **Rejects** emails with empty domain → throws `InvalidEmailException`
- All invalid inputs produce clear, user-friendly error messages

### Output Message Format

**Python messages:**
```
Please enter your Email Id:
Your username is: user
Your domain is: domain.com
```

**Java messages:**
```
Enter your email address:
Username: user
Domain: domain.com
```

The Java version uses modernized, standardized wording for clarity and treats this as the canonical implementation going forward.

### Architecture

The Java implementation separates concerns:
- **`EmailParser`** - Pure parsing logic, reusable in any context
- **`EmailSlicerApp`** - CLI-specific input/output handling
- **`EmailParts`** - Immutable data structure (Java 17 record)
- **`InvalidEmailException`** - Domain-specific exception type

This makes the core parsing logic easily reusable in other contexts (web services, GUIs, batch processing).

## Maven Coordinates

```xml
<dependency>
    <groupId>com.modelcode</groupId>
    <artifactId>emailslicer</artifactId>
    <version>1.0.0</version>
</dependency>
```

## License

Distributed under the MIT License. See `LICENSE.md` for more information.

Original Python implementation by Avinaba Bera (2022).
Java 17 migration maintains the same MIT License.

## Educational Purpose

This tool is designed as an educational resource demonstrating:
- Python to Java migration patterns
- String manipulation and parsing in Java
- Input validation and error handling
- Clean architecture with separation of concerns
- Java 17 features (records, modern syntax)
- Unit testing with JUnit 5
- Maven project structure and build lifecycle

## Repository Structure Note

This repository contains both the original Python implementation and the new Java implementation. The Python version remains in the root directory for reference, while the Java version uses a standard Maven layout under `src/main/java/`.

Both implementations coexist to demonstrate the migration process and allow comparison between the two approaches.

## Contributing

This is an educational project. Feel free to:
- Study the code to learn about Java migration patterns
- Use it as a template for similar migration projects
- Enhance it with additional features (RFC 5322 validation, DNS checks, etc.)

## Contact

For questions about the original Python version:
- Original Author: Avinaba Bera
- GitHub: https://github.com/avimax37/email-slicer-python

For questions about the Java migration:
- See the project specification and milestone documentation in `.mcode/`
