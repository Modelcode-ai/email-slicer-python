# Email Slicer — Java 17

Email Slicer is a simple tool that takes an email address as input and returns the username and domain as output. This is a Java 17 port of the [original Python Email Slicer](https://github.com/Modelcode-ai/email-slicer-python).

## Built With

- Java 17 LTS
- Maven
- JUnit 5 (testing)

## Prerequisites

- **Java Development Kit (JDK):** Version 17 or later
- **Maven:** Version 3.8 or later

## Build

```bash
mvn clean package
```

This produces an executable JAR at `target/email-slicer-java-1.0-SNAPSHOT.jar`.

## Usage

### Interactive Mode

Run the JAR without arguments to be prompted for an email address:

```bash
java -jar target/email-slicer-java-1.0-SNAPSHOT.jar
```

```
Enter your email: avimax37@gmail.com
Username: avimax37
Domain: gmail.com
```

### Non-Interactive Mode

Pass the email address as a command-line argument:

```bash
java -jar target/email-slicer-java-1.0-SNAPSHOT.jar user@example.com
```

```
Username: user
Domain: example.com
```

### Error Handling

Invalid input produces an error message on `stderr` and exits with code `1`:

```bash
java -jar target/email-slicer-java-1.0-SNAPSHOT.jar "not-an-email"
```

```
Invalid email: missing '@' symbol.
```

## Running Tests

```bash
mvn test
```

## Differences from the Python Version

This Java port includes the following intentional enhancements over the original Python implementation:

| Behavior | Python Version | Java Version |
|---|---|---|
| Multiple `@` symbols (e.g., `a@b@c.com`) | Accepted silently | Rejected with error |
| Empty username (e.g., `@example.com`) | Accepted silently | Rejected with error |
| Empty domain (e.g., `user@`) | Accepted silently | Rejected with error |
| Error output | Printed to `stdout` | Printed to `stderr` |
| Exit code on error | `0` | `1` |
| Whitespace trimming | Supported via `strip()` | Supported via `trim()` |

## API Usage

The core parsing logic is available as a reusable API independent of the CLI:

```java
import com.emailslicer.EmailSlicer;
import com.emailslicer.EmailParts;

EmailParts parts = EmailSlicer.parse("user@example.com");
System.out.println(parts.username()); // "user"
System.out.println(parts.domain());   // "example.com"
```

## License

Distributed under the MIT License. See `LICENSE.md` for more information.
