# Email Slicer (Java 17)

A simple command-line tool that takes an email address as input and returns the username and domain as output. This is the **Java 17** modernization of the [original Python Email Slicer](https://github.com/Modelcode-ai/email-slicer-python).

## Features

- Prompts the user for an email address via the console
- Parses the email into username (before `@`) and domain (after `@`)
- Validates the email format before slicing
- Clean separation between CLI and core logic

## Prerequisites

- **Java 17** JDK installed and available on `PATH`
- **Maven 3.6+** installed and available on `PATH`

## Build

```bash
mvn clean package
```

This compiles the source, runs all tests, and produces an executable JAR in the `target/` directory.

## Run

```bash
java -jar target/emailslicer-user-<version>.jar
```

Example session:

```
Please enter your Email Id:
avimax37@gmail.com
Your username is: avimax37
Your domain is: gmail.com
```

If the email is invalid:

```
Please enter your Email Id:
invalid-email
Please enter a valid Email Id.
```

## Run Tests

```bash
mvn test
```

The test suite includes:
- **EmailSlicerTest** — unit tests for the core parsing/validation logic
- **AppTest** — CLI behavior tests verifying exact console output

## Project Structure

```
emailslicer-user/
├── pom.xml
└── src/
    ├── main/java/com/emailslicer/
    │   ├── App.java            # CLI entrypoint (main method)
    │   ├── EmailParts.java     # Immutable value type for username + domain
    │   └── EmailSlicer.java    # Core email parsing and validation
    └── test/java/com/emailslicer/
        ├── AppTest.java        # CLI behavior tests
        └── EmailSlicerTest.java # Unit tests for EmailSlicer
```

## Migration Notes

This project was migrated from a single-file Python script (`emailSlicer.py`) to an idiomatic Java 17 Maven project. Key changes from the original:

### Behavior preserved
- Trimming of leading/trailing whitespace before validation
- Single prompt and single read from standard input
- Same success messages: `Your username is: <username>` / `Your domain is: <domain>`
- Same error message: `Please enter a valid Email Id.`
- Exit code `0` for both valid and invalid inputs

### Strengthened validation (intentional changes)
- Emails with **multiple `@` characters** are now rejected as invalid (the Python version used `str.find("@")` which would accept the first `@`)
- Emails where `@` is the **first or last character** are now rejected as invalid

## License

This project is licensed under the MIT License — see the [LICENSE.md](LICENSE.md) file for details.
