package com.modelcode.emailslicer;

import java.util.Scanner;

public class EmailSlicer {

    public static void main(String[] args) {
        EmailParser parser = new EmailParser();

        System.out.println("Please enter your Email Id:");
        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();
            try {
                EmailComponents components = parser.parse(input);
                System.out.println("Your username is: " + components.username());
                System.out.println("Your domain is: " + components.domain());
            } catch (IllegalArgumentException ex) {
                System.out.println("Please enter a valid Email Id.");
                System.exit(1);
            }
        }
    }
}
