package com.smoothstack.training.wk1day2;

/*
 *  This class serves as a driver to test the shape class functionality
 */
public class ShapeDriver {

	public static void main(String[] args) {
		//	declare and create new Shape objects
		Rectangle 	rec = new Rectangle();
		Triangle 	tri = new Triangle();
		Circle 		cir = new Circle();
		
		// for each of the three Shape objects, display the area
		// formula and perform an area calculation in the console
		rec.display();
		rec.calculateArea();
		System.out.println();
		
		tri.display();
		tri.calculateArea();
		System.out.println();
		
		cir.display();
		cir.calculateArea();
		System.out.println();
	}

}
