/**
 * 
 */
package com.ss.training.wk2.project;

import java.util.List;

/**
 * @author jalveste
 *
 */
public class OptionMenu {

	public MenuChoice displayMainMenu(String prompt, List<String> optList) {

		UserInput userInput = new UserInput();

		System.out.println(prompt);
		System.out.println();

		int counter = 1;
		for (String opt : optList) {
			System.out.println("  " + counter + ") " + opt);
			counter++;
		}

		int response = userInput.askForIntInRange("\n", 1, optList.size());

		MenuChoice choice = new MenuChoice();
		choice.setId(response);

		return choice;
	}

	public MenuChoice displayMenuWithPrevious(String prompt, List<String> optList) {

		UserInput userInput = new UserInput();

		System.out.println(prompt);

		int counter = 1;
		for (String opt : optList) {
			System.out.println("  " + counter + ") " + opt);
			counter++;
		}
		System.out.println("  " + counter + ") Quit to previous");

		int response = userInput.askForIntInRange("\n", 1, optList.size() + 1);

		if (response == counter) {
			return null;
		} else {
			MenuChoice choice = new MenuChoice();
			choice.setId(response);
			return choice;
		}
	}
}
