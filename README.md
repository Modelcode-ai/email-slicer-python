# Email Slicer

A simple command-line tool that parses an email address into its **username** and **domain** components.

This is a Java 17 port of the original [Python Email Slicer](https://github.com/Modelcode-ai/email-slicer-python) by Avinaba Bera.

## Prerequisites

- **Java 17** (or newer) — JDK must be installed and available on `PATH`
- **Maven 3.6+** — or use the included Maven Wrapper (`./mvnw`)

## Build

```bash
# Using Maven Wrapper (recommended)
./mvnw clean package

# Or using system Maven
mvn clean package
```

This produces an executable fat JAR at:

```
target/emailslicer-1.0.0-jar-with-dependencies.jar
```

## Run

```bash
java -jar target/emailslicer-1.0.0-jar-with-dependencies.jar
```

The program will prompt you to enter an email address, then display the parsed username and domain:

```
Enter your email address: user@example.com
Your username is: user
Your domain is: example.com
```

## Run Tests

```bash
./mvnw test
```

## Usage Examples

| Input                    | Username | Domain           |
|--------------------------|----------|------------------|
| `user@example.com`       | `user`   | `example.com`    |
| `  user@example.com  `   | `user`   | `example.com`    |
| `user@sub@domain.com`    | `user`   | `sub@domain.com` |

Invalid inputs (no `@`, empty username, empty domain, blank input) produce an error message and exit with code 1.

## Differences from the Python Version

This Java implementation intentionally diverges from the original Python script in two areas:

1. **Stricter validation** — The Python script accepts inputs like `@example.com` (empty username) and `user@` (empty domain), printing empty values. The Java version rejects these as invalid and exits with code 1.

2. **Exit codes** — The Python script always exits with code 0, even for invalid input. The Java version exits with code 1 on invalid or missing input, and code 0 on success.

## Project Structure

```
emailslicer-dkasargod/
  pom.xml                                          # Maven build configuration
  mvnw / mvnw.cmd                                  # Maven Wrapper scripts
  src/
    main/java/com/emailslicer/
      Main.java                                    # CLI entry point
      EmailSlicer.java                             # Core parsing logic
    test/java/com/emailslicer/
      EmailSlicerTest.java                         # Unit tests
```

## License

Based on original work by Avinaba Bera, licensed under the MIT License.
