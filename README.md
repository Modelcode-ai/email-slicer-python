# Email Slicer (Java 17 CLI)

A simple command-line tool that parses an email address into its **username** and **domain** components.

This is a Java 17 migration of the original [Python Email Slicer](https://github.com/Modelcode-ai/email-slicer-python) script (`emailSlicer.py`).

## Prerequisites

- **JDK 17** (or later) installed and available on `PATH`
- **Maven 3.8+** installed

## Build

```bash
mvn clean package
```

This compiles the source, runs the JUnit 5 test suite, and produces an executable JAR at `target/emailslicer-1.0.0.jar`.

## Usage

```bash
java -jar target/emailslicer-1.0.0.jar <email>
```

### Example

```bash
$ java -jar target/emailslicer-1.0.0.jar avimax37@gmail.com
Your user name is: avimax37
Your domain name is: gmail.com
```

### Exit Codes

| Code | Meaning |
|------|---------|
| `0`  | Email parsed successfully |
| `1`  | Missing argument or invalid email address |

## Behavior

- The input email is **trimmed** of leading and trailing whitespace before parsing.
- Validation checks only for the presence of an `@` character (minimal validation, matching the original Python script).
- The email is split at the **first** `@` into a username (before `@`) and domain (after `@`).
- Empty username (e.g., `@example.com`) or empty domain (e.g., `user@`) are considered valid as long as `@` is present.
- If no argument is provided, a usage message is printed to stderr.
- If the email is invalid (no `@`), an error message is printed to stderr.

## Running Tests

```bash
mvn clean test
```

## Project Structure

```
pom.xml
src/
  main/java/com/emailslicer/
    EmailSlicer.java   -- Core parsing and validation logic
    Main.java          -- CLI entry point
  test/java/com/emailslicer/
    EmailSlicerTest.java -- JUnit 5 unit tests
```

## License

This project is based on work originally licensed under the MIT License by Avinaba Bera.
