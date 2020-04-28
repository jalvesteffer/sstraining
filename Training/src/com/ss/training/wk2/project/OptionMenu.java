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

	/**
	 * This method creates a selection menu in a console user interface
	 * 
	 * @param prompt  a user prompt giving instructions about selection
	 * @param optList a list of strings, each representing one choice
	 * @return MenuChoice object with an Integer and String component
	 */
	public MenuChoice displayMainMenu(String prompt, List<String> optList) {

		UserInput userInput = new UserInput();
		
		if (optList == null)	{
			return null;
		}

		System.out.println(prompt);
		System.out.println();

		int counter = 1;
		for (String opt : optList) {
			System.out.println("  " + counter + ") " + opt);
			counter++;
		}
		System.out.println("\n  0) Exit");

		int response = userInput.askForIntInRange("\n", 0, optList.size());

		MenuChoice choice = new MenuChoice();
		choice.setId(response);

		return choice;
	}

	/**
	 * This method creates a selection menu in a console user interface. It adds an
	 * additional "go back" option as the last choice
	 * 
	 * @param prompt  a user prompt giving instructions about selection
	 * @param optList a list of strings, each representing one choice
	 * @return MenuChoice object with an Integer and String component
	 */
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

	/**
	 * This method creates a selection menu in a console user interface. It adds an
	 * additional "go back" option as the last choice
	 * 
	 * @param prompt       a user prompt giving instructions about selection
	 * @param optList      a list of strings, each representing one choice
	 * @param customChoice the last choice is the menu is this String
	 * @return MenuChoice object with an Integer and String component
	 */
	public MenuChoice displayMenuWithCustomChoice(String prompt, List<String> optList, String customChoice) {

		UserInput userInput = new UserInput();

		System.out.println(prompt);

		int counter = 1;
		for (String opt : optList) {
			System.out.println("  " + counter + ") " + opt);
			counter++;
		}
		System.out.println("  " + counter + ") " + customChoice);

		int response = userInput.askForIntInRange("\n", 1, optList.size() + 1);

		if (response == counter) {
			return null;
		} else {
			MenuChoice choice = new MenuChoice();
			choice.setId(response);
			return choice;
		}
	}

	/**
	 * This method generates a list of strings that are for information purposes.
	 * There is selection involved here
	 * 
	 * @param prompt  description of list displayed
	 * @param optList list of strings, each list item shown on a seperate line
	 */
	public void displayOnly(String prompt, List<String> optList) {
		System.out.println(prompt);

		int counter = 1;
		for (String opt : optList) {
			System.out.println("  " + counter + ") " + opt);
			counter++;
		}
	}
}
