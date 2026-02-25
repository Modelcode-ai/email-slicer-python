# Email Slicer

A simple CLI tool that takes an email address as input and returns the username and domain as output. Migrated from the original [Python Email Slicer](https://github.com/Modelcode-ai/email-slicer-python) to Java 17.

## Prerequisites

- **Java 17** (JDK) installed and available on `PATH`.
- No separate Maven installation required — the included Maven Wrapper handles it.

## Build and Test

```bash
./mvnw clean test
```

This compiles the project and runs all unit and integration tests.

## Build Only

```bash
./mvnw clean package
```

Produces a runnable JAR at `target/emailslicer-user-1.0.0.jar`.

## Run

```bash
java -jar target/emailslicer-user-1.0.0.jar
```

The application will prompt:

```
Enter your email:
```

Type an email address and press Enter. For a valid email the tool prints:

```
Your username is: avimax37
Your domain is: gmail.com
```

For an invalid email (no `@` character) the tool prints:

```
Invalid email address.
```

## Exit Codes

| Code | Meaning |
|------|---------|
| `0`  | Email parsed successfully |
| `1`  | Invalid input (missing `@`, empty, or whitespace-only) |

## Edge-Case Behaviour

- **Multiple `@` characters** — splits at the first `@` (e.g., `name@sub@domain.com` → username `name`, domain `sub@domain.com`).
- **Leading/trailing whitespace** — trimmed before parsing.
- **`@domain.com`** (empty username) and **`user@`** (empty domain) — treated as valid, preserving parity with the original Python script.

## Project Structure

```
src/
  main/java/emailslicer/
    Main.java                 # CLI entry point
    EmailSlicer.java          # Parsing logic + EmailParts
    InvalidEmailException.java # Custom exception for invalid input
  test/java/emailslicer/
    EmailSlicerTest.java      # Unit tests for EmailSlicer.parse()
    MainCliTest.java          # CLI integration tests
pom.xml                       # Maven build (Java 17, JUnit 5)
mvnw / mvnw.cmd               # Maven Wrapper scripts
```

## License

See [LICENSE.md](LICENSE.md) for details.
