package com.ss.training.wk2.project.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ss.training.wk2.project.MenuChoice;
import com.ss.training.wk2.project.OptionMenu;

public class OptionMenuTest {
	
	@Test
	public void displayMenuChoiceNullValues()	{
		OptionMenu optMenu = new OptionMenu();
		
		// set display menu method argument here
		String prompt = "Welcome to the GCIT Library Management System. Which category of a user are you";
		List<String> optList = new ArrayList<>();
		optList.add("Librarian");
		optList.add("Administrator");
		optList.add("Borrower");
		
		assertNull(optMenu.displayMainMenu(null, null));
		
		
	}
}
