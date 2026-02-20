# Email Slicer

A simple Java 17 console application that parses an email address into its username and domain components.

Migrated from the original [Python Email Slicer](https://github.com/Modelcode-ai/email-slicer-python) to idiomatic Java 17 with proper validation, immutable value types, and unit tests.

## Prerequisites

- **JDK 17** or later
- **Maven 3.x** or later

## Build

```bash
mvn package
```

This compiles the source, runs the test suite, and produces an executable JAR at `target/emailslicer-dkasargod-1.0.0-SNAPSHOT.jar`.

## Run

```bash
java -jar target/emailslicer-dkasargod-1.0.0-SNAPSHOT.jar
```

### Example

```
Enter your email: avimax37@gmail.com
Username: avimax37
Domain: gmail.com
```

Invalid input produces a clear error message:

```
Enter your email: invalid-email
Error: Invalid email address: invalid-email
```

## Test

```bash
mvn test
```

## Project Structure

```
src/
  main/java/com/emailslicer/
    EmailParts.java      # Immutable record holding username and domain
    EmailSlicer.java     # Core parsing and validation logic
    Main.java            # CLI entry point
  test/java/com/emailslicer/
    EmailSlicerTest.java # JUnit 5 unit tests
```
