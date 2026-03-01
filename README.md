# Email Slicer

Email Slicer is a simple CLI tool that takes an email address as input and returns the username and domain as output. It uses string slicing operations to parse email addresses.

This is a Java 17 port of the original [Python Email Slicer](https://github.com/Modelcode-ai/email-slicer-python).

## Built With

- Java 17 LTS
- Maven (with Maven Wrapper for self-contained builds)
- JUnit 5 (for testing)

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

Here we got **`avimax37`** as username and **`gmail.com`** as domain.

## Prerequisites

- Java 17 JDK installed and available on `PATH`

No pre-installed Maven is required — the project includes a Maven Wrapper.

## Build

```bash
./mvnw clean package
```

This produces the runnable JAR at `target/emailslicer-user-1.0.0.jar`.

## Run

```bash
java -jar target/emailslicer-user-1.0.0.jar
```

The application will prompt you to enter an email address and then display the username and domain.

## Test

```bash
./mvnw test
```

Runs all JUnit 5 tests including:
- **EmailSlicerTest** — unit tests for email parsing and validation logic
- **MainCliTest** — CLI behavior tests verifying prompt text and output format

## License

Distributed under the MIT License. See **`LICENSE.md`** for more information.
