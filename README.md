# Email Slicer

Email Slicer is a simple CLI tool that takes an email address as input and returns the username and domain as output. It parses email addresses using string slicing operations.

This is a Java 17 implementation migrated from the [original Python version](https://github.com/Modelcode-ai/email-slicer-python).

## Prerequisites

- **Java 17** JDK (or later)
- **Maven 3.8+**

## Build

Compile the project, run tests, and package the JAR:

```bash
mvn clean package
```

To run only the tests:

```bash
mvn test
```

## Run

After packaging, run the application with:

```bash
java -jar target/emailslicer-user-1.0.0-SNAPSHOT.jar
```

## Example

Input:
```
Please enter an email address:
avimax37@gmail.com
```

Output:
```
Your username is: avimax37
Your domain is: gmail.com
```

If the input does not contain an `@` character, the application prints:
```
Invalid email address.
```

## Project Structure

```
emailslicer-user/
  pom.xml
  src/
    main/java/com/emailslicer/
      Main.java                  # CLI entry point
      EmailSlicer.java           # Core parsing and validation logic
      EmailParts.java            # Record holding username and domain
      InvalidEmailException.java # Exception for invalid email input
    test/java/com/emailslicer/
      EmailSlicerTest.java       # JUnit 5 test suite
```

## License

Distributed under the MIT License.
