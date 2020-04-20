package com.smoothstack.training.wk1.project.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.smoothstack.training.wk1.project.BookStore;
import com.smoothstack.training.wk1.project.Publisher;
import com.smoothstack.training.wk1.project.PublisherStore;

public class PublisherStoreTest {

	@Test
	public void readPublisherEmptyFile() {
		PublisherStore testPublisherStore = new PublisherStore();
		testPublisherStore.readFromFile("resources/test/publishers_empty.txt");

		assertEquals(testPublisherStore.read(), 0);
	}

	@Test
	public void readAuthorsFullFile() {
		PublisherStore testPublisherStore = new PublisherStore();
		testPublisherStore.readFromFile("resources/test/publishers_full.txt");

		assertEquals(testPublisherStore.read(), 16);
	}

	@Test
	public void printPublisherInfoNull() {
		PublisherStore testPublisherStore = new PublisherStore();
		testPublisherStore.readFromFile("resources/test/publishers_full.txt");

		assertEquals(testPublisherStore.printPublisherInfo(null), -1);
	}

	@Test
	public void printPublisherInfoSuccess() {
		PublisherStore testPublisherStore = new PublisherStore();
		testPublisherStore.readFromFile("resources/test/publishers_full.txt");

		Publisher pubObj = (Publisher) testPublisherStore.getIDObject(1);

		assertEquals(testPublisherStore.printPublisherInfo(pubObj), 0);
	}

	@Test
	public void createNullValues() {
		PublisherStore testPublisherStore = new PublisherStore();
		testPublisherStore.readFromFile("resources/test/publishers_full.txt");

		assertEquals(testPublisherStore.create(null, "New York, NY"), -1);
		assertEquals(testPublisherStore.create("Jason Publishers", null), -1);
		assertEquals(testPublisherStore.create(null, null), -1);
	}

	@Test
	public void readCreateNameLengthErrors() {
		PublisherStore testPublisherStore = new PublisherStore();
		testPublisherStore.readFromFile("resources/test/publishers_full.txt");

		assertEquals(testPublisherStore.create("J", "Williamsburg, USA"), -1);
		assertEquals(
				testPublisherStore.create("Jason Publishing Jason Publishing Jason Publishing ", "Williamsburg, USA"),
				-1);
		assertEquals(testPublisherStore.create("Jason Publishing", "W"), -1);
		assertEquals(testPublisherStore.create("Jason Publishing",
				"Williamsburg, USA Williamsburg, USA Williamsburg, USA Williamsburg, USA"), -1);
	}

	@Test
	public void createDuplicatePublisher() {
		PublisherStore testPublisherStore = new PublisherStore();
		testPublisherStore.readFromFile("resources/test/publishers_full.txt");

		assertEquals(testPublisherStore.create("Tor Books", "Williamsburg, USA"), -1);
	}

	@Test
	public void readCreateValid() {
		PublisherStore testPublisherStore = new PublisherStore();
		testPublisherStore.readFromFile("resources/test/publishers_full.txt");

		assertEquals(testPublisherStore.create("Jason Books", "Williamsburg, USA"), 17);
		assertEquals(testPublisherStore.create("New Era Publishing", "New York, USA"), 18);
	}

	@Test
	public void deleteNullValues() {
		PublisherStore testPublisherStore = new PublisherStore();
		testPublisherStore.readFromFile("resources/test/publishers_full.txt");

		assertEquals(testPublisherStore.delete(1, null), 1);
	}

	@Test
	public void deleteBadID() {
		PublisherStore testPublisherStore = new PublisherStore();
		BookStore testBookStore = new BookStore();
		testPublisherStore.readFromFile("resources/test/publishers_full.txt");
		testBookStore.readFromFile("resources/test/books_full.txt");

		assertEquals(testPublisherStore.delete(100, testBookStore), 1);
		assertEquals(testPublisherStore.delete(0, testBookStore), 1);
	}

	@Test
	public void deleteValid() {
		PublisherStore testPublisherStore = new PublisherStore();
		BookStore testBookStore = new BookStore();
		testPublisherStore.readFromFile("resources/test/publishers_full.txt");
		testBookStore.readFromFile("resources/test/books_full.txt");

		assertEquals(testPublisherStore.delete(2, testBookStore), 0);
		assertEquals(testPublisherStore.delete(5, testBookStore), 0);
	}

	@Test
	public void updateNullValues() {
		PublisherStore testPublisherStore = new PublisherStore();
		testPublisherStore.readFromFile("resources/test/publishers_full.txt");

		assertEquals(testPublisherStore.update(1, null, "Williamsburg, USA"), 1);
		assertEquals(testPublisherStore.update(4, "Jason Publishers", null), 1);
		assertEquals(testPublisherStore.update(3, null, null), 1);
	}

	@Test
	public void updateNameLengthInvalid() {
		PublisherStore testPublisherStore = new PublisherStore();
		testPublisherStore.readFromFile("resources/test/publishers_full.txt");

		assertEquals(testPublisherStore.update(1, "J", "Williamsburg, USA"), 1);
		assertEquals(testPublisherStore.update(5, "Jason Alvesteffer Jason Alvesteffer Jason Alvesteffer",
				"Williamsburg, USA"), 1);
		assertEquals(testPublisherStore.update(1, "Jason Publishing", "W"), 1);
		assertEquals(testPublisherStore.update(5, "Jason Publishing",
				"Williamsburg, USA Williamsburg, USA Williamsburg, USA Williamsburg, USA Williamsburg"), 1);
	}

	@Test
	public void updateBadID() {
		PublisherStore testPublisherStore = new PublisherStore();
		testPublisherStore.readFromFile("resources/test/publishers_full.txt");
		
		assertEquals(testPublisherStore.update(100, "Jason Alvesteffer", "Williamsburg, USA"), 1);
		assertEquals(testPublisherStore.update(-1, "Jason Alvesteffer", "Williamsburg, USA"), 1);
	}
	
	@Test
	public void updateExistingName() {
		PublisherStore testPublisherStore = new PublisherStore();
		testPublisherStore.readFromFile("resources/test/publishers_full.txt");
		
		assertEquals(testPublisherStore.update(2, "Hachette", "Williamsburg, USA"), 1);
		assertEquals(testPublisherStore.update(5, "Tor Books", "Williamsburg, USA"), 1);
	}
	
	@Test
	public void updateValid() {
		PublisherStore testPublisherStore = new PublisherStore();
		testPublisherStore.readFromFile("resources/test/publishers_full.txt");
		
		assertEquals(testPublisherStore.update(1, "Jason Publishing", "Williamsburg, USA"), 0);
	}
	

}
