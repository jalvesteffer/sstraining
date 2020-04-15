package com.smoothstack.training.wk1day2;

/*
 *  This class represents a triangle shape and can perform an area
 *  calculation or display the area formula to the user
 */
public class Triangle implements Shape {

	/*
	 *  This method calculates the area of a triangle from user
	 *  input, and displays the results in the console
	 */
	@Override
	public void calculateArea() {
		double base;		// base of triangle
		double height;		// height of triangle
		double area;		// area of triangle
		
		// create object to receive user input from console
		UserInput usr = new UserInput();
		
		// get user values for base and height of triangle
		base 	= usr.askForDouble("Enter the base of the triangle:");
		height 	= usr.askForDouble("Enter the height of the triangle:");
		
		// perform shape area calculation
		area = (base * height) / 2;
		
		// display the results
		System.out.println("The area of the triangle is: " + area);
	}

	/*
	 *  This method displays the area formula for a triangle to the console
	 */
	@Override
	public void display() {
		System.out.println("The area formula for a triangle: (base * height) / 2");
	}

}
