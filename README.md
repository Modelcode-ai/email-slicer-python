# Email Slicer (Java)

A simple CLI tool that takes an email address as input and returns the username and domain as output. This is a Java 17 migration of the original [Python Email Slicer](https://github.com/Modelcode-ai/email-slicer-python).

## Prerequisites

- Java 17 JDK (e.g., Eclipse Temurin 17)
- Maven 3.8+

## Build

```bash
mvn clean package
```

This compiles the source, runs all tests, and produces an executable JAR in `target/`.

## Run

```bash
java -jar target/email-slicer-java-1.0.0.jar
```

### Example

```
Please enter your Email Id:avimax37@gmail.com
Your username is: avimax37
Your domain is: gmail.com
```

If the input does not contain an `@` character:

```
Please enter your Email Id:invalidinput
Please enter a valid Email Id.
```

## Run Tests

```bash
mvn clean test
```

## Project Structure

```
pom.xml
src/
  main/java/com/emailslicer/
    Main.java              - CLI entry point
    EmailSlicer.java       - Email parsing logic
    EmailSliceResult.java  - Record holding username and domain
    InvalidEmailException.java - Exception for invalid input
  test/java/com/emailslicer/
    EmailSlicerTest.java   - Unit tests for parsing logic
    MainCliTest.java       - CLI integration tests
```
