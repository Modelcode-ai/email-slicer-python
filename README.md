<!-- TITLE -->

# Email Slicer Using Java 17

Email Slicer is a simple tool where the email address is provided as an input and the application returns the username and the domain of the email address as an output. It uses Java string manipulation operations to parse email addresses.

This is a modernized Java 17 implementation of the original Python Email Slicer tool, featuring proper class structure, input validation, and comprehensive testing.

<!-- BUILT WITH -->

## Built With

![Java][java-shield]
![Maven][maven-shield]
![JUnit][junit-shield]

<!-- EXAMPLE -->

## Example

Input:
```
Enter your Email Id: avimax37@gmail.com
```

Output:
```
Your username is: avimax37
Your domain is: gmail.com
```

Here we got **`avimax37`** as username and **`gmail.com`** as domain.

<!-- INSTALLATION -->

## Installation

### Prerequisites

You need to have the following installed:

1. **Java 17 JDK** - Download from:
   - [Oracle JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
   - [OpenJDK 17](https://adoptium.net/temurin/releases/?version=17)

2. **Apache Maven 3.9+** - Download from:
   - [Apache Maven](https://maven.apache.org/download.cgi)

### Verify Installation

```bash
# Check Java version
java -version

# Check Maven version
mvn -version
```

<!-- USAGE -->

## Usage

### Building the Project

```bash
# Navigate to the project directory
cd email-slicer-python

# Clean and build the project
mvn clean install

# Run tests only
mvn test
```

### Running the Application

After building, you can run the application using:

```bash
# Run the executable JAR
java -jar target/emailslicer-1.0.0.jar
```

The application will prompt you for an email address and display the parsed username and domain.

<!-- CODE -->

## Code

The Java implementation is structured with clean separation of concerns:

### Core Components

**EmailComponents Record** (`EmailComponents.java`)

An immutable Java 17 record that holds the parsed email components:

```java
public record EmailComponents(String username, String domain) {
}
```

**EmailSlicer Class** (`EmailSlicer.java`)

The core parsing and validation logic:

```java
public class EmailSlicer {
    public EmailComponents slice(String email) throws InvalidEmailException {
        // Normalize input by trimming whitespace
        final String trimmedEmail = email.trim();

        // Validate non-empty input
        if (trimmedEmail.isEmpty()) {
            throw new InvalidEmailException("...");
        }

        // Validate exactly one '@'
        final int firstAt = trimmedEmail.indexOf('@');
        final int lastAt = trimmedEmail.lastIndexOf('@');

        if (firstAt == -1 || firstAt != lastAt) {
            throw new InvalidEmailException("...");
        }

        // Parse username and domain
        final String username = trimmedEmail.substring(0, firstAt);
        final String domain = trimmedEmail.substring(firstAt + 1);

        // Validate non-empty parts
        if (username.isEmpty() || domain.isEmpty()) {
            throw new InvalidEmailException("...");
        }

        return new EmailComponents(username, domain);
    }
}
```

**Main Class** (`Main.java`)

The CLI entry point that handles user interaction:

```java
public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    try {
        System.out.print("Enter your Email Id: ");
        String emailInput = scanner.nextLine();

        EmailSlicer slicer = new EmailSlicer();
        EmailComponents components = slicer.slice(emailInput);

        System.out.println("Your username is: " + components.username());
        System.out.println("Your domain is: " + components.domain());

    } catch (InvalidEmailException e) {
        System.err.println("Error: " + e.getMessage());
        System.exit(1);
    } finally {
        scanner.close();
    }
}
```

### Validation Rules

The Java implementation enforces stricter validation than the original Python version:

1. **Non-empty input** - The trimmed input must not be empty
2. **Exactly one '@'** - The email must contain exactly one '@' character
3. **Non-empty parts** - Both username and domain must be non-empty strings

<!-- LOGIC -->

## Logic

Let's consider the input is **`avimax37@gmail.com`**. The Java implementation uses string methods to parse the email:

1. **Trimming**: The input is normalized using `trim()` to remove leading/trailing whitespace (equivalent to Python's `strip()`).

2. **Finding '@'**: We use `indexOf('@')` to find the first occurrence and `lastIndexOf('@')` to find the last occurrence. If these are different or if '@' is not found, the email is invalid.

3. **Slicing**: We use `substring()` to extract the parts:
   - `substring(0, firstAt)` gets everything before the '@' → username
   - `substring(firstAt + 1)` gets everything after the '@' → domain

4. **Validation**: We check that both username and domain are non-empty strings.

For **`avimax37@gmail.com`**:
- `firstAt` = 8 (index of '@')
- `username` = `substring(0, 8)` → `"avimax37"`
- `domain` = `substring(9)` → `"gmail.com"`

<!-- ARCHITECTURE -->

## Architecture

The application follows a layered architecture:

```
com.modelcode.emailslicer/
├── Main.java                    (CLI layer - user interaction)
└── core/
    ├── EmailSlicer.java        (Core logic - parsing & validation)
    ├── EmailComponents.java    (Data model - immutable record)
    └── InvalidEmailException.java (Exception handling)
```

**Design Principles:**
- Single Responsibility: Each class has one clear purpose
- Separation of Concerns: CLI logic is separate from parsing logic
- Testability: Core logic has no I/O dependencies
- Immutability: Using Java 17 records for data structures

<!-- LICENSE -->

## License

Distributed under the MIT License. See **`LICENSE.md`** for more information.

<!-- CONTACT -->

## Contact

Avinaba Bera

[![Twitter][twitter-shield]][twitter-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- PROJECT LINKS -->

## Project Links

[![GitHub - Original Python][github-shield]][github-url]

<!-- MARKDOWNS -->

[java-shield]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[maven-shield]: https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white
[junit-shield]: https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white

[twitter-shield]: https://img.shields.io/badge/Twitter-%231DA1F2.svg?style=for-the-badge&logo=Twitter&logoColor=white
[twitter-url]: https://twitter.com/IainSchneider

[linkedin-shield]: https://img.shields.io/badge/linkedin-%230077B5.svg?style=for-the-badge&logo=linkedin&logoColor=white
[linkedin-url]: https://www.linkedin.com/in/avinaba-bera

[github-shield]: https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white
[github-url]: https://github.com/avimax37/email-slicer-python
