package com.smoothstack.training.wk2.day2.assignment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommaSepString {

	public static void main(String[] args) {
		// Question 2 solution
		System.out.println(commaSeparatedList());

	}

	public static String commaSeparatedList() {
		System.out.println("Comma-Separated List");
		System.out.println();

		Integer intArray[] = { 5, 7, 22, 13, 55, 3, 17, 18, 28, 25, 30 };
		List<Integer> intList = Arrays.asList(intArray);

		Stream<String> evenList = intList.stream().filter(x -> x % 2 == 0).map(x -> "e".concat(x.toString()));
		Stream<String> oddList = intList.stream().filter(x -> x % 2 == 1).map(x -> "o".concat(x.toString()));
		List<String> finalList = Stream.concat(evenList, oddList).collect(Collectors.toList());

		return String.join(",", finalList);
	}

}
