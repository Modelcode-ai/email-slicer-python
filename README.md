# Email Slicer — Java 17

Email Slicer is a simple CLI tool that takes an email address as input and returns the username and domain as output. It uses Java string operations to parse email addresses.

This is a Java 17 migration of the original [Python Email Slicer](https://github.com/Modelcode-ai/email-slicer-python).

## Prerequisites

- **Java 17 JDK** installed and available on `PATH`
- No need for a pre-installed Maven — the included Maven Wrapper handles it

## Build

```bash
./mvnw clean package
```

On Windows:

```cmd
mvnw.cmd clean package
```

## Run

After building, run the application with:

```bash
java -jar target/emailslicer-user-1.0.0.jar
```

Or via Maven:

```bash
./mvnw exec:java -Dexec.mainClass="com.emailslicer.Main" -q
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

Here we got **`avimax37`** as username and **`gmail.com`** as domain.

## Run Tests

```bash
./mvnw test
```

The test suite covers:
- Valid email parsing (happy path)
- Multiple `@` signs (splits at first `@`)
- Missing `@` sign (error handling)
- Empty and whitespace-only input
- Edge cases (`@domain.com`, `user@`)
- CLI integration test (simulated stdin/stdout)

## Project Structure

```
emailslicer-user/
├── pom.xml
├── mvnw / mvnw.cmd
├── .mvn/wrapper/
├── README.md
└── src/
    ├── main/java/com/emailslicer/
    │   ├── EmailSlicer.java    # Core parsing logic
    │   └── Main.java           # CLI entry point
    └── test/java/com/emailslicer/
        └── EmailSlicerTest.java
```

## License

Distributed under the MIT License. See the original repository for more information.
