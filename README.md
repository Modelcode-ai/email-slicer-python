<!-- TITLE -->

# Email Slicer Using Python

Email Slicer is a simple tool where the email address is provided as an input and the application returns the username and the domain of the email address as an output. It makes use of the slicing operation in Python.

<!-- BUILT WITH -->

## Built With

![Python][python-shield]


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

## Installation

Use the link to download Python.

[![Python][python-shield]][python-url]

Use the link to download PyCharm.

[![PyCharm][pycharm-shield]][pycharm-url]

Use the link to download Visual Studio Code.

[![Visual Studio Code][visual studio code-shield]][visual studio code-url]

<!-- USAGE -->

## Usage

Open Command Prompt or Windows PowerShell to start.

```python
# get into python mode
python

# create a python script
hello.py
print("Hello World")

# go to the file path
cd <file path>

# run the script
python hello.py
```

VS Code or PyCharm can also be used.

<!-- CODE -->

## Code

So at first we are going to ask the user to enter the email that is to be sliced.

```python
print("Please enter your Email Id:")
email = input().strip()
```

Here, as usual, we are making use of the **`input()`** function to get the input from the user in form of a string. We will store this input string in **`email`** variable.

Also we are making use of the **`strip()`** function. **`strip()`** function will remove any additional & unwanted spacing on both sides of the input string. So that we can make sure that we have only the email in the input and not any unwanted spaces.

```python
if email.find("@") != -1:
```

Here we are checking if the input string contains **`@`** or not. If it contains **`@`**, then it is considered as a valid email id and we move to the next step, which is slicing the email.

```python
username = email[:email.index("@")]
domain = email[email.index("@") + 1:]
```

Now, we print the **`username`** and **`domain`** to show the result.

```python
print("Your username is: ", username)
print("Your domain is: ", domain)
```

If the input string does not contain **`@`**, then it is considered as an invalid email id and we get out of the loop with a warning message.

```python
else:
     print("Please enter a valid Email Id.")
```

<!-- LOGIC -->

## Logic

Let's consider the input is **`avimax37@gmail.com`**, so when we write **`email[email.index("@"):]`**, our **`index()`** function will interpret it as **`email[:8]`** as our **`@`** is located at index **8**. Now **`email[:8]`** knows that **`@`** is located at index **8**, so now it will keep the part before index **8** and discard the rest. It will store the stripped value in **`username`**.

This exact same process is followed for **`domain`** also.

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

[python-shield]: https://img.shields.io/badge/python-3670A0?style=for-the-badge&logo=python&logoColor=ffdd54
[python-url]: https://www.python.org/downloads

[pycharm-shield]: https://img.shields.io/badge/pycharm-143?style=for-the-badge&logo=pycharm&logoColor=black&color=black&labelColor=green
[pycharm-url]: https://www.jetbrains.com/pycharm/download/#section=windows

[visual studio code-shield]: https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white
[visual studio code-url]: https://code.visualstudio.com/download

[twitter-shield]: https://img.shields.io/badge/Twitter-%231DA1F2.svg?style=for-the-badge&logo=Twitter&logoColor=white
[twitter-url]: https://twitter.com/IainSchneider

[linkedin-shield]: https://img.shields.io/badge/linkedin-%230077B5.svg?style=for-the-badge&logo=linkedin&logoColor=white
[linkedin-url]: https://www.linkedin.com/in/avinaba-bera

[github-shield]: https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white
[github-url]: https://github.com/avimax37/email-slicer-python
