package com.smoothstack.training.wk1day2;

/*
 *  This class represents a circle shape and can perform an area
 *  calculation or display the area formula to the user
 */
public class Circle implements Shape {

	/*
	 *  This method calculates the area of a circle from user
	 *  input, and displays the results in the console
	 */
	@Override
	public void calculateArea() {
		double radius;		// radius of circle
		double area;		// area of circle
		
		// create object to receive user input from console
		UserInput usr = new UserInput();
		
		// get user value for radius of circle
		radius 	= usr.askForDouble("Enter the radius of the circle:");
		
		// perform shape area calculation
		area = Math.PI * radius * radius;
		
		// display the results
		System.out.println("The area of the circle is: " + area);
	}

	
	/*
	 *  This method displays the area formula for a circle to the console
	 */
	@Override
	public void display() {
		System.out.println("The area formula for a circle: PI * radius * radius");
	}

}
