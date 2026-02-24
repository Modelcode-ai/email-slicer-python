# Email Slicer - Java 17

Email Slicer is a simple command-line tool that takes an email address as input and returns the username and domain as output. This is a Java 17 migration of the original [Python Email Slicer](https://github.com/Modelcode-ai/email-slicer-python).

## Built With

- Java 17 (LTS)
- Maven
- JUnit 5

## Example

Input:
```
Please enter your Email Id:
avimax37@gmail.com
```

Output:
```
Your username is: avimax37
Your domain is: gmail.com
```

Here we got **`avimax37`** as username and **`gmail.com`** as domain.

## Project Structure

```
src/
  main/java/com/emailslicer/
    Main.java             # CLI entry point (stdin/stdout)
    EmailSlicer.java      # Core parsing & validation logic
  test/java/com/emailslicer/
    EmailSlicerTest.java   # JUnit 5 unit tests
pom.xml                    # Maven build configuration
```

## Prerequisites

- Java 17 JDK
- Maven 3.8+

## Build

Compile the project:
```bash
mvn compile
```

Run tests:
```bash
mvn test
```

Package as a JAR:
```bash
mvn package
```

## Usage

Run the packaged JAR:
```bash
echo "avimax37@gmail.com" | java -jar target/emailslicer-1.0.0.jar
```

Or run interactively:
```bash
java -jar target/emailslicer-1.0.0.jar
```
Then type an email address and press Enter.

### Behavior

- **Valid email**: Extracts and displays the username (before `@`) and domain (after `@`)
- **Invalid email** (no `@`): Prints `"Please enter a valid Email Id."` and exits with code `1`
- **Multiple `@` signs**: Splits on the first `@` (e.g., `user@sub@example.com` yields username `user`, domain `sub@example.com`)
- **Whitespace**: Leading and trailing whitespace is trimmed before parsing

## License

Distributed under the MIT License.
