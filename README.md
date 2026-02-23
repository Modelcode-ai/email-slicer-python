# Email Slicer

A simple command-line tool that takes an email address as input and extracts the **username** and **domain** components using string parsing operations.

This is a Java 17 migration of the original [Email Slicer Python tool](https://github.com/Modelcode-ai/email-slicer-python), rewritten with idiomatic Java class structure, input validation, and automated tests.

## Prerequisites

- **JDK 17** installed and available on your `PATH`
- **Git** to clone the repository
- **Maven Wrapper** is included in the repository — no separate Maven installation required

## Build

Run tests:

```bash
./mvnw clean test
```

Package as an executable JAR:

```bash
./mvnw clean package
```

## Run

### Using Maven exec plugin

```bash
./mvnw -q exec:java -Dexec.mainClass="com.emailslicer.Main"
```

### Using the packaged JAR

```bash
java -jar target/emailslicer-xli-1.0.0.jar
```

## Example

### Valid email

```
$ java -jar target/emailslicer-xli-1.0.0.jar
Please enter your Email Id:
avimax37@gmail.com
Your username is:  avimax37
Your domain is:  gmail.com
```

### Invalid email

```
$ java -jar target/emailslicer-xli-1.0.0.jar
Please enter your Email Id:
invalidemail
Please enter a valid Email Id.
```

## Project Structure

```
src/
  main/java/com/emailslicer/
    EmailParts.java      # Immutable record for username + domain
    EmailSlicer.java     # Core parsing and validation logic
    Main.java            # CLI entry point
  test/java/com/emailslicer/
    EmailSlicerTest.java # Unit tests for parsing logic
    MainCliTest.java     # Integration tests for CLI behavior
```

## License

MIT License — see [LICENSE.md](LICENSE.md) for details.
