# Email Slicer

A simple CLI tool that takes an email address as input and returns the username and domain as output. Migrated from the original Python implementation to idiomatic Java 17.

## Requirements

- **Java 17** (JDK)
- **Maven 3.8+**

## Build

```bash
mvn package
```

## Run

```bash
java -jar target/emailslicer-user-1.0-SNAPSHOT.jar
```

## Run Tests

```bash
mvn test
```

## Example Usage

```
Please enter your Email Id:
user@domain.com
Your username is:  user
Your domain is:  domain.com
```

For invalid input:
```
Please enter your Email Id:
invalidemail
Please enter a valid Email Id.
```

## Project Structure

```
src/
  main/java/com/emailslicer/
    Main.java            - CLI entry point
    EmailSlicer.java     - Core parsing & validation logic
  test/java/com/emailslicer/
    EmailSlicerTest.java - Unit tests
```
