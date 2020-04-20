package com.smoothstack.training.wk1day3.practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompareMovies {

	public static void main(String[] args) {
		Movie m1 = new Movie(2010, "Avengers", 9.8f);
		Movie m2 = new Movie(2012, "Avengers - AU ", 7.8f);
		Movie m3 = new Movie(2014, "Avengers - IW", 9.6f);
		Movie m4 = new Movie(2018, "Avengers - EG", 8.8f);
		
		List<Movie> avengers = new ArrayList<>();
		avengers.add(m2);
		avengers.add(m4);
		avengers.add(m1);
		avengers.add(m3);
		
		System.out.println("Before sort:");
		for(Movie m : avengers){
			System.out.println("Name: "+m.getName()+" year: "+m.getYear()+" with rating: "+m.getRating());
		}
		
		Collections.sort(avengers);
		System.out.println("After sort:");
		for(Movie m: avengers){
			System.out.println("Name: "+m.getName()+" year: "+m.getYear()+" with rating: "+m.getRating());
		}
		
		System.out.println("After sort by Rating:");
		RatingComparator ratingComparator = new RatingComparator();
		Collections.sort(avengers, ratingComparator);
		for(Movie m: avengers){
			System.out.println("Name: "+m.getName()+" year: "+m.getYear()+" with rating: "+m.getRating());
		}
		
	}

}
