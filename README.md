# Email Slicer — Java 17

Email Slicer is a simple tool where an email address is provided as input and the application returns the username and the domain of the email address as output. Migrated from the original Python implementation to idiomatic Java 17.

## Built With

- Java 17 (OpenJDK)
- Maven 3.9+ (via Maven Wrapper)
- JUnit 5 (testing)

## Prerequisites

- Java 17 or later installed (`java -version` to check)
- No Maven installation required — the Maven Wrapper (`mvnw`) is included

## Build

```bash
./mvnw package
```

## Run

```bash
java -jar target/email-slicer.jar
```

## Test

```bash
./mvnw test
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

## Project Structure

```
├── pom.xml                                    # Maven build configuration
├── mvnw / mvnw.cmd                            # Maven Wrapper scripts
├── src/main/java/com/emailslicer/
│   ├── Main.java                              # Console I/O entry point
│   ├── EmailSlicer.java                       # Pure parsing logic
│   ├── EmailSlicerResult.java                 # Record holding username + domain
│   └── Messages.java                          # User-facing string constants
└── src/test/java/com/emailslicer/
    └── EmailSlicerTest.java                   # JUnit 5 unit tests
```

## License

Distributed under the MIT License. See **`LICENSE.md`** for more information.
