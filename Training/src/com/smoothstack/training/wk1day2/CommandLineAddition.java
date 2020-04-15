package com.smoothstack.training.wk1day2;

/*
 * 	this class receives arguments from the command line and adds them up when possible
 */
public class CommandLineAddition {

	public static void main(String[] args) {
		
		double sum = 0.0;		//	holds current sum of argument values
		
		// display a message and exit if there are no command line arguments
		if (args.length == 0)	{
			System.out.println("There are currently no command line arguments.");
			System.out.println("Add some, then re-run this program.");
			return;
		}
		
		// loops through all arguments and adds them.  Non-numbers are ignored
		for (String arg : args)	{
			try	{
				// read value and add to sum
				sum += Double.parseDouble(arg);
				System.out.println("Argument Read: " + arg);
			}
			catch (NumberFormatException e)	{
				// ignore non-numbers
				System.out.println("Argument Ignored: " + arg + " (Not a Number)");
			}
		}
		
		// print sum of all command line values
		System.out.println();
		System.out.println("The sum of all command line values is: " + sum);
	}

}
