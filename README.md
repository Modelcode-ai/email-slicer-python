# Email Slicer - Java 17

Email Slicer is a simple tool that takes an email address as input and returns the username and domain as output. It uses string slicing operations to parse email addresses.

This is a Java 17 modernization of the original [Python Email Slicer](https://github.com/Modelcode-ai/email-slicer-python) by Avinaba Bera.

## Built With

- Java 17 (LTS)
- Apache Maven

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

- Java Development Kit (JDK) 17 or later
- Apache Maven 3.8+

## Building

Build the project using Maven:

```bash
mvn clean package
```

This creates an executable JAR file at `target/email-slicer-1.0.0-SNAPSHOT.jar`.

## Running

After building, run the application using:

```bash
java -jar target/email-slicer-1.0.0-SNAPSHOT.jar
```

Or using the classpath:

```bash
java -cp target/email-slicer-1.0.0-SNAPSHOT.jar com.emailslicer.Main
```

## Project Structure

```
email-slicer/
├── pom.xml
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/emailslicer/
│   │           ├── EmailSlicer.java      # Core email parsing logic
│   │           └── Main.java             # CLI entry point
│   └── test/
│       └── java/
│           └── com/emailslicer/
│               └── EmailSlicerTest.java  # JUnit 5 tests
├── README.md
└── LICENSE
```

## How It Works

The application:

1. Prompts the user to enter an email address
2. Trims leading and trailing whitespace from the input
3. Validates that the input contains an `@` symbol with non-empty username and domain parts
4. Extracts the username (before `@`) and domain (after `@`) using the first `@` occurrence
5. Displays the results or an error message for invalid input

## Running Tests

Run the unit tests using:

```bash
mvn test
```

## License

Distributed under the MIT License. See `LICENSE` for more information.

Original Python implementation by Avinaba Bera.
