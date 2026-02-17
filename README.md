<!-- TITLE -->

# Email Slicer Using Java

Email Slicer is a simple tool where the email address is provided as an input and the application returns the username and the domain of the email address as an output. It makes use of string operations in Java to parse email addresses.

<!-- BUILT WITH -->

## Built With

![Java][java-shield]

<!-- EXAMPLE -->

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

<!-- PREREQUISITES -->

## Prerequisites

- **Java**: JDK 17 installed and available on `PATH`.
- **Git**: To clone the repository.
- **Maven**: Not required globally — the project includes Maven Wrapper.

<!-- INSTALLATION -->

## Installation

Clone the repository:

```bash
git clone <repository-url>
cd emailslicer-dkasargod
```

<!-- USAGE -->

## Usage

### Build and run tests

```bash
./mvnw clean test
```

### Package the application

```bash
./mvnw clean package
```

### Run the application

```bash
java -jar target/emailslicer-1.0.0.jar
```

The program will prompt you to enter an email address and then display the parsed username and domain.

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

[![GitHub][github-shield]][github-url]

<!-- MARKDOWNS -->

[java-shield]: https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white

[twitter-shield]: https://img.shields.io/badge/Twitter-%231DA1F2.svg?style=for-the-badge&logo=Twitter&logoColor=white
[twitter-url]: https://twitter.com/IainSchneider

[linkedin-shield]: https://img.shields.io/badge/linkedin-%230077B5.svg?style=for-the-badge&logo=linkedin&logoColor=white
[linkedin-url]: https://www.linkedin.com/in/avinaba-bera

[github-shield]: https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white
[github-url]: https://github.com/avimax37/email-slicer-python
