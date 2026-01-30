<!-- TITLE -->

# Email Slicer Using Java 17

Email Slicer is a simple tool where an email address is provided as input and the application returns the username and domain of the email address as output. This Java implementation uses string parsing operations with comprehensive validation to parse email addresses.

<!-- BUILT WITH -->

## Built With

![Java][java-shield]
![Maven][maven-shield]

<!-- EXAMPLE -->

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

<!-- INSTALLATION -->

## Prerequisites

To build and run this application, you need:

### Java Development Kit (JDK) 17

Use the link to download Java 17:

[![Java][java-shield]][java-url]

### Apache Maven 3.8+

Use the link to download Maven:

[![Maven][maven-shield]][maven-url]

### IDE (Optional)

Use one of these IDEs for development:

[![IntelliJ IDEA][intellij-shield]][intellij-url]

[![Visual Studio Code][visual-studio-code-shield]][visual-studio-code-url]

<!-- USAGE -->

## Usage

### Building the Application

Open Command Prompt, PowerShell, or a terminal and navigate to the project directory:

```bash
# Navigate to project directory
cd <project-path>

# Build the application using Maven
mvn clean package
```

This will compile the source code, run all tests, and create an executable JAR file in the `target/` directory.

### Running the Application

After building, run the application with:

```bash
# Run the executable JAR
java -jar target/emailslicer-1.0.0-SNAPSHOT.jar
```

Alternatively, you can run directly from your IDE:
- In IntelliJ IDEA or VS Code: Right-click on `EmailSlicer.java` → Run

<!-- CODE -->

## Code

The application consists of three main components:

### 1. User Input (EmailSlicer.java)

First, we prompt the user to enter their email address:

```java
System.out.println("Please enter your Email Id:");
try (Scanner scanner = new Scanner(System.in)) {
    String input = scanner.nextLine();
```

Here we use the **`Scanner`** class to read input from the console. The **`nextLine()`** method reads the entire line as a string. We use try-with-resources to ensure the scanner is properly closed.

### 2. Parsing and Validation (EmailParser.java)

The input is first trimmed to remove any leading or trailing whitespace:

```java
String trimmed = email.strip();
```

The **`strip()`** method removes whitespace from both sides of the string, ensuring we only have the email address without unwanted spaces.

Next, we validate the email address with comprehensive checks:

```java
int atIndex = trimmed.indexOf('@');
if (atIndex == -1) {
    throw new IllegalArgumentException("Invalid email: missing '@' symbol");
}
```

We verify that the email contains exactly one **`@`** symbol. The **`indexOf()`** method returns the position of the first occurrence, or -1 if not found. We also check for multiple **`@`** symbols, empty username, empty domain, and verify the domain contains at least one dot.

Then we extract the username and domain using string slicing:

```java
String username = trimmed.substring(0, atIndex);
String domain = trimmed.substring(atIndex + 1);
```

The **`substring()`** method extracts parts of the string. The first call extracts from index 0 to the **`@`** symbol (exclusive), and the second extracts from after the **`@`** to the end.

### 3. Result Storage (EmailComponents.java)

The parsed components are returned as an immutable record:

```java
public record EmailComponents(String username, String domain) { }
```

Java 17 **records** provide a concise way to create immutable data classes. The record automatically generates constructor, accessors (`.username()` and `.domain()`), equals, hashCode, and toString methods.

### 4. Output

Finally, we print the username and domain:

```java
EmailComponents components = parser.parse(input);
System.out.println("Your username is: " + components.username());
System.out.println("Your domain is: " + components.domain());
```

If validation fails, we catch the exception and display a friendly error message:

```java
catch (IllegalArgumentException ex) {
    System.out.println("Please enter a valid Email Id.");
    System.exit(1);
}
```

<!-- LOGIC -->

## Logic

Let's consider the input is **`avimax37@gmail.com`**.

When we execute **`trimmed.indexOf('@')`**, our **`indexOf()`** method returns **`8`** because the **`@`** symbol is located at index **8** (counting from 0).

Now when we call **`trimmed.substring(0, 8)`**, it extracts characters from index 0 up to (but not including) index 8, giving us **`avimax37`** which is stored in **`username`**.

For the domain, **`trimmed.substring(8 + 1)`** extracts from index 9 to the end of the string, giving us **`gmail.com`** which is stored in **`domain`**.

### Validation Features

This Java implementation includes enhanced validation beyond simple **`@`** checking:

- **Null and blank checking**: Ensures input is not null or empty
- **Single @ requirement**: Verifies exactly one **`@`** symbol exists
- **Non-empty username**: Ensures there's content before **`@`**
- **Non-empty domain**: Ensures there's content after **`@`**
- **Domain structure**: Verifies the domain contains at least one dot (e.g., `.com`, `.org`)

<!-- PROJECT STRUCTURE -->

## Project Structure

The project follows standard Maven conventions:

```
emailslicer/
├── pom.xml                                    # Maven configuration
├── README.md                                  # This file
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── modelcode/
│   │               └── emailslicer/
│   │                   ├── EmailSlicer.java      # CLI entry point
│   │                   ├── EmailParser.java      # Parsing and validation logic
│   │                   └── EmailComponents.java  # Immutable data record
│   └── test/
│       └── java/
│           └── com/
│               └── modelcode/
│                   └── emailslicer/
│                       └── EmailParserTest.java  # JUnit 5 unit tests
└── target/                                    # Build output (generated)
```

<!-- LICENSE -->

## License

Distributed under the MIT License. See **`LICENSE.md`** for more information.

<!-- CONTACT -->

## Contact

Original Python Version by Avinaba Bera

[![Twitter][twitter-shield]][twitter-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- PROJECT LINKS -->

## Project Links

Original Python Project:
[![GitHub][github-shield]][github-url]

<!-- MARKDOWNS -->

[java-shield]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[java-url]: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

[maven-shield]: https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white
[maven-url]: https://maven.apache.org/download.cgi

[intellij-shield]: https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white
[intellij-url]: https://www.jetbrains.com/idea/download/

[visual-studio-code-shield]: https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white
[visual-studio-code-url]: https://code.visualstudio.com/download

[twitter-shield]: https://img.shields.io/badge/Twitter-%231DA1F2.svg?style=for-the-badge&logo=Twitter&logoColor=white
[twitter-url]: https://twitter.com/IainSchneider

[linkedin-shield]: https://img.shields.io/badge/linkedin-%230077B5.svg?style=for-the-badge&logo=linkedin&logoColor=white
[linkedin-url]: https://www.linkedin.com/in/avinaba-bera

[github-shield]: https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white
[github-url]: https://github.com/avimax37/email-slicer-python
