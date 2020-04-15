package com.smoothstack.training.wk1day2;

import java.util.ArrayList;

/*
 *  This class can generate a 2D array, and then find its maximum
 *  value as well as the location where that value occurs
 */
public class ArrayMax {
	
	// 2D Array variable
	private ArrayList< ArrayList<Integer> > twoDList;
	
	// define constants for 2D array size used
	final int rows = 5;
	final int cols = 5;

	public static void main(String[] args) {
		
		// Create a new randomly generated 2D array
		ArrayMax Arr = new ArrayMax();
		Arr.createRandArray();
		
		// Report the maximum value and where it occurs in the console
		Arr.reportMax();
	}
	
	
	// constructor creates new 2d ArrayList object
	public ArrayMax()	{
		twoDList = new ArrayList< ArrayList<Integer> >();
	}
	
	
	/*
	 *  This method creates a new randomly generated 2D array based
	 *  on the rows and cols size constants.  It is filled with random
	 *  integers from 1-100
	 */
	public void createRandArray()	{
		
		// create the 2D array
		for (int x = 0; x < rows; x++)	{
			// creates a row
			twoDList.add(new ArrayList<Integer>());
			
			// fill that row with random integers of 1-100
			for (int y = 0; y < cols; y++)	{
				twoDList.get(x).add((int)(Math.random()*100 + 1));
			}
			
		}
		
		// display the generated 2D array to console
		System.out.println("Here is the randomly-generated 2D array:");
		System.out.println(twoDList);
	}
	
	
	/*
	 *  Given this object's 2D Array instance variable, this method
	 *  iterates through the array and finds the value and location
	 *  of its maximum value.  The results are reported to the console
	 */
	public void reportMax()	{
		
		int currVal = -1;		// holds currently value being read
		int currMaxVal = -1;	// the max value so far
		int currMaxX = -1;		// the row number of the max value so far
		int currMaxY = -1;		// the column number of the max value so far
		
		// make sure the 2D array has been generated; otherwise, exit
		if (twoDList.size() == 0)	{
			System.out.println("ERROR: The 2D array has not yet been created");
			return;
		}
		
		// iterate through each row of 2D array
		for (int x = 0; x < rows; x++)	{
			
			// for each row, iterate through all columns and remember the
			// max value and its location encountered so far
			for (int y = 0; y < cols; y++)	{
				// get new value to compare to the current max value
				currVal = twoDList.get(x).get(y);
				
				// if the current value is greater then the current maximum
				// value, the current value is the new maximum value.
				// also, remember its location in the array
				if (currVal > currMaxVal)	{
					currMaxVal = currVal;
					currMaxX = x;
					currMaxY = y;					
				}
				
			}			
		}
		
		// report to the maximum value and its location in the array 
		// to the console
		System.out.println();
		System.out.println("The Max Number is " + currMaxVal + ".");
		System.out.println("It occurs at row " + currMaxX + ", column " + currMaxY + 
				" (zero-based index).");
	}

}
