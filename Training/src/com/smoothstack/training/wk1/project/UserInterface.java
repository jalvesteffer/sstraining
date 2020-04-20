package com.smoothstack.training.wk1.project;

import java.util.ArrayList;

/**
 * This class implements the console user interface for this program. It also
 * servers as a program driver
 * 
 * @author jalveste
 *
 */
public class UserInterface {

	public static void main(String[] args) {

		// create a new UserInterface object and start it
		UserInterface ui = new UserInterface();
		ui.start();

	}

	// data stores for the application
	private AuthorStore authorStore;
	private PublisherStore publisherStore;
	private BookStore bookStore;

	// input file paths
	private final String authorFilePath = "resources/authors.txt";
	private final String publisherFilePath = "resources/publishers.txt";
	private final String bookFilePath = "resources/books.txt";

	// remembers menu option selections during navigation
	private int fileChoice = 0;
	private int operationChoice = 0;

	/**
	 * This class constructor creates new data storage map objects for use with this
	 * program
	 */
	public UserInterface() {
		authorStore = new AuthorStore();
		publisherStore = new PublisherStore();
		bookStore = new BookStore();
	}

	/**
	 * This class starts the user interface, reads the files into the data maps and
	 * displays the main menu
	 */
	public void start() {
		// program title
		System.out.println("LIBRARY MANAGEMENT SYSTEM");

		// launches the main menu
		mainMenu();
	}

	/**
	 * Represents the main menu in the user interface. Shows the three file options
	 * to perform CRUD operations on as well as two exit options
	 */
	public void mainMenu() {
		UserInput userInput = new UserInput(); // for user in getting user input
		int choice = 0; // holds user menu choice

		System.out.println();
		System.out.println("Main Menu");
		System.out.println();

		// loop while the use has not chosen the exit options
		while (fileChoice != 4 && fileChoice != 5) {

			// Display menu options
			System.out.println("Which file do you with to edit?");
			System.out.println();
			System.out.println("[1] Authors");
			System.out.println("[2] Publishers");
			System.out.println("[3] Books");
			System.out.println();
			System.out.println("[4] Exit without Saving Changes");
			System.out.println("[5] Save Changes & Exit");
			System.out.println();
			choice = userInput.askForInt("Enter a number from 1 - 5 and press enter to make your selection:");

			fileChoice = choice;

			switch (fileChoice) {
			case 1: // Authors menu
				authorsMenu();
				fileChoice = 0;
				break;
			case 2: // Publishers menu
				publishersMenu();
				fileChoice = 0;
				break;
			case 3: // Books menu
				booksMenu();
				fileChoice = 0;
				break;
			case 4: // Exit without Saving Changes
				System.out.println("Goodbye");
				return;
			case 5: // Save Changes & Exit
				authorStore.writeToFile(authorFilePath);
				publisherStore.writeToFile(publisherFilePath);
				bookStore.writeToFile(bookFilePath);
				System.out.println("Changes Saved... Goodbye");
			}
		}
	}

	/**
	 * Represents the Publishers menu in the user interface. Users can perform CRUD
	 * operations from here.
	 */
	private void publishersMenu() {
		UserInput userInput = new UserInput(); // for getting user input

		// menu choice selected by user
		int choice = 0;

		// holds user responses from console
		int userInt = 0;
		String userStr1 = null;
		String userStr2 = null;

		System.out.println();
		System.out.println("Publishers Menu");
		System.out.println();

		// loop while user does not select back to main menu option
		while (operationChoice != 5) {

			// Display menu options
			System.out.println("Which operation do you wish to perform?");
			System.out.println();
			System.out.println("[1] Create");
			System.out.println("[2] Read");
			System.out.println("[3] Update");
			System.out.println("[4] Delete");
			System.out.println();
			System.out.println("[5] Back to Main Menu");
			System.out.println();
			choice = userInput.askForInt("Enter a number from 1 - 5 and press enter to make your selection:");

			operationChoice = choice;

			switch (operationChoice) {
			case 1: // Create
				// Asks user for publisher name and address
				userStr1 = userInput.askForString("Enter a new publisher name:");
				userStr2 = userInput.askForString("Enter the publishers address (City, Country):");

				// call PublisherStore create method
				publisherStore.create(userStr1, userStr2);
				operationChoice = 0;
				break;
			case 2: // Read
				// call PublisherStore read method that displays all its contents to console
				publisherStore.read();
				operationChoice = 0;
				break;
			case 3: // Update
				// display all publishers then ask user for an ID to update and their updated
				// publisher info
				publisherStore.read();
				userInt = userInput.askForInt("Enter an publisher ID to update:");
				userStr1 = userInput.askForString("Enter your modified publisher name:");
				userStr2 = userInput.askForString("Enter your modified publisher address:");

				// call PublisherStore method to update publisher
				publisherStore.update(userInt, userStr1, userStr2);
				break;
			case 4: // Delete
				// display all publishers and ask user to enter ID to delete
				publisherStore.read();
				userInt = userInput.askForInt("Enter an publisher ID to remove:");

				// call Publisher store method to perfrom deletion
				publisherStore.delete(userInt, bookStore);
				break;
			case 5: // Back to Main Menu
				operationChoice = 0;
				return;
			}
		}

	}

	/**
	 * Represents the Books menu in the user interface. Users can perform CRUD
	 * operations from here.
	 */
	private void booksMenu() {
		UserInput userInput = new UserInput(); // for getting user input
		int choice = 0; // holds current menu choice
		int userInt = 0; // holds user response

		System.out.println();
		System.out.println("Books Menu");
		System.out.println();

		// loop while user does not select back to main menu option
		while (operationChoice != 5) {

			// Display menu options
			System.out.println("Which operation do you wish to perform?");
			System.out.println();
			System.out.println("[1] Create");
			System.out.println("[2] Read");
			System.out.println("[3] Update");
			System.out.println("[4] Delete");
			System.out.println();
			System.out.println("[5] Back to Main Menu");
			System.out.println();
			choice = userInput.askForInt("Enter a number from 1 - 5 and press enter to make your selection:");

			operationChoice = choice;

			switch (operationChoice) {
			case 1: // Create
				createBook();
				operationChoice = 0;
				break;
			case 2: // Read
				bookStore.read(getAuthorStore(), getPublisherStore());
				operationChoice = 0;
				break;
			case 3: // Update
				// display list of all books and back menu option
				bookStore.read(getAuthorStore(), getPublisherStore());
				System.out.println("[0] Back to Main Menu");
				System.out.println();

				// ask user which book ID they want to update; 0 goes back
				userInt = userInput.askForInt("Enter a Book ID to update:");
				if (userInt == 0)
					return;

				// get the book object for the requested ID and pass it to the book update menu
				// method
				Book bookObj = (Book) bookStore.getIDObject(userInt);
				if (bookObj != null) {
					bookUpdateMenu(bookObj);
				}
				operationChoice = 0;
				break;
			case 4: // Delete
				// display all books and ask user for book id to remove
				bookStore.read(authorStore, publisherStore);
				userInt = userInput.askForInt("Enter an book ID to remove:");

				// call BookStore delete method for deletion
				bookStore.delete(userInt);
				break;
			case 5:
				operationChoice = 0;
				return;
			}
		}

	}

	/**
	 * Represents the Author menu in the user interface. Users can perform CRUD
	 * operations from here.
	 */
	public void authorsMenu() {
		UserInput userInput = new UserInput();
		int choice = 0;
		int userInt = 0;
		String userStr = null;

		System.out.println();
		System.out.println("Authors Menu");
		System.out.println();

		// loop while user does not select back to main menu option
		while (operationChoice != 5) {

			// Display menu options
			System.out.println("Which operation do you wish to perform?");
			System.out.println();
			System.out.println("[1] Create");
			System.out.println("[2] Read");
			System.out.println("[3] Update");
			System.out.println("[4] Delete");
			System.out.println();
			System.out.println("[5] Back to Main Menu");
			System.out.println();
			choice = userInput.askForInt("Enter a number from 1 - 5 and press enter to make your selection:");

			operationChoice = choice;

			switch (operationChoice) {
			case 1: // Create
				// ask user for new author name and call on AuthorStore method to create it
				userStr = userInput.askForString("Enter new author name:");
				authorStore.create(userStr);
				operationChoice = 0;
				break;
			case 2: // Read
				// display all authors
				authorStore.read();
				operationChoice = 0;
				break;
			case 3: // Update
				// displays all current authors and asks for an ID to update, followed by what
				// the new author name should be
				authorStore.read();
				userInt = userInput.askForInt("Enter an author ID to update:");
				userStr = userInput.askForString("Enter your modified author name:");

				// call AuthorStore update method to perform update
				authorStore.update(userInt, userStr);
				operationChoice = 0;
				break;
			case 4: // Delete
				// display all authors and ask for an author id to delete
				authorStore.read();
				userInt = userInput.askForInt("Enter an author ID to remove:");

				// call on AuthorStore method for deletion
				authorStore.delete(userInt, bookStore);
				operationChoice = 0;
				break;
			case 5:
				operationChoice = 0;
				return;
			}
		}
	}

	/**
	 * This method provides the interface for creating a new book
	 */
	private void createBook() {
		String title; // book title
		String author; // holds author name string before adding to author list
		String pubName; // book publisher name
		String pubAddress = null; // book publisher address

		// list to handle multiple authors
		ArrayList<String> authorsList = new ArrayList<>();

		// object for getting user input
		UserInput userInput = new UserInput();

		// get book title
		title = userInput.askForString("Enter the book title:");

		// display all authors and ask user to reference an ID or type in a new author
		// name
		authorStore.read();
		System.out.println("[0] Go Back");
		System.out.println();
		author = userInput.askForString("Enter an existing [ID] number shown above or type a new author name:");

		// if 0, then user wants to exit this menu
		if ("0".equals(author)) {
			System.out.println();
			return;
		}
		authorsList.add(author); // add author to author list

		// this loop keeps asking user for more author names until they enter 0
		do {
			System.out.println(
					"If there are more co-authors yet not entered, enter an existing [ID] number shown above or type a new author name");
			author = userInput.askForString("If there are no more authors to enter for this book, type [0]:");
			if (!"0".equals(author)) {
				authorsList.add(author);
			}
		} while (!"0".equals(author));

		// display all publisher names and ask user to enter an id or type a new
		// publisher name
		publisherStore.read();
		pubName = userInput.askForString("Enter an existing [ID] number shown above or type a new publisher name:");

		// see if pubName is an integer. If not, catch the exception and ask for
		// publisher address
		try {
			Integer.parseInt(pubName);
		} catch (NumberFormatException e) {
			pubAddress = userInput.askForString("Enter the address of the publisher (City, Country):");
		}

		// try to create the book
		bookStore.create(title, authorsList, pubName, pubAddress, authorStore, publisherStore);
	}

	/**
	 * This method allows user to update a book
	 * 
	 * @param book book to update
	 */
	private void bookUpdateMenu(Book book) {
		// object for getting user input
		UserInput userInput = new UserInput();

		// for remembering menu choice
		int choice = 0;

		// temporary variables to hold user input
		int userInt = 0;
		String userStr1 = null;
		String userStr2 = null;

		Publisher pubObj; // used for type-casting IDObject

		System.out.println();
		System.out.println("Book Update Menu");
		System.out.println();

		// loop while user does not select back to main menu option
		while (operationChoice != 5) {

			// Display menu options
			// some information requires addition object queries to obtain
			System.out.println("Which part of the book information do you wish to update?");
			System.out.println();
			System.out.println("[1] " + book.getName());
			System.out.print("[2] ");
			bookStore.printAuthorList(book.getAuthorIDList(), authorStore);
			System.out.print("[3] ");
			pubObj = (Publisher) publisherStore.getIDObject(book.getPublisherID());
			publisherStore.printPublisherInfo(pubObj);
			System.out.println();
			System.out.println("[0] Back to Main Menu");
			System.out.println();

			// ask user which part of book record to update
			choice = userInput.askForInt("Enter 1 - 3 to make your selection or 0 to go back:");

			operationChoice = choice;

			switch (operationChoice) {
			case 1: // update Title
				userStr1 = userInput.askForString("Enter new book title:");
				bookStore.update(book.getId(), userStr1);
				operationChoice = 0;
				break;
			case 2: // update Author
				// Displays one or more authors for book and asks for ID of author to update
				System.out.println();
				System.out.println("Authors for " + book.getName());
				System.out.println();
				authorStore.read(book.getAuthorIDList());
				System.out.println();
				System.out.println("[0] Go Back");
				System.out.println();

				userInt = userInput.askForInt("Enter an author ID to update:");
	
				// if user enters 0, exit this menu
				if (userInt == 0) {
					System.out.println();
					return;
				}

				// ask for another ID until the entered author ID is in books author ID list
				while (userInt != 0 && !book.getAuthorIDList().contains(userInt)) {

					System.out.println("Invalid author ID.  Try again:");
					userInt = userInput.askForInt("Enter an author ID to update:");

					// allow user to exit this menu by entering 0
					if (userInt == 0) {
						System.out.println();
						return;
					}
				}

				// ask for new author name and call AuthorStore method to perform update
				userStr1 = userInput.askForString("Enter your modified author name:");
				authorStore.update(userInt, userStr1);
				operationChoice = 0;
				break;
			case 3: // update Publisher
				// ask for new publisher info and call on PublisherStore update method
				userStr1 = userInput.askForString("Enter your modified publisher name:");
				userStr2 = userInput.askForString("Enter your modified publisher address:");
				publisherStore.update(book.getPublisherID(), userStr1, userStr2);
				operationChoice = 0;
				break;
			case 0:
				operationChoice = 0;
				return;
			}
		}
	}

	/**
	 * @return the authorStore
	 */
	public AuthorStore getAuthorStore() {
		return authorStore;
	}

	/**
	 * @return the publisherStore
	 */
	public PublisherStore getPublisherStore() {
		return publisherStore;
	}

}
