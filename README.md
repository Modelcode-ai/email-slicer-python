# Email Slicer — Java 17

Email Slicer is a simple CLI tool that takes an email address as input and returns the username and domain as output. This is a Java 17 migration of the [original Python implementation](https://github.com/Modelcode-ai/email-slicer-python).

## Prerequisites

- **Java 17 JDK** installed and available on the `PATH`
- **Maven 3.8+** installed

## Build

Compile the project:

```bash
mvn compile
```

Run the tests:

```bash
mvn test
```

Package as a runnable JAR:

```bash
mvn package
```

## Usage

Run the application:

```bash
java -jar target/emailslicer-user-1.0-SNAPSHOT.jar
```

Or using the classpath:

```bash
java -cp target/emailslicer-user-1.0-SNAPSHOT.jar com.emailslicer.Main
```

## Example

Input:

```
Please enter your Email Id:
avimax37@gmail.com
```

Output:

```
Your username is:  avimax37
Your domain is:  gmail.com
```

If an invalid email (without `@`) is provided:

```
Please enter your Email Id:
invalidemail
Please enter a valid Email Id.
```

## Project Structure

```
pom.xml
src/
  main/java/com/emailslicer/
    Main.java              # CLI entry point
    EmailSlicer.java       # Core parsing logic
    EmailSliceResult.java  # Immutable result record (username, domain)
  test/java/com/emailslicer/
    EmailSlicerTest.java   # Unit tests for parsing logic
    MainSmokeTest.java     # CLI subprocess smoke tests
```

## License

Distributed under the MIT License. See `LICENSE.md` for more information.
