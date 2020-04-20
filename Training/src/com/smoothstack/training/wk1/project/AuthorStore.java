package com.smoothstack.training.wk1.project;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class extends the basic map storage of the DataStore class and provides
 * additional CRUD operations over IDOBjects that are specifically Author
 * objects
 * 
 * @author jalveste
 *
 */
public class AuthorStore extends DataStore {

	/**
	 * This class displays all authors stored in the author map with their id
	 * numbers
	 * 
	 * @return number of records output
	 */
	public int read() {
		int numOutput = 0; // keep track of how many records output for testing purposes

		System.out.println();
		System.out.println("Read:");

		// loop through the data map objects, outputting one per line
		for (IDObject o : dataMap.values()) {
			System.out.println("[" + o.getId() + "] " + o.getName());
			numOutput++;
		}
		System.out.println();

		return numOutput;
	}

	/**
	 * This class takes an ArrayList of IDs as an argument and prints Author
	 * information for all authors with matching IDs.
	 * 
	 * @param idList list of Author IDs for which to print out corresponding author
	 *               info to console
	 * @return number of valid author records printed to console; -1 indicates a
	 *         failure
	 */
	public int read(ArrayList<Integer> idList) {
		List<Integer> intersectList = null;
		Author author;

		// create a new list containing records with IDs matching the idList
		try {
			intersectList = dataMap.keySet().stream().filter(idList::contains).collect(Collectors.toList());
		} catch (Exception e) {
			System.out.println("Problem with intersection list operation");
			return -1;
		}

		// output records corresponding to idList
		for (Integer authorID : intersectList) {
			author = (Author) dataMap.get(authorID);
			System.out.println("[" + author.getId() + "] " + author.getName());
		}

		// return the number output records
		return intersectList.size();
	}

	/**
	 * This class creates a new author with the name argument
	 * 
	 * @param name name of author
	 * @return ID of author created; -1 on failure
	 */
	public int create(String name) {
		System.out.println();
		System.out.println("Write:");

		// make sure name isn't null
		if (name == null) {
			System.out.println("Author name is null");
			return -1;
		}

		// see if name already exists
		List<Integer> results = getKey(name);

		// add new author if name is valid; otherwise, display error
		// names must be between 2-50 characters and not already exist
		if (name.length() > 1 && name.length() <= 50 && results.size() == 0) {
			Author newAuthor = new Author(++lastID, name);
			dataMap.put(lastID, newAuthor);
			System.out.println("Added new author with name: " + name);
			System.out.println();
			return lastID;
		} else {
			System.out.println("Author name invalid");
			System.out.println("Author names must be between 2-50 characters in length and cannot already exist");
			System.out.println();
			return -1;
		}
	}

	/**
	 * This class updates an author name having id with a new name
	 * 
	 * @param id      id of author to update
	 * @param newName new author name to replace existing name
	 * @return 0 on success; 1 on failure
	 */
	public int update(int id, String newName) {
		System.out.println();
		System.out.println("Update:");

		// checks to see if publisher with ID exists in data map
		// also check whether the new name already exists
		boolean oldNameExists = dataMap.entrySet().stream().anyMatch(x -> x.getKey() == id);
		boolean newNameExists = dataMap.entrySet().stream().anyMatch(x -> x.getValue().getName().equals(newName));

		// update name if the new name is valid and doesn't already exist; otherwise, return with error
		if (oldNameExists && !newNameExists && newName != null && newName.length() > 1 && newName.length() <= 50) {
			dataMap.get(id).setName(newName);
			return 0;
		} else {
			System.out.println("Invalid update");
			System.out.println("Names must be between 2-50 characters in length. "
					+ "Updated author names cannot already exist");
			System.out.println();
			return 1;
		}
	}

	/**
	 * This class deletes an author with matching id. It also deletes all books
	 * by this author in the bookStore to ensure referential integrity
	 * 
	 * @param id        id of author to delete
	 * @param bookStore bookStore from which to delete all books by author having id
	 * @return 0 on success; 1 on failure
	 */
	public int delete(int id, BookStore bookStore) {
		// delete author with id; result reports any error with id
		int result = this.delete(id);

		// if the id deleted without error, proceed to delete all books from the author
		// with id; else, there is a problem with the id
		if (result != 1) {
			
			// make sure the bookstore is not null before deleting books by author with id
			if (bookStore != null) {
				bookStore.deleteBooksFromAuthor(id);
				return 0;
			} else {
				System.out.println("bookStore is null");
				return 1;
			}
		} else {
			System.out.println("Author ID to delete is invalid");
			return 1;
		}
	}
}
