package com.smoothstack.training.wk2.day2.assignment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BasicLamdas {

	private static String someStrings[] = { "This is a repetitive way to write", "This",
			"This is a repetitive way to write a String", "This is", "This is a", "This is a repetitive way to",
			"This is a repetitive way", "This is a repetitive way to", "This is a repetitive",
			"This is a repetitive way to write a" };

	private static String animalStrings[] = { "cat", "camel", "eagle", "donkey", "mouse", "zebra", "whale", "dog",
			"elephant", "horse", "lion", "snake", "buffalo" };

	public interface EListInterface {
		public void makeList(List<String> list);
	}

	public static void main(String[] args) {

		// Question 1, Bullet 1 Solution
		lengthShortestToLongest();
		
		// Question 1, Bullet 2 Solution
		lengthLongestToShortest();
		
		// Question 1, Bullet 3 Solution
		alphabeticallyByFirstCharacter();
		
		// Question 1, Bullet 4 Solution
		eStringsFirst();
		
		// Question 1, Bullet 5 Solution
		System.out.println();
		System.out.println("-------------------------------------------------------");
		System.out.println("Strings that contain 'e' first with Helper Method");
		System.out.println();

		Arrays.sort(animalStrings, (String x, String y) -> eStringsFirstWithHelperMethod(x, y));
		List<String> sortedList = Arrays.asList(animalStrings);
		sortedList.forEach(System.out::println);

	}

	public static void lengthShortestToLongest() {

		System.out.println();
		System.out.println("Shortest to longest String length sort");
		System.out.println();

		List<String> sortedList = Arrays.asList(someStrings);
		sortedList.sort((x, y) -> ((Integer) (x.length())).compareTo((Integer) (y.length())));
		sortedList.forEach(System.out::println);
	}

	public static void lengthLongestToShortest() {

		System.out.println();
		System.out.println("-------------------------------------------------------");
		System.out.println("Longest to shortest String length sort");
		System.out.println();

		List<String> sortedList = Arrays.asList(someStrings);
		sortedList.sort((x, y) -> ((Integer) (y.length())).compareTo((Integer) (x.length())));
		sortedList.forEach(System.out::println);
	}

	public static void alphabeticallyByFirstCharacter() {

		System.out.println();
		System.out.println("-------------------------------------------------------");
		System.out.println("Sorted alphabetically by first character only");
		System.out.println();

		List<String> sortedList = Arrays.asList(animalStrings);
		sortedList.sort((x, y) -> ((Character) (x.charAt(0))).compareTo((Character) (y.charAt(0))));
		sortedList.forEach(System.out::println);
	}

	public static void eStringsFirst() {

		System.out.println();
		System.out.println("-------------------------------------------------------");
		System.out.println("Strings that contain 'e' first");
		System.out.println();

		List<String> animalList = Arrays.asList(animalStrings);

		EListInterface eListInterface = (List<String> list) -> {
			Stream<String> eList = list.stream().filter(x -> x.contains("e"));
			Stream<String> notEList = list.stream().filter(x -> !x.contains("e"));

			Stream.concat(eList, notEList).collect(Collectors.toList()).forEach(n -> System.out.println(n));
		};

		eListInterface.makeList(animalList);
	}

	public static Integer eStringsFirstWithHelperMethod(String x, String y) {

		if (x.contains("e") && !y.contains("e")) {
			return -1;
		}
		if (y.contains("e") && !x.contains("e")) {
			return 1;
		}
		return 0;
	}

}
