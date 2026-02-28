# Email Slicer

A simple CLI tool that parses an email address into its username and domain parts. Migrated from the original [Python Email Slicer](https://github.com/Modelcode-ai/email-slicer-python) to idiomatic Java 17.

## Prerequisites

- Java 17 JDK installed and available on `PATH`

## Build

Build the project (compiles and runs all tests):

```bash
./mvnw clean verify
```

## Run

After building, run the application:

```bash
echo "your-email@example.com" | java -jar target/emailslicer-user-1.0.0.jar
```

## Usage Examples

**Valid email:**

```bash
$ echo "avimax37@gmail.com" | java -jar target/emailslicer-user-1.0.0.jar
Your username is avimax37
Your domain is gmail.com
```

**Invalid email:**

```bash
$ echo "invalid.email.com" | java -jar target/emailslicer-user-1.0.0.jar
Invalid email address.
```

## Exit Codes

| Code | Meaning |
|------|---------|
| `0`  | Valid email parsed successfully |
| `1`  | Invalid email input (error message printed to stderr) |

## Project Structure

```
src/
  main/java/emailslicer/
    EmailSlicer.java         # Core parsing and validation logic
    EmailParts.java          # Immutable record holding username and domain
    InvalidEmailException.java # Custom exception for invalid input
    Main.java                # CLI entry point
  test/java/emailslicer/
    EmailSlicerTest.java     # JUnit 5 unit tests
```

## Running Tests

```bash
./mvnw test
```
