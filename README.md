# Email Slicer (Java)

Email Slicer is a simple tool where an email address is provided as input and the application returns the username and the domain of the email address as output. It splits the email on the first `@` character using Java string operations.

This is a Java 17 migration of the original [Python Email Slicer](https://github.com/Modelcode-ai/email-slicer-python).

## Built With

- Java 17
- Maven

## Prerequisites

- **Java 17 JDK** installed and on `PATH`
- **Maven 3.8.6+** installed (or use the included Maven wrapper)

## Build

```bash
# Using Maven wrapper (recommended)
./mvnw clean package

# Or using system Maven
mvn clean package
```

## Run

```bash
java -cp target/emailslicer-user-1.0.0.jar emailslicer.EmailSlicer
```

## Example

Input:
```
Enter your email: avimax37@gmail.com
```

Output:
```
Your username is: avimax37
Your domain is: gmail.com
```

If the input does not contain `@`, the tool prints:
```
Invalid email address.
```

## Run Tests

```bash
# Using Maven wrapper
./mvnw test

# Or using system Maven
mvn test
```

## Project Structure

```
pom.xml
src/
  main/java/emailslicer/
    EmailSlicer.java        # Main class with CLI and parsing logic
    EmailSliceResult.java    # Value object for parsed email result
  test/java/emailslicer/
    EmailSlicerTest.java     # JUnit 5 unit tests
```

## License

Distributed under the MIT License.
