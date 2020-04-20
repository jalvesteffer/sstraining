package com.smoothstack.training.wk1.project;

import java.util.ArrayList;
import java.util.List;

/**
 * This class extends the basic map storage of the DataStore class and provides
 * additional CRUD operations over IDOBjects that are specifically Book objects
 * 
 * @author jalveste
 *
 */
public class BookStore extends DataStore {

	/**
	 * This method displays all books stored in the book map with their id numbers,
	 * associated author(s), and publisher info
	 * 
	 * @param authStore used to access author info for book
	 * @param pubStore  used to access publisher info for book
	 * @return number of records output
	 */
	public int read(AuthorStore authStore, PublisherStore pubStore) {

		Book book; // used for receiving type-cast IDObject
		Publisher publisher; // used for receiving type-cast IDObject
		int numOutput = 0; // keep track of how many records output for testing purposes

		System.out.println();
		System.out.println("Read:");

		// loop through the book map objects, outputting one per line
		for (IDObject o : dataMap.values()) {
			book = (Book) o;
			System.out.println("[" + o.getId() + "]\t" + o.getName());
			System.out.print("\t");

			// prints books authors as comma-separated list
			printAuthorList(book.getAuthorIDList(), authStore);

			// get associated book publisher info and display it
			publisher = (Publisher) pubStore.getIDObject(book.getPublisherID());
			if (publisher != null) {
				System.out.println("\t" + publisher.getName() + " (" + publisher.getPublisherAddress() + ")");
				System.out.println();
				numOutput++;
			}

		}

		return numOutput;
	}

	/**
	 * This method deletes all books in the book map having a given publisher ID
	 * 
	 * @param publisherID all books having this publisher ID are to be deleted
	 * @return number of books for deletion
	 */
	public int deleteBooksFromPublisher(int publisherID) {
		Book book; // used for receiving type-cast IDObject
		List<Integer> keysToDelete = new ArrayList<>();

		// create a list of book keys matching a deleted publisher id
		for (IDObject o : dataMap.values()) {
			book = (Book) o;
			if (book != null && book.getPublisherID() == publisherID) {
				System.out.println("Deleting associated book: " + book.getName());
				keysToDelete.add(book.getId());
			}
		}

		// delete the list of books whose publisher has been deleted
		keysToDelete.stream().forEach(x -> this.delete(x));

		return keysToDelete.size();
	}

	/**
	 * This method deletes all books in the book map having a given author ID
	 * 
	 * @param authorID all books having this author ID are to be deleted
	 * @return number of books for deletion
	 */
	public int deleteBooksFromAuthor(int authorID) {
		Book book; // used for receiving type-cast IDObject
		List<Integer> keysToDelete = new ArrayList<>();

		// loop through all books in the book map
		for (IDObject o : dataMap.values()) {
			book = (Book) o;

			// this nested loop looks through each books author lists for matches
			// since books are allowed to have more than one author
			for (Integer i : book.getAuthorIDList()) {
				// create a list of book keys matching a deleted author id
				if (i == authorID) {
					System.out.println("Deleting associated book: " + book.getName());
					keysToDelete.add(book.getId());
					break;
				}
			}
		}

		// delete the list of books whose publisher has been deleted
		keysToDelete.stream().forEach(x -> this.delete(x));

		return keysToDelete.size();
	}

	/**
	 * Prints the list of comma-separated author names to console from IDs in
	 * authorList
	 * 
	 * @param authorList list of author ID keys
	 * @param authStore  author map object
	 * @return number of authors printed
	 */
	public int printAuthorList(ArrayList<Integer> authorList, AuthorStore authStore) {
		int counter = 0; // keep track of how many authors printed

		// fail if arguments null
		if (authorList == null || authStore == null) {
			System.out.println("printAuthorList method has null arguments");
			return -1;
		}

		System.out.print("By ");

		// loop through a books author list
		for (int authID : authorList) {
			System.out.print(authStore.getIDObject(authID).getName());

			// if there are more than one author, separate names by commas
			counter++;
			if (counter < authorList.size()) {
				System.out.print(", ");
			}
		}
		System.out.println();

		return counter;
	}

	/**
	 * This method updates a book title for a specified book id
	 * 
	 * @param bookID   id of book to update title
	 * @param newTitle new title for book
	 * @return 0 for success; -1 for failure
	 */
	public int update(int bookID, String newTitle) {

		// method fails when the newTitle is null or is not between 2-50 characters
		if (newTitle == null || newTitle.length() < 2 || newTitle.length() > 50) {
			System.out.println("Invalid title name");
			return -1;
		}

		// get book with matching bookID and update the title if its not null
		IDObject obj = getIDObject(bookID);
		if (obj != null) {
			obj.setName(newTitle);
			return 0;
		} else {
			System.out.println("Bad book ID for update");
			return -1;
		}
	}

	/**
	 * This method creates a new book from strings received from the user terminal.
	 * The strings are parsed and all necessary associated objects created to
	 * maintain referential integrity in the stored data.
	 * 
	 * @param bookTitle        String containing book title
	 * @param authorsList      List of authors for book. Can be a String or number
	 *                         ID
	 * @param publisherName    Publisher name for book. Can be a String or number ID
	 * @param publisherAddress Publisher address for book as a String
	 * @param authStore        Provides access to author data
	 * @param pubStore         provides access to publisher data
	 * @return 1 on success; -1 on failure
	 */
	public int create(String bookTitle, ArrayList<String> authorsList, String publisherName, String publisherAddress,
			AuthorStore authStore, PublisherStore pubStore) {
		int intIDResult = -1; // temporary storage for integer results
		IDObject objResult = null; // temperary storage for IDObject results
		boolean usingPubID = false; // flag to indicate whether publisher is an ID or a String

		// flag to determine whether the entire book can be created without generating
		// an error
		boolean canCommit = true;

		Book bookObj; // new book object to be added to BookStore if create successful
		Publisher pubObj; // used for type-casting of IDObject result
		ArrayList<Integer> currAuthorIDList = new ArrayList<>(); // holds valid author IDs as they are verified
		ArrayList<String> currAuthorNameList = new ArrayList<>(); // holds new author names to be created later

		// make sure none of the arguments are null
		if (bookTitle == null || authorsList == null || publisherName == null || authStore == null
				|| pubStore == null) {
			System.out.println("Error: BookStore create method has one or more null arguments");
			return -1;
		}

		// make sure none of the arguments are null
		if (bookTitle.length() < 1 || bookTitle.length() > 50 || publisherName.length() < 1
				|| publisherName.length() > 40) {
			System.out.println(
					"Error: BookStore create method received String arguments with improper character lengths");
			return -1;
		}

		// Author processing
		// more than one author is possible; loop through the one or more authors
		for (String s : authorsList) {

			// try to parse authors as an integer. If there is no exception, then the number
			// is interpreted as an author ID key. It is added to an integer list of author
			// IDs
			// if the parsing fails, the input is interpreted as a new author name. It is
			// added to a String list of new author names to be created
			try {
				intIDResult = Integer.parseInt(s);
				objResult = authStore.getIDObject(intIDResult);
				if (objResult != null) {
					currAuthorIDList.add(objResult.getId());
				} else {
					System.out.println("Author reference ID invalid");
					canCommit = false;
				}
			} catch (NumberFormatException e) {
				// Not an ID, interpreting as a new author name
				currAuthorNameList.add(s);
			}

		}

		// Publisher processing
		// try to parse publisher as an integer. If there is no exception, then the
		// number
		// is interpreted as an publisher ID key.
		// if the parsing fails, the input is interpreted as a new author name.
		try {
			intIDResult = Integer.parseInt(publisherName);
			objResult = pubStore.getIDObject(intIDResult);
			if (objResult != null) {
				usingPubID = true; // publisher ID detected
			} else {
				System.out.println("Publisher reference ID invalid");
				canCommit = false;
			}
		} catch (NumberFormatException e) {
			// Not an ID, interpreting as a new publisher name
			if (publisherAddress == null)
				canCommit = false; // error: new publisher without address
		}

		// check to see if bookTitle is unique
		List<Integer> results = getKey(bookTitle);

		if (results.size() == 0) {
//			System.out.println("Book title unique");
		} else {
			System.out.println("Book already exists, check publishers");

			// if the book title is not unique, check to see if the publishers are
			// identical.
			// if so, the book is determined to be a duplicate and book creation not happen
			if (canCommit && usingPubID) {
				bookObj = (Book) getIDObject(results.get(0));
				if (bookObj != null && (bookObj.getPublisherID() == intIDResult)) {
					System.out.println("Book is a duplicate... Ignored");
					canCommit = false;
				}
			} else if (canCommit && !usingPubID) {
				pubObj = (Publisher) pubStore.getKey(publisherName);
				if (pubObj != null && (pubObj.getName() == publisherName)) {
					System.out.println("Book is a duplicate... Ignored");
					canCommit = false;
				}
			}
		}

		// all the book data appears valid. Book creation can be committed
		if (canCommit) {
			int nameID = -1; // holds id of a newly created Author
			int pubID = -1; // holds id of a newly created publisher

			System.out.println();
			System.out.println("Write:");

			// Author creation from name list
			for (String nameStr : currAuthorNameList) {
				nameID = authStore.create(nameStr);
				if (nameID != -1) {
					currAuthorIDList.add(nameID);
					System.out.println("Added: " + nameStr + " with ID: " + nameID);
				}
			}

			// Publisher creation
			// Note: duplicates and a null publisherAddress were already checked earlier
			// to determine canCommit flag
			if (!usingPubID) {
				pubID = pubStore.create(publisherName, publisherAddress);

				if (pubID != -1) {
					System.out.println("Added: " + publisherName + " (" + publisherAddress + ") with ID: " + pubID);
				}
			} else {
				pubID = intIDResult; // use existing publisher ID from earlier
			}

			// Book creation
			Book newBook = new Book(++lastID, bookTitle, pubID, currAuthorIDList);
			dataMap.put(lastID, newBook);
			System.out.println("Added new book with title: " + bookTitle);
			System.out.println();

			return 0;
		}

		return -1;

	}

}
