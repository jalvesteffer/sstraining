package com.smoothstack.training.wk1day2;

import java.util.InputMismatchException;
import java.util.Scanner;

/*
 *  This class assists in getting a Double-type value from the user
 *  terminal
 */
public class UserInput {
		
	// This method asks the user in the console for a positive number (double)
	// It returns the input as a double if it is valid
	public double askForDouble(String prompt)	{
		boolean isValid = false;		// flag for error-checking input
		double	userVal = -1.0;			// user input value
		Scanner scan;					// for getting user input 
		
		// display input prompt
		System.out.print(prompt + " ");

		// keep asking for user input until a valid double value is received
		while ( ! isValid )	{
			try	{
				scan = new Scanner(System.in);	// create new scanner object
				
				// read user input as a Double
				userVal = scan.nextDouble();
				
				// make sure the number is positive and non-zero
				if (userVal <= 0.0) {
					System.out.println();
					System.out.println("ERROR: Number must be positive.");
					System.out.print(prompt + " ");
				}
				else	{
					isValid = true;		// input is valid
				}
			}
			catch (InputMismatchException e)	{
				// handle exceptions for non-valid input
				System.out.println();
				System.out.println("ERROR: Not a positive number.");
				System.out.print(prompt + " ");
				userVal = -1;
			}
		} // end while
				
		return userVal;
	}
}
