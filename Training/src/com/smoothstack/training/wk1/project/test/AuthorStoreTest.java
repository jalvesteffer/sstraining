package com.smoothstack.training.wk1.project.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.smoothstack.training.wk1.project.AuthorStore;
import com.smoothstack.training.wk1.project.BookStore;

public class AuthorStoreTest {

	@Test
	public void readAuthorsFullEmpty() {
		AuthorStore testAuthorStore = new AuthorStore();
		testAuthorStore.readFromFile("resources/test/authors_empty.txt");
		
		assertEquals(testAuthorStore.read(), 0);
	}
	
	@Test
	public void readAuthorsFullFile() {
		AuthorStore testAuthorStore = new AuthorStore();
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		
		assertEquals(testAuthorStore.read(), 22);
	}

	@Test
	public void readArrayListNull() {
		AuthorStore testAuthorStore = new AuthorStore();
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		
		assertEquals(testAuthorStore.read(null), -1);
	}
	
	@Test
	public void readArrayListSuccess() {
		AuthorStore testAuthorStore = new AuthorStore();
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		
		ArrayList<Integer> testIDList = new ArrayList<>();
		testIDList.add(1);
		testIDList.add(2);
		testIDList.add(100);
		
		assertEquals(testAuthorStore.read(testIDList), 2);
	}
	
	@Test
	public void createNullName() {
		AuthorStore testAuthorStore = new AuthorStore();
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		
		assertEquals(testAuthorStore.create(null), -1);
	}
	
	@Test
	public void createNameTooShort() {
		AuthorStore testAuthorStore = new AuthorStore();
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		
		assertEquals(testAuthorStore.create("J"), -1);
	}
	
	@Test
	public void createNameTooLong() {
		AuthorStore testAuthorStore = new AuthorStore();
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		
		assertEquals(testAuthorStore.create("Jason Alvesteffer Jason Alvesteffer Jason Alvesteffer"), -1);
	}

	@Test
	public void createNameDuplicate() {
		AuthorStore testAuthorStore = new AuthorStore();
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		
		assertEquals(testAuthorStore.create("Stephen King"), -1);
	}
	
	@Test
	public void createNameValid() {
		AuthorStore testAuthorStore = new AuthorStore();
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		
		assertEquals(testAuthorStore.create("Jason Alvesteffer"), 23);
	}
	
	@Test
	public void updateNullValues() {
		AuthorStore testAuthorStore = new AuthorStore();
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		
		assertEquals(testAuthorStore.update(1, null), 1);
		assertEquals(testAuthorStore.update(0, null), 1);
	}
	
	@Test
	public void updateNameLengthInvalid() {
		AuthorStore testAuthorStore = new AuthorStore();
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		
		assertEquals(testAuthorStore.update(1, "J"), 1);
		assertEquals(testAuthorStore.update(1, "Jason Alvesteffer Jason Alvesteffer Jason Alvesteffer"), 1);
	}
	
	@Test
	public void updateBadID() {
		AuthorStore testAuthorStore = new AuthorStore();
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		
		assertEquals(testAuthorStore.update(100, "Jason Alvesteffer"), 1);
	}
	
	@Test
	public void updateExistingName() {
		AuthorStore testAuthorStore = new AuthorStore();
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		
		assertEquals(testAuthorStore.update(1, "Jon Duckett"), 1);
	}
	
	@Test
	public void updateValid() {
		AuthorStore testAuthorStore = new AuthorStore();
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		
		assertEquals(testAuthorStore.update(1, "Jason Alvesteffer"), 0);
	}
	
	@Test
	public void deleteNullValues() {
		AuthorStore testAuthorStore = new AuthorStore();
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		
		assertEquals(testAuthorStore.delete(1, null), 1);
	}
	
	@Test
	public void deleteBadID() {
		AuthorStore testAuthorStore = new AuthorStore();
		BookStore testBookStore = new BookStore();
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		testBookStore.readFromFile("resources/test/books_full.txt");
		
		assertEquals(testAuthorStore.delete(100, testBookStore), 1);
		assertEquals(testAuthorStore.delete(0, testBookStore), 1);
	}
	
	@Test
	public void deleteValid() {
		AuthorStore testAuthorStore = new AuthorStore();
		BookStore testBookStore = new BookStore();
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		testBookStore.readFromFile("resources/test/books_full.txt");
		
		assertEquals(testAuthorStore.delete(2, testBookStore), 0);
		assertEquals(testAuthorStore.delete(5, testBookStore), 0);
	}
}
