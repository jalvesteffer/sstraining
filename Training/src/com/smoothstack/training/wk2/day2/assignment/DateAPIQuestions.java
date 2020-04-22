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
		
		// Question 7 Solution
		
		// set date here to test 
		LocalDate calDate = LocalDate.of(2020, Month.MARCH, 13);
		
	   	if (isFridayTheThirteenth(calDate))	{
	   		System.out.println("It's Friday the 13th");
	   	} else	{
	   		System.out.println("It's not Friday the 13th");
	   	}
	}

	public static boolean isFridayTheThirteenth(LocalDate calDate) {

		if (calDate.getDayOfMonth() == 13 && calDate.getDayOfWeek() == DayOfWeek.FRIDAY) {
			return true;
		} else {
			return false;
		}

	}
}