package com.smoothstack.training.wk1.project.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.smoothstack.training.wk1.project.Author;
import com.smoothstack.training.wk1.project.DataStore;
import com.smoothstack.training.wk1.project.IDObject;

public class DataStoreTest {
	
	@Test
	public void readFromFilePathNull() {
		DataStore testDataStore = new DataStore();

		assertEquals(testDataStore.readFromFile(null), 1);
	}
	
	@Test
	public void readFromFilePathSuccess() {
		DataStore testDataStore = new DataStore();
		
		assertEquals(testDataStore.readFromFile("resources/test/authors_full.txt"), 0);
	}
	
	@Test
	public void writeToFilePathNull() {
		DataStore testDataStore = new DataStore();

		assertEquals(testDataStore.writeToFile(null), 1);
	}
	
	@Test
	public void writeToFilePathSuccess() {
		DataStore testDataStore = new DataStore();
		testDataStore.readFromFile("resources/test/authors_full.txt");

		assertEquals(testDataStore.writeToFile("resources/test/authors_full.txt"), 0);
	}
	
	@Test
	public void testGetKeyFail() {
		DataStore testDataStore = new DataStore();
		testDataStore.readFromFile("resources/test/authors_full.txt");

		List<Integer> emptyList = new ArrayList<>();
		assertEquals(testDataStore.getKey("Bad Name"), emptyList);
	}
	
	@Test
	public void testGetKeySuccess() {
		DataStore testDataStore = new DataStore();
		testDataStore.readFromFile("resources/test/authors_full.txt");
		
		List<Integer> resultList = new ArrayList<>();
		resultList.add(2);
		
		assertEquals(testDataStore.getKey("Stephen King"), resultList);
	}
	
	@Test
	public void testGetIDObjectBadID() {
		DataStore testDataStore = new DataStore();
		testDataStore.readFromFile("resources/test/authors_full.txt");

		assertNull(testDataStore.getIDObject(100));
	}
	
	@Test
	public void testGetIDObjectSuccess() {
		DataStore testDataStore = new DataStore();
		testDataStore.readFromFile("resources/test/authors_full.txt");
		
		List<IDObject> results = new ArrayList<>();
		Author obj = new Author(2, "Stephen King");
		results.add(obj);
		
		assertEquals(testDataStore.getIDObject(2), obj);
	}
	
	@Test
	public void testDeleteBadID() {
		DataStore testDataStore = new DataStore();
		testDataStore.readFromFile("resources/test/authors_full.txt");

		assertEquals(testDataStore.delete(100), 1);
	}
	
	@Test
	public void testDeleteSuccess() {
		DataStore testDataStore = new DataStore();
		testDataStore.readFromFile("resources/test/authors_full.txt");

		assertEquals(testDataStore.delete(2), 0);
	}
}
