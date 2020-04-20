package com.smoothstack.training.wk1.project;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class acts a base class for ordinary data objects that consist of and
 * key value and a stored object. The data store is implemented using a map. The
 * data is read from a file and can be written back to file as well.
 * 
 * @author jalveste
 */
public class DataStore {
	protected Map<Integer, IDObject> dataMap; // map for data storage
	protected int lastID; // highest ID assigned to objects so far

	/**
	 * This class constructor creates a new HashMap instance to store data
	 */
	public DataStore() {
		dataMap = new HashMap<>();
	}

	/**
	 * @return the lastID
	 */
	public int getLastID() {
		return lastID;
	}

	/**
	 * @param lastID the lastID to set
	 */
	public void setLastID(int lastID) {
		this.lastID = lastID;
	}

	/**
	 * This class saves the data from the program to a specified file path
	 * 
	 * @param filePath output file path to write the program data stored in the map
	 */
	public int writeToFile(String filePath) {

		try {
			// create new output streams
			FileOutputStream fileOut = new FileOutputStream(filePath);
			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

			// loop through all map objects and write them all to file
			for (IDObject o : dataMap.values()) {
				objOut.writeObject(o);
			}

			// close output connection
			objOut.close();

			return 0;
		} catch (Exception e) {
			System.out.println("Problem writing file");
			return 1;
		}

	}

	/**
	 * This class loads data from a specified file path into this classes map data
	 * store
	 * 
	 * @param filePath input file path of data to read into datastore map
	 * @return 0 if file read successful; 1 if there was a problem reading input
	 *         file
	 */
	public int readFromFile(String filePath) {
		IDObject obj; // holds type cast object read from file
		ObjectInputStream objIn = null; // object input stream
		File file = null; // file to read

		// try to create a new file to read from filePath
		try {
			file = new File(filePath);
			file.createNewFile(); // create new file if one doesn't exist
		} catch (Exception e) {
			System.out.println("Error creating new file with given filePath");
			return 1;
		}

		// try opening data input streams and read each object from file
		try {
			FileInputStream fileIn = new FileInputStream(filePath);

			// loop reads each object from file and inserts them into dataMap
			objIn = new ObjectInputStream(fileIn);
			do {
				obj = (IDObject) objIn.readObject();
				if (obj != null) {
					dataMap.put(obj.getId(), obj);
				}
			} while (obj != null);

			// close object input stream
			if (objIn != null)
				objIn.close();

			System.out.println("Object read success");
			return 0;

		} catch (EOFException e) {	// this part executes after file encounters EOF
			try {
				// set lastID to the maximum id value read in the input file
				lastID = dataMap.keySet().stream().max(Integer::compareTo).orElse(0);

				// close object input stream
				if (objIn != null)
					objIn.close();
			} catch (IOException e1) {
				System.err.println("Error closing file after reading");
				return 1;
			}

			return 0;

		} catch (NullPointerException e) {
			System.out.println("File does not exist");
			return 1;
		} catch (Exception e) {
			System.out.println("Error reading file");
			return 1;
		}

	}

	/**
	 * This class searches dataMap for IDObject names matching the passed String
	 * name argument. The id keys of all matches found are returned in a list
	 * 
	 * @param name name to search for in dataMap
	 * @return list of id keys of IDObjects with matching name
	 */
	public List<Integer> getKey(String name) {
		List<Integer> results = dataMap.entrySet().stream().filter(x -> x.getValue().getName().equals(name))
				.map(x -> x.getKey()).collect(Collectors.toList());

		return results;
	}

	/**
	 * This class searches for an object in the data map by its id key value
	 * 
	 * @param id id key value to search by
	 * @return object matching id, or null if no match found
	 */
	public IDObject getIDObject(int id) {
		List<IDObject> results = dataMap.entrySet().stream().filter(x -> x.getValue().getId() == id)
				.map(x -> x.getValue()).collect(Collectors.toList());

		// if success, should return a list of exactly int representing matching key ID
		if (results.size() > 0) {
			return results.get(0);
		} else {
			return null;
		}

	}

	/**
	 * This class deletes an object with key value of id if it exists in the store
	 * 
	 * @param id key value of object to delete
	 * @return 0 if successful; 1 if ID does not exist
	 */
	public int delete(int id) {
		System.out.println();
		System.out.println("Delete:");

		// check to see if an object key id exists
		boolean exists = dataMap.entrySet().stream().anyMatch(x -> x.getKey() == id);

		// remove object with key id if it exists, otherwise, indicate failure
		if (exists) {
			dataMap.remove(id);
			System.out.println("Removed object with ID " + id);
			System.out.println();
			return 0;
		} else {
			System.out.println("Invalid ID");
			System.out.println();
			return 1;
		}
	}
}
