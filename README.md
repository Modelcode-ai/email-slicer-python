# Email Slicer (Java 17)

A Java 17 console application that extracts the username and domain parts from an email address. This is a migration of the original Python [Email Slicer](https://github.com/dkasargod/emailslicer) tool.

## Prerequisites

- Java 17 or later (JDK)

No global Maven installation is required — the project includes the Maven Wrapper.

## Build

```bash
./mvnw package
```

## Run

```bash
java -jar target/emailslicer-1.0.0.jar
```

The application will prompt you to enter an email address, then print the username and domain parts.

### Example

```
Please enter your Email Id:
avimax37@gmail.com
Your username is:  avimax37
Your domain is:  gmail.com
```

## Run Tests

```bash
./mvnw test
```

## Project Structure

```
src/main/java/com/emailslicer/
├── EmailParts.java      # Record holding username + domain
├── EmailSlicer.java     # Parsing logic (static slice method)
└── Main.java            # Console I/O entry point

src/test/java/com/emailslicer/
└── EmailSlicerTest.java # JUnit 5 unit tests
```
