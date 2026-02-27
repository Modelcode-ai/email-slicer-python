# Email Slicer

A Java 17 CLI tool that parses an email address into its **username** and **domain** components.

This project is a Java migration of the original [Email Slicer Python tool](https://github.com/Modelcode-ai/email-slicer-python). The Java and Python versions are independently versioned.

## Prerequisites

- **Java 17** (or later)
- **Maven 3.8+**

Verify your installation:

```bash
java -version    # Should report 17.x or later
mvn -version     # Should report 3.8+ with Java 17
```

## Build

```bash
mvn clean package
```

This compiles the source, runs all JUnit 5 tests, enforces JaCoCo code coverage (90% minimum line coverage), and produces an executable JAR.

## Run

### Interactive mode (stdin prompt)

```bash
java -jar target/emailslicer-user-1.0.0.jar
```

You will be prompted:

```
Enter your email: avimax37@gmail.com
Username: avimax37
Domain: gmail.com
```

### Argument mode

```bash
java -jar target/emailslicer-user-1.0.0.jar user@example.com
```

Output:

```
Username: user
Domain: example.com
```

### Error handling

Invalid email input produces an error message on stderr and exits with code **1**:

```bash
java -jar target/emailslicer-user-1.0.0.jar "invalidemail"
# stderr: Invalid email: Email must contain an '@' symbol
# exit code: 1
```

## Validation Rules

The parser splits on the **first** `@` character. An email is considered **invalid** if:

- The input is null, empty, or whitespace-only
- There is no `@` character
- The `@` is the first character (empty username)
- The `@` is the last character (empty domain)

**Note:** Compared to the original Python version, this implementation **tightens validation** by rejecting `@` at the first or last position. The Python version only checks for the presence of `@`.

Emails with multiple `@` characters are valid; everything before the first `@` is the username, and everything after is the domain (e.g., `user@sub@domain.com` yields username `user` and domain `sub@domain.com`).

## Project Structure

```
emailslicer-user/
├── pom.xml                                        # Maven build configuration
└── src/
    ├── main/java/com/emailslicer/
    │   ├── EmailParts.java                        # Record: username + domain
    │   ├── EmailSlicer.java                       # Core parsing logic
    │   └── Main.java                              # CLI entry point
    └── test/java/com/emailslicer/
        └── EmailSlicerTest.java                   # JUnit 5 tests
```

## Testing

Run all tests:

```bash
mvn test
```

Run tests with JaCoCo coverage verification (90% minimum):

```bash
mvn verify
```

## License

MIT
