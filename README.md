# Email Slicer (Java 17)

A simple CLI tool that parses email addresses into username and domain components. Migrated from the original Python implementation to idiomatic Java 17.

## Prerequisites

- Java 17 (JDK)
- Apache Maven 3.9+

## Build

```bash
mvn clean package
```

## Run

```bash
java -jar target/emailslicer-1.0.0.jar
```

The application will prompt you to enter an email address and display the parsed username and domain.

### Example

```
Please enter your Email Id:
avimax37@gmail.com
Your username is avimax37 and your domain is gmail.com
```

## Test

```bash
mvn test
```

## Project Structure

```
src/
├── main/java/com/emailslicer/
│   ├── EmailSlicer.java       # CLI entry point
│   ├── EmailParser.java       # Parsing logic with EmailParts value object
│   └── EmailValidator.java    # Input validation
└── test/java/com/emailslicer/
    ├── EmailSlicerTest.java    # CLI integration tests
    ├── EmailParserTest.java    # Parser unit tests
    └── EmailValidatorTest.java # Validator unit tests
```
