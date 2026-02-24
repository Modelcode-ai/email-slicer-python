# Email Slicer Using Java

Email Slicer is a simple tool where the email address is provided as an input and the application returns the username and the domain of the email address as an output. It makes use of string splitting operations in Java.

This is a Java 17 port of the original [Python Email Slicer](https://github.com/Modelcode-ai/email-slicer-python).

## Built With

- Java 17 LTS
- Maven

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

## Prerequisites

- **Java 17 JDK** installed and available on the `PATH`.
- **Maven** (3.8+ recommended) installed and available on the `PATH`.

## Build

```bash
mvn clean package
```

This produces `target/emailslicer-user-1.0.0.jar`.

## Run

```bash
java -jar target/emailslicer-user-1.0.0.jar
```

You will see the prompt `Please enter your Email Id:` — enter an email address and press Enter.

## Run Tests

```bash
mvn test
```

## Project Structure

```text
emailslicer-user/
├── pom.xml
├── src/
│   ├── main/java/com/emailslicer/
│   │   ├── Main.java            # CLI entry point; handles stdin/stdout
│   │   └── EmailSlicer.java     # Email parsing and validation logic
│   └── test/java/com/emailslicer/
│       └── EmailSlicerTest.java  # JUnit 5 tests for parsing and validation
├── LICENSE.md
└── README.md
```

## How It Works

The `EmailSlicer` class splits the email on the first `@` character:
- Characters before `@` become the **username**
- Characters after `@` become the **domain**

If the input does not contain `@`, or is empty/whitespace-only, it is treated as invalid and the tool displays an error message.

## License

Distributed under the MIT License. See **`LICENSE.md`** for more information.
