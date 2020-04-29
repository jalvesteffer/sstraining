package com.ss.week3day2.maventest;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HelloWorld {

	public static void main(String[] args) {
		HelloWorld helloWorld = new HelloWorld();

		// ask for number of times to print 'Hello World!' and then print it to console
		// the specified number of times
		int userInt = helloWorld.askForInt("Enter the number of times you want to see 'Hello World!': ");
		helloWorld.printHelloWorld(userInt);

	}

	/**
	 * This method prints 'HelloWorld' the number of times a user specifies
	 * 
	 * @param numOfTimes how many times you want to print 'Hello World!'
	 * @return 0 for success; -1 for less than 1 time error; 1 for more than 100
	 *         times error
	 */
	public int printHelloWorld(int numOfTimes) {

		if (numOfTimes < 1) {
			System.out.println("Sorry, can display something less than 1 time");
			return -1;
		} else if (numOfTimes > 100) {
			System.out.println("Sorry, your asking way too much of me!");
			return 1;
		}
		for (int n = 0; n < numOfTimes; n++) {
			System.out.println("Hello World!");
		}
		return 0;
	}

	// This method asks the user in the console for a positive number (int)
	// It returns the input as a int if it is valid
	public int askForInt(String prompt) {
		boolean isValid = false; // flag for error-checking input
		int userVal = -1; // user input value
		Scanner scan; // for getting user input

		// display input prompt
		System.out.print(prompt + " ");

		// keep asking for user input until a valid int value is received
		while (!isValid) {
			try {
				scan = new Scanner(System.in); // create new scanner object

				// read user input as a int
				userVal = scan.nextInt();

				// make sure the number is non-negative
				if (userVal < 0) {
					System.out.println();
					System.out.println("ERROR: Number cannot be negative.");
					System.out.print(prompt + " ");
				} else {
					isValid = true; // input is valid
				}
			} catch (InputMismatchException e) {
				// handle exceptions for non-valid input
				System.out.println();
				System.out.println("ERROR: Not a positive integer.");
				System.out.print(prompt + " ");
				userVal = -1;
			}
		} // end while

		return userVal;
	}
}
