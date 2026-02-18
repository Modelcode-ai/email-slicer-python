# Email Slicer

A simple CLI tool that parses email addresses into username and domain components.

Migrated from Python to Java 17 as an idiomatic Java implementation with proper class structure, input validation, and comprehensive testing.

## Requirements

- Java 17 or later
- Maven 3.9.x or later

## Building

```bash
mvn clean compile
```

## Testing

```bash
mvn clean test
```

## Packaging

```bash
mvn clean package
```

This creates an executable JAR at `target/emailslicer-dkasargod-1.0.0-jar-with-dependencies.jar`

## Running

```bash
java -jar target/emailslicer-dkasargod-1.0.0-jar-with-dependencies.jar
```

## Project Structure

```
emailslicer-dkasargod/
├── pom.xml                          # Maven build configuration
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── emailslicer/     # Application code
│   └── test/
│       └── java/
│           └── com/
│               └── emailslicer/     # Test code
```
