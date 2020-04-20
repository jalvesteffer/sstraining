package com.smoothstack.training.wk1.project;

import java.util.List;

/**
 * This class extends the basic map storage of the DataStore class and provides
 * additional CRUD operations over IDOBjects that are specifically Publisher
 * objects
 * 
 * @author jalveste
 *
 */
public class PublisherStore extends DataStore {

	/**
	 * This method displays all publishers stored in the publisher map with their id
	 * numbers
	 * 
	 * @return number of records output
	 */
	public int read() {
		Publisher publisher; // used for receiving type-cast IDObject
		int numOutput = 0; // keep track of how many records output for testing purposes

		System.out.println();
		System.out.println("Read:");

		// loop through the publisher map objects, outputting one per line
		for (IDObject obj : dataMap.values()) {
			publisher = (Publisher) obj;
			System.out.print("[" + publisher.getId() + "] ");
			printPublisherInfo(publisher);
			numOutput++;
		}
		System.out.println();

		return numOutput;
	}

	/**
	 * This method receives a Publisher object, and outputs the Publisher name and
	 * address info contained in the object to the console
	 * 
	 * @param pubObj publisher object to output info from
	 * @return 0 on success; -1 on failure
	 */
	public int printPublisherInfo(Publisher pubObj) {

		// null check on publisher object
		if (pubObj != null) {
			System.out.println(pubObj.getName() + " (" + pubObj.getPublisherAddress() + ")");
			return 0;
		} else {
			System.out.println("Publisher object is null");
			return -1;
		}
	}

	/**
	 * This method creates a new publisher with the name and address arguments
	 * 
	 * @param name    name of publisher
	 * @param address address of publisher
	 * @return ID of publisher created; -1 on failure
	 */
	public int create(String name, String address) {
		System.out.println();
		System.out.println("Write:");
		List<Integer> results = getKey(name);

		// make sure name isn't null
		if (name == null || address == null) {
			System.out.println("name or address is null");
			System.out.println();
			return -1;
		}

		// add new publisher if name is valid; otherwise, display error
		// names must be between 2-40 characters and not already exist
		// addresses must be between 2-40 characters
		if (results.size() == 0 && name.length() > 1 && name.length() <= 40 && address.length() > 1
				&& address.length() <= 40) {
			Publisher newPublisher = new Publisher(++lastID, name, address);
			dataMap.put(lastID, newPublisher);
			System.out.println("Added new publisher with name: " + name);
			System.out.println();
			return lastID;
		} else {
			System.out.println("Publisher name or address is invalid");
			System.out.println("Names & addresses must be between 2-40 characters in length. "
					+ "Publisher names cannot already exist");
			System.out.println();
			return -1;
		}
	}

	/**
	 * This method deletes a publisher with matching id. It also deletes all books by
	 * this publisher in the bookStore to ensure referential integrity
	 * 
	 * @param id        id of publisher to delete
	 * @param bookStore bookStore from which to delete all books by publisher having
	 *                  id
	 * @return 0 on success; 1 on failure
	 */
	public int delete(int id, BookStore bookStore) {
		// delete publisher with id; result reports any error with id
		int result = this.delete(id);

		// if the id deleted without error, proceed to delete all books from the
		// publisher
		// with id; else, there is a problem with the id
		if (result != 1) {

			// make sure the bookstore is not null before deleting books by publisher with
			// id
			if (bookStore != null) {
				bookStore.deleteBooksFromPublisher(id);
				return 0;
			} else {
				System.out.println("bookStore is null");
				return 1;
			}
		} else {
			System.out.println("Publisher ID to delete is invalid");
			return 1;
		}
	}

	/**
	 * This method updates publisher information
	 * 
	 * @param id         key value of publisher object
	 * @param newName    new publisher name to update existing name
	 * @param newAddress new publisher address to update existing address
	 * @return 0 for success, 1 for failure
	 */
	public int update(int id, String newName, String newAddress) {
		Publisher publisher; // for type-casting IDObject results

		System.out.println();
		System.out.println("Update:");

		// checks to see if publisher with ID exists in data map
		// also check whether the new publisher already exists
		boolean oldNameExists = dataMap.entrySet().stream().anyMatch(x -> x.getKey() == id);
		boolean newNameExists = dataMap.entrySet().stream().anyMatch(x -> x.getValue().getName().equals(newName));

		// updates publisher if name it exists, and name and address values are valid;
		// otherwise, display error
		// names must be between 2-40 characters
		// addresses must be between 2-40 characters
		if (oldNameExists && !newNameExists && newName != null && newName.length() > 1 && newName.length() <= 40
				&& newAddress != null && newAddress.length() > 1 && newAddress.length() <= 40) {
			publisher = (Publisher) dataMap.get(id);
			publisher.setName(newName);
			publisher.setPublisherAddress(newAddress);
			return 0;
		} else {
			System.out.println("Invalid update");
			System.out.println("Names & addresses must be between 2-40 characters in length. "
					+ "Updated publisher names cannot already exist");
			System.out.println();
			return 1;
		}
	}

}
