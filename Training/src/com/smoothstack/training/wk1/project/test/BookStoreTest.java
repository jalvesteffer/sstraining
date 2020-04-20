package com.smoothstack.training.wk1.project.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.smoothstack.training.wk1.project.AuthorStore;
import com.smoothstack.training.wk1.project.Book;
import com.smoothstack.training.wk1.project.BookStore;
import com.smoothstack.training.wk1.project.PublisherStore;

public class BookStoreTest {

	@Test
	public void readBookEmptyFile() {
		PublisherStore testPublisherStore = new PublisherStore();
		AuthorStore testAuthorStore = new AuthorStore();
		BookStore testBookStore = new BookStore();
		testPublisherStore.readFromFile("resources/test/publishers_empty.txt");
		testAuthorStore.readFromFile("resources/test/authors_empty.txt");
		testBookStore.readFromFile("resources/test/books_empty.txt");

		assertEquals(testBookStore.read(testAuthorStore, testPublisherStore), 0);
	}
	
	@Test
	public void readBookFullFile() {
		PublisherStore testPublisherStore = new PublisherStore();
		AuthorStore testAuthorStore = new AuthorStore();
		BookStore testBookStore = new BookStore();
		testPublisherStore.readFromFile("resources/test/publishers_full.txt");
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		testBookStore.readFromFile("resources/test/books_full.txt");

		assertEquals(testBookStore.read(testAuthorStore, testPublisherStore), 22);
	}
	
	@Test
	public void deleteBooksFromPublisherBadID() {
		BookStore testBookStore = new BookStore();
		testBookStore.readFromFile("resources/test/books_full.txt");

		assertEquals(testBookStore.deleteBooksFromPublisher(0), 0);
		assertEquals(testBookStore.deleteBooksFromPublisher(-1), 0);
		assertEquals(testBookStore.deleteBooksFromPublisher(100), 0);
	}
	
	@Test
	public void deleteBooksFromPublisherValid() {
		BookStore testBookStore = new BookStore();
		testBookStore.readFromFile("resources/test/books_full.txt");

		assertEquals(testBookStore.deleteBooksFromPublisher(5), 2);
		assertEquals(testBookStore.deleteBooksFromPublisher(13), 1);
	}
	
	@Test
	public void deleteBooksFromAuthorBadID() {
		BookStore testBookStore = new BookStore();
		testBookStore.readFromFile("resources/test/books_full.txt");

		assertEquals(testBookStore.deleteBooksFromAuthor(0), 0);
		assertEquals(testBookStore.deleteBooksFromAuthor(-1), 0);
		assertEquals(testBookStore.deleteBooksFromAuthor(100), 0);
	}
	
	@Test
	public void deleteBooksFromAuthorValid() {
		BookStore testBookStore = new BookStore();
		testBookStore.readFromFile("resources/test/books_full.txt");

		assertEquals(testBookStore.deleteBooksFromAuthor(2), 3);
		assertEquals(testBookStore.deleteBooksFromAuthor(16), 1);
	}
	
	@Test
	public void printAuthorListNullArgs() {
		AuthorStore testAuthorStore = new AuthorStore();
		BookStore testBookStore = new BookStore();
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		testBookStore.readFromFile("resources/test/books_full.txt");
		
		ArrayList<Integer> authorsList = new ArrayList<>();
		authorsList.add(2);
		authorsList.add(4);

		assertEquals(testBookStore.printAuthorList(null, null), -1);
	}
	
	@Test
	public void printAuthorListEmpty() {
		AuthorStore testAuthorStore = new AuthorStore();
		BookStore testBookStore = new BookStore();
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		testBookStore.readFromFile("resources/test/books_full.txt");
		
		ArrayList<Integer> authorsList = new ArrayList<>();

		assertEquals(testBookStore.printAuthorList(authorsList, testAuthorStore), 0);
	}
	
	@Test
	public void printAuthorListValid() {
		AuthorStore testAuthorStore = new AuthorStore();
		BookStore testBookStore = new BookStore();
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		testBookStore.readFromFile("resources/test/books_full.txt");
		
		ArrayList<Integer> authorsList = new ArrayList<>();
		authorsList.add(2);
		authorsList.add(4);

		assertEquals(testBookStore.printAuthorList(authorsList, testAuthorStore), 2);
	}
	
	@Test
	public void updateBadArgs() {
		BookStore testBookStore = new BookStore();
		testBookStore.readFromFile("resources/test/books_full.txt");

		assertEquals(testBookStore.update(0, null), -1);
		assertEquals(testBookStore.update(-1, "T"), -1);
		assertEquals(testBookStore.update(-1, "New Title"), -1);
	}
	
	@Test
	public void updateInvalidTitleLengths() {
		BookStore testBookStore = new BookStore();
		testBookStore.readFromFile("resources/test/books_full.txt");

		assertEquals(testBookStore.update(1, "N"), -1);
		assertEquals(testBookStore.update(2, "New Title New Title New Title New Title New Title New Title New Title New Title New Title"), -1);
	}
	
	@Test
	public void updateValid() {
		BookStore testBookStore = new BookStore();
		testBookStore.readFromFile("resources/test/books_full.txt");

		assertEquals(testBookStore.update(1, "New Title"), -0);
		assertEquals(testBookStore.update(2, "Another Title"), 0);
	}
	
	@Test
	public void createNullArgs() {
		PublisherStore testPublisherStore = new PublisherStore();
		AuthorStore testAuthorStore = new AuthorStore();
		BookStore testBookStore = new BookStore();
		testPublisherStore.readFromFile("resources/test/publishers_full.txt");
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		testBookStore.readFromFile("resources/test/books_full.txt");

		ArrayList<String> authors = new ArrayList<>();
		authors.add("Test Author1");
		authors.add("Test Author2");
		
		assertEquals(testBookStore.create(null, null, null, null, null, null), -1);
	}
	
	@Test
	public void createBadArgLengths() {
		PublisherStore testPublisherStore = new PublisherStore();
		AuthorStore testAuthorStore = new AuthorStore();
		BookStore testBookStore = new BookStore();
		testPublisherStore.readFromFile("resources/test/publishers_full.txt");
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		testBookStore.readFromFile("resources/test/books_full.txt");

		ArrayList<String> authors = new ArrayList<>();
		authors.add("Test Author1");
		authors.add("Test Author2");
		
		assertEquals(testBookStore.create("", authors, "Test Publisher", "Test Address", testAuthorStore, testPublisherStore), -1);
		assertEquals(testBookStore.create("Test Name", authors, "", "Test Address", testAuthorStore, testPublisherStore), -1);
		assertEquals(testBookStore.create("Test Name Test Name Test Name Test Name Test Name Test Name Test Name Test Name Test Name Test Name Test Name ", authors, "Test Publisher", "Test Address", testAuthorStore, testPublisherStore), -1);
	}
	
	@Test
	public void createValid() {
		PublisherStore testPublisherStore = new PublisherStore();
		AuthorStore testAuthorStore = new AuthorStore();
		BookStore testBookStore = new BookStore();
		testPublisherStore.readFromFile("resources/test/publishers_full.txt");
		testAuthorStore.readFromFile("resources/test/authors_full.txt");
		testBookStore.readFromFile("resources/test/books_full.txt");

		ArrayList<String> authors = new ArrayList<>();
		authors.add("Test Author1");
		authors.add("Test Author2");
		
		assertEquals(testBookStore.create("Test Title", authors, "Test Publisher", "Test Address", testAuthorStore, testPublisherStore), 0);
		assertEquals(testBookStore.create("Test Title", authors, "1", "Test Address", testAuthorStore, testPublisherStore), 0);
		assertEquals(testBookStore.create("Test Title", authors, "2", "", testAuthorStore, testPublisherStore), 0);
	}
	
}
