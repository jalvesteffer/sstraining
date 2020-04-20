package com.smoothstack.training.wk1day3.practice.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.smoothstack.training.wk1day3.practice.Author;
import com.smoothstack.training.wk1day3.practice.AuthorService;

public class AuthorServiceTest {

	@Test
	public void authorNameEmptyOrNull() {
		AuthorService authorService = new AuthorService();
//		Author author = new Author(2, "ab");
		assertEquals(authorService.addAuthor(null), "Author Name Cannot be Null");
	}
	
	@Test
	public void authorNameLengthMin() {
		AuthorService authorService = new AuthorService();
		Author author = new Author(2, "ab");
		assertEquals(authorService.addAuthor(author), "Author name has to be more than 3 characters");
	}
	
	@Test
	public void authorNameLengthMax() {
		AuthorService authorService = new AuthorService();
		Author author = new Author(3, "1234567891");
		assertEquals(authorService.addAuthor(null), "Author Name cannot be more than 10 characters");
	}
	
	@Test
	public void authorNameValid() {
		AuthorService authorService = new AuthorService();
		Author author = new Author(4, "John D");
		assertEquals(authorService.addAuthor(author), "Success");
	}
}
