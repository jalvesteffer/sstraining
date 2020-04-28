package com.ss.training.wk2.project.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ss.training.wk2.project.UserInterface;
import com.ss.training.wk2.project.entity.Book;

public class UserInterfaceTest {

	@Test
	public void makeTitleByAuthorStringsNull()	{
		UserInterface ui = new UserInterface();
		List<Book> books = null;
		
		assertEquals(ui.makeTitleByAuthorStrings(books), null);
	}
	
	@Test
	public void makeTitleByAuthorStringsEmpty()	{
		UserInterface ui = new UserInterface();
		List<Book> books = new ArrayList<>();
		
		assertEquals(ui.makeTitleByAuthorStrings(books), null);
	}
	

}
