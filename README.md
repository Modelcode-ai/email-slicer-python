# Email Slicer - Java 17

A simple command-line tool that parses email addresses into username and domain components.

Migrated from Python to Java 17 with idiomatic Java structure, proper validation, and comprehensive testing.

## Features

- Parse email addresses into username and domain parts
- Input validation (checks for exactly one '@', non-empty username and domain)
- Two modes of operation: interactive and command-line argument
- Clean error messages without stack traces
- Built with Java 17 and Maven

## Build Instructions

```bash
# Build the project
./mvnw clean package

# Run tests
./mvnw clean test
```

## Usage

### Interactive Mode

Run without arguments to enter interactive mode:

```bash
java -jar target/emailslicer-cli-1.0.0.jar
```

You'll be prompted to enter an email address:

```
Please enter your Email Id: avimax37@gmail.com
Your username is:  avimax37
Your domain is:  gmail.com
```

### Command-Line Argument Mode

Pass an email address as an argument:

```bash
java -jar target/emailslicer-cli-1.0.0.jar "user@example.com"
```

Output:

```
Your username is:  user
Your domain is:  example.com
```

## Project Structure

```
src/
  main/java/com/emailslicer/
    EmailSlicer.java              # Main CLI class
    EmailParser.java              # Core parsing and validation logic
    EmailResult.java              # Record for username/domain pair
    EmailValidationException.java # Custom validation exception
  test/java/com/emailslicer/
    EmailParserTest.java          # Unit tests for parser
    EmailSlicerCliTest.java       # Integration tests for CLI
```

## Requirements

- Java 17 or later
- Maven 3.9.x (or use included Maven wrapper `./mvnw`)

## Original Python Version

This is a migration of the Python Email Slicer tool from:
https://github.com/Modelcode-ai/email-slicer-python
