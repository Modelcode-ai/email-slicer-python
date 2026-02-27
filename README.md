# Email Slicer

A simple command-line tool that takes an email address as input and returns the username and domain as output. Migrated from the original [Python implementation](https://github.com/Modelcode-ai/email-slicer-python) to idiomatic Java 17.

## Prerequisites

- **Java 17** JDK installed and available on `PATH`
- **Maven 3.6+** installed

## Build

```bash
mvn clean package
```

## Run

```bash
java -cp target/emailslicer-xli-1.0-SNAPSHOT.jar com.emailslicer.Main
```

### Example

```
Enter your Email: avimax37@gmail.com
Your username is: avimax37
Your domain is: gmail.com
```

If the input does not contain `@`:

```
Enter your Email: bad-email
Please enter a valid Email Id.
```

## Test

```bash
mvn test
```

The test suite includes:

- **Unit tests** (`EmailSlicerTest`) — valid emails, whitespace handling, multiple `@` characters, missing `@`, empty/null input, and edge cases.
- **CLI tests** (`MainTest`) — redirect `System.in`/`System.out` to verify exact console output matches the specification.

## Project Structure

```
emailslicer-xli/
├── pom.xml
├── src/
│   ├── main/java/com/emailslicer/
│   │   ├── EmailSlicer.java    # Core parsing logic
│   │   └── Main.java           # CLI entry point
│   └── test/java/com/emailslicer/
│       ├── EmailSlicerTest.java # Unit tests
│       └── MainTest.java        # CLI behavior tests
└── README.md
```

## Behavior Notes

- The tool reads **one email** and exits (no interactive loop).
- Input is trimmed of leading/trailing whitespace before parsing.
- The email is split at the **first** `@` character; additional `@` characters belong to the domain.
- Output formatting follows the modernization specification with single-space separators (differs slightly from the Python original, which used Python `print()` comma separators producing double spaces).
