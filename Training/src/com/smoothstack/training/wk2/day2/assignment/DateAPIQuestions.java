package com.smoothstack.training.wk2.day2.assignment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

public class DateAPIQuestions {

	public static void main(String[] args) {

		/*
		 * Question 1
		 * 
		 * LocalDateTime.of(int year, int month, int dayOfMonth, int hour, int minute,
		 * int second, int nanoOfSecond);
		 */

		/*
		 * Question 2
		 * 
		 * LocalDateTime.minusWeeks(long weeks);
		 */

		/*
		 * Question 3
		 * 
		 * ZoneId refers to a specific time-zone whereas ZoneOffset specifies a timezone
		 * relative to UTC/GMT
		 */

		/*
		 * Question 4
		 * 
		 * Convert from Instant -> ZonedDateTime Use the atZone() method on the Instant
		 * 
		 * Convert from ZonedDateTime -> Instant Use the toInstant() method on the
		 * dateTime
		 */

		// Question 5 Solution
		System.out.println("Length of each month in a year question");
		System.out.println();

		int yearToTest = 2020;
		numberOfDaysInMonth(yearToTest);

		// Question 6 Solution
		System.out.println();
		System.out.println("-------------------------------------------------------");
		System.out.println("Number of Mondays question");
		System.out.println();

		int monthToTest = 3;
		int mondayCount = numberOfMondays(monthToTest);

		System.out.println("There are " + mondayCount + " mondays in month " + monthToTest + " of 2020.");

		// Question 7 Solution
		System.out.println();
		System.out.println("-------------------------------------------------------");
		System.out.println("Friday the 13th Question");
		System.out.println();

		// set date here to test
		LocalDate calDate = LocalDate.of(2020, Month.MARCH, 13);

		if (isFridayTheThirteenth(calDate)) {
			System.out.println("It's Friday the 13th");
		} else {
			System.out.println("It's not Friday the 13th");
		}
	}
	
	public static void numberOfDaysInMonth(int year) {

		int dayCount = 0;

		LocalDate currMonthDate = LocalDate.of(year, Month.JANUARY, 1);
		LocalDate nextYearDate = currMonthDate.plusYears(1);
		
		System.out.println("Number of days in each month for " + year);

		while (currMonthDate.isBefore(nextYearDate)) {
			System.out.println("Month " + currMonthDate.getMonthValue() + ": " + currMonthDate.lengthOfMonth() + " days");
			currMonthDate = currMonthDate.plusMonths(1);
		}
	}

	public static int numberOfMondays(int month) {

		int mondayCount = 0;

		LocalDate currDate = LocalDate.of(2020, month, 1);
		LocalDate nextMonthDate = currDate.plusMonths(1);

		while (currDate.isBefore(nextMonthDate)) {
			if (currDate.getDayOfWeek() == DayOfWeek.MONDAY) {
				mondayCount++;
			}
			currDate = currDate.plusDays(1);
		}

		return mondayCount;
	}
	
	public static boolean isFridayTheThirteenth(LocalDate calDate) {

		if (calDate.getDayOfMonth() == 13 && calDate.getDayOfWeek() == DayOfWeek.FRIDAY) {
			return true;
		} else {
			return false;
		}

	}

}