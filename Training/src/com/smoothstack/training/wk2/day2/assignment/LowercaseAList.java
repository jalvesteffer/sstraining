package com.smoothstack.training.wk2.day2.assignment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LowercaseAList {

	public static void main(String[] args) {
		// Question 3 solution
		System.out.println(lowercaseAList());

	}

	public static List<String> lowercaseAList() {

		System.out.println("Lowercase 'a' List");
		System.out.println();

		String strArray[] = { "apple", "animal", "tree", "aim", "doghouse", "Anthony", "Randy", "ATE", "Alan", "Bob",
				"red", "ace" };
		List<String> strList = Arrays.asList(strArray);

		Stream<String> filteredList = strList.stream().filter(x -> x.charAt(0) == 'a' && x.length() == 3);
		List<String> finalList = filteredList.collect(Collectors.toList());

		return finalList;

	}

}
