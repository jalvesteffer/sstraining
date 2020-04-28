/**
 * 
 */
package com.ss.training.wk2.project;

import java.util.InputMismatchException;
import java.util.Scanner;

/*
 *  This class assists in getting a Double-type value from the user
 *  terminal
 */
/**
 * @author jalveste
 *
 */
/**
 * @author jalveste
 *
 */
public class UserInput {

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

	// This method asks the user in the console for a positive number (int)
	// It returns the input as a int if it is valid
	public int askForIntInRange(String prompt, int min, int max) {
		boolean isValid = false; // flag for error-checking input
		int userVal = -1; // user input value

		// keep asking for user input until a valid int value is received
		while (!isValid) {
			userVal = askForInt(prompt);
			if (userVal != -1 && (userVal < min || userVal > max)) {
				System.out.println("\nERROR: Input is not an integer between " + min + " and " + max + ".");
			} else {
				isValid = true;
			}
		} // end while

		return userVal;
	}

	// This method asks the user in the console for a positive number (double)
	// It returns the input as a double if it is valid
	public String askForString(String prompt) {
		boolean isValid = false; // flag for error-checking input
		String userVal = null; // user input value
		Scanner scan; // for getting user input

		// display input prompt
		System.out.print(prompt + " ");

		// keep asking for user input until a valid String value is received
		while (!isValid) {
			try {
				scan = new Scanner(System.in); // create new scanner object

				// read user input as a String
				userVal = scan.nextLine();

				// make sure the number is positive and non-zero
				if (userVal == null) {
					System.out.println();
					System.out.println("ERROR: null value.");
					System.out.print(prompt + " ");
				} else {
					isValid = true; // input is valid
				}
			} catch (Exception e) {
				// handle exceptions for non-valid input
				System.out.println();
				System.out.println("Input error occured.");
				System.out.print(prompt + " ");
				userVal = null;
			}
		} // end while

		return userVal;
	}

	/**
	 * This method validates an edited string and returns a decision in String form
	 * of how to handle it
	 * 
	 * @param editedStr new String value to validate
	 * @return "ok" proceed normally; "skip" do nothing; "quit" exit operation'
	 *         "error" something went wrong
	 */
	public String validateResponse(String editedStr) {
		if (!editedStr.isEmpty() && !"quit".equalsIgnoreCase(editedStr) && !"N/A".equalsIgnoreCase(editedStr)) {
			System.out.println("Changing this value to " + editedStr);
			return "ok";
		} else if ("N/A".equalsIgnoreCase(editedStr)) {
			System.out.println("Skipping this value");
			return "skip";
		} else if ("quit".equalsIgnoreCase(editedStr)) {
			System.out.println("Quitting update");
			return "quit";
		} else {
			System.out.println("Input error, exiting...");
			return "error";
		}
	}

}
