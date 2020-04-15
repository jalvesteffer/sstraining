package com.smoothstack.training.wk1day2;

/*
 *  This class represents a Rectangle shape and can perform an area
 *  calculation or display the area formula to the user
 */
public class Rectangle implements Shape {

	/*
	 *  This method calculates the area of a Rectangle from user
	 *  input, and displays the results in the console
	 */
	@Override
	public void calculateArea() {
		double length;		// length of rectangle
		double width;		// width of rectangle
		double area;		// area of rectangle
		
		// create object to receive user input from console
		UserInput usr = new UserInput();
		
		// get user values for length and width of rectangle
		length 	= usr.askForDouble("Enter the length of the rectangle:");
		width 	= usr.askForDouble("Enter the width of the rectangle:");
		
		// perform shape area calculation
		area = length * width;
		
		// display the results
		System.out.println("The area of the rectagle is: " + area);
	}

	/*
	 *  This method displays the area formula for a rectangle to the console
	 */
	@Override
	public void display() {
		System.out.println("The area formula for a rectangle: length * width");
	}
}
