# Email Slicer

Email Slicer is a simple command-line tool that takes an email address as input and returns the username and domain as separate components. This Java 17 implementation provides robust validation and a clean, testable architecture.

## Built With

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

## Prerequisites

- **Java 17+** - Java Development Kit (JDK) version 17 or higher
- **Maven 3.9+** - Apache Maven for building the project

## Building the Project

To build the Email Slicer application, run:

```bash
mvn clean package
```

This command will:
- Compile the Java source code
- Run all unit tests
- Create an executable JAR file at `target/emailslicer.jar`

## Running the Application

After building, run the application with:

```bash
java -jar target/emailslicer.jar
```

The application will prompt you to enter an email address interactively.

## Example Usage

**Input:**
```
Enter your email address: avimax37@gmail.com
```

**Output:**
```
Username: avimax37
Domain: gmail.com
```

Here we get **`avimax37`** as the username and **`gmail.com`** as the domain.

## Validation Rules

The Email Slicer performs structural validation to ensure the email address is properly formatted:

- **Must contain exactly one `@` symbol** - No more, no less
- **Username must not be empty** - The part before `@` must have at least one character
- **Domain must not be empty** - The part after `@` must have at least one character
- **Whitespace is trimmed** - Leading and trailing spaces are automatically removed

### Invalid Email Examples

The application will display clear error messages for invalid input:

```
Enter your email address: invalidemail
Invalid email: Email must contain exactly one '@' symbol.
```

```
Enter your email address: @gmail.com
Invalid email: Username (part before '@') must not be empty.
```

```
Enter your email address: user@
Invalid email: Domain (part after '@') must not be empty.
```

```
Enter your email address: user@@example.com
Invalid email: Email must contain exactly one '@' symbol.
```

## Project Structure

```
emailslicer/
├── src/
│   ├── main/java/com/emailslicer/
│   │   ├── Main.java                   # CLI entry point
│   │   ├── EmailSlicerService.java     # Email parsing logic
│   │   ├── EmailComponents.java        # Data model (Java record)
│   │   └── InvalidEmailException.java  # Custom exception
│   └── test/java/com/emailslicer/
│       └── EmailSlicerServiceTest.java # Unit tests
├── pom.xml                              # Maven configuration
├── README.md                            # This file
└── LICENSE.md                           # MIT License
```

## Running Tests

To run the test suite:

```bash
mvn test
```

The tests cover various scenarios including valid emails, invalid formats, edge cases, and special character handling.

## Code Style

To check code style compliance:

```bash
mvn checkstyle:check
```

The project follows Google Java Style conventions enforced via Maven Checkstyle Plugin.

## License

Distributed under the MIT License. See `LICENSE.md` for more information.

## Contact

Avinaba Bera

[![Twitter](https://img.shields.io/badge/Twitter-%231DA1F2.svg?style=for-the-badge&logo=Twitter&logoColor=white)](https://twitter.com/IainSchneider)
[![LinkedIn](https://img.shields.io/badge/linkedin-%230077B5.svg?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/avinaba-bera)

## Project Links

[![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)](https://github.com/avimax37/email-slicer-python)
