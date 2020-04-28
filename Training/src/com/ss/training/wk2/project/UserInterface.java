/**
 * 
 */
package com.ss.training.wk2.project;

import static org.hamcrest.CoreMatchers.containsString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.ss.training.wk2.project.entity.Author;
import com.ss.training.wk2.project.entity.Book;
import com.ss.training.wk2.project.entity.BookLoans;
import com.ss.training.wk2.project.entity.Borrower;
import com.ss.training.wk2.project.entity.Branch;
import com.ss.training.wk2.project.entity.Genre;
import com.ss.training.wk2.project.entity.Publisher;
import com.ss.training.wk2.project.service.AdminService;
import com.ss.training.wk2.project.service.BorrowerService;
import com.ss.training.wk2.project.service.LibrarianService;

/**
 * @author jalveste
 *
 */
public class UserInterface {

	// provides access to database
	private AdminService adminService;
	private LibrarianService librarianService;
	private BorrowerService borrowerService;

	// logged in user to this UI
	private Integer cardNo = 0;

	/**
	 * @param adminService
	 */
	public UserInterface() {
		// create new service objects
		adminService = new AdminService();
		librarianService = new LibrarianService();
		borrowerService = new BorrowerService();
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		UserInterface ui = new UserInterface();
		ui.start();

	}

	/**
	 * This methods starts the UI and runs the program
	 */
	public void start() {
		// program title
		System.out.println("LIBRARY MANAGEMENT SYSTEM");

		// launches the main menu
		mainMenu();

		// exit program
		System.out.println("\nGoodbye");

	}

	/**
	 * Main Menu
	 * 
	 */
	public void mainMenu() {
		// variables for console ui navigation
		OptionMenu optMenu = new OptionMenu();
		MenuChoice choice = new MenuChoice();
		
		
		
	
		
		
		
		
		

		// set display menu method argument here
		String prompt = "\nMAIN:\nWelcome to the GCIT Library Management System. Which category of a user are you?";
		List<String> optList = new ArrayList<>();
		optList.add("Librarian");
		optList.add("Administrator");
		optList.add("Borrower");

		// repeatedly loop menu until user chooses to exit
		while (choice != null) {

			// display menu options and remember choice
			choice = optMenu.displayMainMenu(prompt, optList);

			// exit on null choice
			if (choice == null) {
				return;
			}

			// execute next step according to user selection
			switch (choice.getId()) {
			case 0: // exit program
				return;
			case 1: // go to librarian menu
				libOneMenu();
				break;
			case 2: // go to admin menu
				admOneMenu();
				break;
			case 3: // go to borrower menu
				if (!passValidation()) {
					System.out.println("\nCard number is not valid.");
					cardNo = 0; // reset cardNo
					continue;
				} else {
					System.out.println("Card number is valid. Granting access...");
				}
				borrOneMenu();
				break;
			default:
				System.out.println("ERROR: User choice not recognized");
				choice = null;
			}

		}

	}

	/**
	 * Main Administrator console view
	 * 
	 */
	public void admOneMenu() {
		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// set display menu method argument here
		List<String> opt = new ArrayList<>();
		opt.add("Edit Books");
		opt.add("Edit Authors");
		opt.add("Edit Genres");
		opt.add("Edit Publishers");
		opt.add("Edit Library Branches");
		opt.add("Edit Borrowers");
		opt.add("Override Due Dates");

		// repeatedly loop menu until user chooses to Quit
		while (choice != null) {

			// display menu options and remember choice
			choice = optMenu.displayMenuWithPrevious("\nSelect an administrator function:", opt);
			if (choice == null) {
				return;
			}

			// execute next step according to user selection
			switch (choice.getId()) {
			case 1: // edit books
				admTwoMenuBooks();
				break;
			case 2: // edit authors
				admTwoMenuAuthors();
				break;
			case 3: // edit genres
				admTwoMenuGenres();
			case 4: // edit publishers
				admTwoMenuPublishers();
				break;
			case 5: // edit library branches
				admTwoMenuBranches();
				break;
			case 6: // edit borrowers
				admTwoMenuBorrowers();
				break;
			case 7: // edit borrowers
				admTwoMenuOverride();
				break;
			default:
				System.out.println("ERROR: User choice not recognized");
				choice = null;
			}
		}
	}

	/**
	 * OVERRIDE DUE-DATE
	 * 
	 * This method shows over-due books and extends the due date by 7 days
	 */
	public void admTwoMenuOverride() {

		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();
		String optStr = "";
		Book book;
		BookLoans bookLoan;

		// holds the result of a query
		List<String> optList = new ArrayList<>();

		// holds the result of a query
		List<BookLoans> result = null;

		while (choice != null) {

			// query the database for all books
			try {
				result = adminService.getBookLoansDue();
			} catch (SQLException e) {
				System.out.println("Read books librarian service failed");
				e.printStackTrace();
			}

			
			for (BookLoans loan : result)	{
				
				try {
					book = adminService.getBookById(loan.getBookId());
					if (book != null)	{
						optStr = optStr.concat(book.getTitle()).concat("\n\tDue Date: ").concat(loan.getDueDate()).concat("\n\tCard No: ").concat(loan.getCardNo().toString());
					}
				} catch (SQLException e) {
					System.out.println("Error getting book in adminService");
				}
				
				optStr = optStr.concat("\n");
				optList.add(optStr);
				optStr = "";
			}
			

			// pass the constructed list to serve as menu options.
			// ask user to make a choice, and make sure that choice is not null
			choice = optMenu.displayMenuWithPrevious("\nSelect overdue books to extend due date:", optList);
			if (choice == null) {
				return;
			}

			// try to get the object matching user's choice.
			// index needs to be changed to a zero-based index, so index - 1
			// pass the object to helper function
			if (choice.getId() != null) {
				bookLoan = result.get(choice.getId() - 1);

				extendBookLoan(bookLoan);
				break;
			}
		}
	}
	
	
	public void extendBookLoan(BookLoans bookLoan)	{
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		LocalDateTime dateTimeDueDate = LocalDateTime.parse(bookLoan.getDueDate(), formatter);
		LocalDateTime dateTimeDueDateExtended = dateTimeDueDate.plusDays(7);
		String strDueDateExtended = dateTimeDueDateExtended.format(formatter);
		
		try {
			adminService.updateDueDate(bookLoan, strDueDateExtended);
		} catch (SQLException e) {
			System.out.println("updateDueDate failed");
		}
		
		System.out.println("\nThe new extended due date is: " + strDueDateExtended);
	}
	
	
	/**
	 * Book CRUD operation console view
	 * 
	 */
	public void admTwoMenuBooks() {
		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// set display menu method argument here
		List<String> opt = new ArrayList<>();
		opt.add("Add");
		opt.add("Update");
		opt.add("Delete");
		opt.add("Read");

		// repeatedly loop menu until user chooses to Quit
		while (choice != null) {

			// display menu options and remember choice
			choice = optMenu.displayMenuWithPrevious("\nSelect a book function:", opt);
			if (choice == null) {
				return;
			}

			// execute next step according to user selection
			switch (choice.getId()) {
			case 1: // insert function
				admTwoMenuBooksAdd();
				break;
			case 2: // update function
				admTwoMenuBooksUpdate();
				break;
			case 3: // deletion function
				admTwoMenuBooksDelete();
				break;
			case 4: // read function
				admTwoMenuBooksRead();
				break;
			default:
				System.out.println("ERROR: User choice not recognized");
				choice = null;
			}
		}
	}

	/**
	 * Branch CRUD operation console view
	 * 
	 */
	public void admTwoMenuBranches() {
		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// set display menu method argument here
		List<String> opt = new ArrayList<>();
		opt.add("Add");
		opt.add("Update");
		opt.add("Delete");
		opt.add("Read");

		// repeatedly loop menu until user chooses to Quit
		while (choice != null) {

			// display menu options and remember choice
			choice = optMenu.displayMenuWithPrevious("\nSelect a library branch function:", opt);
			if (choice == null) {
				return;
			}

			// execute next step according to user selection
			switch (choice.getId()) {
			case 1: // insert function
				admTwoMenuBranchesAdd();
				break;
			case 2: // update function
				admTwoMenuBranchesUpdate();
				break;
			case 3: // deletion function
				admTwoMenuBranchesDelete();
				break;
			case 4: // read function
				admTwoMenuBranchesRead();
				break;
			default:
				System.out.println("ERROR: User choice not recognized");
				choice = null;
			}
		}
	}

	/**
	 * Publisher CRUD operation console view
	 * 
	 */
	public void admTwoMenuPublishers() {
		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// set display menu method argument here
		List<String> opt = new ArrayList<>();
		opt.add("Add");
		opt.add("Update");
		opt.add("Delete");
		opt.add("Read");

		// repeatedly loop menu until user chooses to Quit
		while (choice != null) {

			// display menu options and remember choice
			choice = optMenu.displayMenuWithPrevious("\nSelect a publisher function:", opt);
			if (choice == null) {
				return;
			}

			// execute next step according to user selection
			switch (choice.getId()) {
			case 1: // insert function
				admTwoMenuPublishersAdd();
				break;
			case 2: // update function
				admTwoMenuPublishersUpdate();
				break;
			case 3: // deletion function
				admTwoMenuPublishersDelete();
				break;
			case 4: // read function
				admTwoMenuPublishersRead();
				break;
			default:
				System.out.println("ERROR: User choice not recognized");
				choice = null;
			}
		}
	}

	/**
	 * Borrower CRUD operation console view
	 * 
	 */
	public void admTwoMenuBorrowers() {
		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// set display menu method argument here
		List<String> opt = new ArrayList<>();
		opt.add("Add");
		opt.add("Update");
		opt.add("Delete");
		opt.add("Read");

		// repeatedly loop menu until user chooses to Quit
		while (choice != null) {

			// display menu options and remember choice
			choice = optMenu.displayMenuWithPrevious("\nSelect a borrower function:", opt);
			if (choice == null) {
				return;
			}

			// execute next step according to user selection
			switch (choice.getId()) {
			case 1: // insert function
				admTwoMenuBorrowersAdd();
				break;
			case 2: // update function
				admTwoMenuBorrowersUpdate();
				break;
			case 3: // deletion function
				admTwoMenuBorrowersDelete();
				break;
			case 4: // read function
				admTwoMenuBorrowersRead();
				break;
			default:
				System.out.println("ERROR: User choice not recognized");
				choice = null;
			}
		}
	}

	/**
	 * Author CRUD operation console view
	 * 
	 */
	public void admTwoMenuAuthors() {
		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// set display menu method argument here
		List<String> opt = new ArrayList<>();
		opt.add("Add");
		opt.add("Update");
		opt.add("Delete");
		opt.add("Read");

		// repeatedly loop menu until user chooses to Quit
		while (choice != null) {

			// display menu options and remember choice
			choice = optMenu.displayMenuWithPrevious("\nSelect an author function:", opt);
			if (choice == null) {
				return;
			}

			// execute next step according to user selection
			switch (choice.getId()) {
			case 1: // insert function
				admTwoMenuAuthorsAdd();
				break;
			case 2: // update function
				admTwoMenuAuthorsUpdate();
				break;
			case 3: // deletion function
				admTwoMenuAuthorsDelete();
				break;
			case 4: // read function
				admTwoMenuAuthorsRead();
				break;
			default:
				System.out.println("ERROR: User choice not recognized");
				choice = null;
			}
		}
	}

	/**
	 * Genre CRUD operation console view
	 * 
	 */
	public void admTwoMenuGenres() {
		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// set display menu method argument here
		List<String> opt = new ArrayList<>();
		opt.add("Add");
		opt.add("Update");
		opt.add("Delete");
		opt.add("Read");

		// repeatedly loop menu until user chooses to Quit
		while (choice != null) {

			// display menu options and remember choice
			choice = optMenu.displayMenuWithPrevious("\nSelect a genre function:", opt);
			if (choice == null) {
				return;
			}

			// execute next step according to user selection
			switch (choice.getId()) {
			case 1: // insert function
				admTwoMenuGenresAdd();
				break;
			case 2: // update function
				admTwoMenuGenresUpdate();
				break;
			case 3: // deletion function
				admTwoMenuGenresDelete();
				break;
			case 4: // read function
				admTwoMenuGenresRead();
				break;
			default:
				System.out.println("ERROR: User choice not recognized");
				choice = null;
			}
		}
	}

	/**
	 * BOOK DELETE
	 * 
	 * For the Book object type. This method queries the database for a list of
	 * all objects of this type and prompts the user to select one to delete.
	 */
	public void admTwoMenuBooksDelete() {

		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// holds the result of a query
		List<String> optList = new ArrayList<>();

		// holds the result of a query
		List<Book> result = null;

		while (choice != null) {

			// query the database for all books
			try {
				result = adminService.readBooks(null, null);
			} catch (SQLException e) {
				System.out.println("Read books librarian service failed");
				e.printStackTrace();
			}

			// create a String for each option from the query result and store in a list
			optList = makeTitleByAuthorStrings(result);

			// pass the constructed list to serve as menu options.
			// ask user to make a choice, and make sure that choice is not null
			choice = optMenu.displayMenuWithPrevious("\nSelect book to delete:", optList);
			if (choice == null) {
				return;
			}

			// try to get the object matching user's choice.
			// index needs to be changed to a zero-based index, so index - 1
			// pass the object to helper function
			if (choice.getId() != null) {
				Book book = result.get(choice.getId() - 1);

				book.setTitle(null); // to indicate deletion

				// try to execute the edits
				try {
					adminService.saveBook(book);
				} catch (SQLException e) {
					System.out.println("Save book librarian service failed");
				}
			}
		}
	}
	
	/**
	 * BRANCH DELETE
	 * 
	 * For the Branch object type. This method queries the database for a list of
	 * all objects of this type and prompts the user to select one to delete.
	 */
	public void admTwoMenuBranchesDelete() {

		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// holds the result of a query
		List<String> optList = new ArrayList<>();

		// holds the result of a query
		List<Branch> result = null;

		while (choice != null) {

			// query the database for all branches
			try {
				result = adminService.readBranches(null, null);
			} catch (SQLException e) {
				System.out.println("Read branches librarian service failed");
				e.printStackTrace();
			}

			// create a String for each option from the query result and store in a list
			optList = result.stream().map(x -> x.getBranchName()).collect(Collectors.toList());

			// pass the constructed list to serve as menu options.
			// ask user to make a choice, and make sure that choice is not null
			choice = optMenu.displayMenuWithPrevious("\nSelect branch to delete:", optList);
			if (choice == null) {
				return;
			}

			// try to get the object matching user's choice.
			// index needs to be changed to a zero-based index, so index - 1
			// pass the object to helper function
			if (choice.getId() != null) {
				Branch branch = result.get(choice.getId() - 1);

				branch.setBranchName(null); // to indicate deletion

				// try to execute the edits
				try {
					adminService.saveBranch(branch);
				} catch (SQLException e) {
					System.out.println("Save branch librarian service failed");
				}
			}
		}
	}

	/**
	 * PUBLISHER DELETE
	 * 
	 * For the Publisher object type. This method queries the database for a list of
	 * all objects of this type and prompts the user to select one to delete.
	 */
	public void admTwoMenuPublishersDelete() {

		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// holds the result of a query
		List<String> optList = new ArrayList<>();

		// holds the result of a query
		List<Publisher> result = null;

		while (choice != null) {

			// query the database for all publishers
			try {
				result = adminService.readPublishers(null, null);
			} catch (SQLException e) {
				System.out.println("Read authors librarian service failed");
				e.printStackTrace();
			}

			// create a String for each option from the query result and store in a list
			optList = result.stream().map(x -> x.getPublisherName()).collect(Collectors.toList());

			// pass the constructed list to serve as menu options.
			// ask user to make a choice, and make sure that choice is not null
			choice = optMenu.displayMenuWithPrevious("\nSelect publisher to delete:", optList);
			if (choice == null) {
				return;
			}

			// try to get the object matching user's choice.
			// index needs to be changed to a zero-based index, so index - 1
			// pass the object to helper function
			if (choice.getId() != null) {
				Publisher publisher = result.get(choice.getId() - 1);

				publisher.setPublisherName(null); // to indicate deletion

				// try to execute the edits
				try {
					adminService.savePublisher(publisher);
				} catch (SQLException e) {
					System.out.println("Save publisher librarian service failed");
				}
			}
		}
	}

	/**
	 * BORROWER DELETE
	 * 
	 * For the Borrower object type. This method queries the database for a list of
	 * all objects of this type and prompts the user to select one to delete.
	 */
	public void admTwoMenuBorrowersDelete() {

		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// holds the result of a query
		List<String> optList = new ArrayList<>();

		// holds the result of a query
		List<Borrower> result = null;

		while (choice != null) {

			// query the database for all borrowers
			try {
				result = adminService.readBorrowers(null, null);
			} catch (SQLException e) {
				System.out.println("Read authors librarian service failed");
				e.printStackTrace();
			}

			// create a String for each option from the query result and store in a list
			optList = result.stream().map(x -> x.getName()).collect(Collectors.toList());

			// pass the constructed list to serve as menu options.
			// ask user to make a choice, and make sure that choice is not null
			choice = optMenu.displayMenuWithPrevious("\nSelect borrower to delete:", optList);
			if (choice == null) {
				return;
			}

			// try to get the object matching user's choice.
			// index needs to be changed to a zero-based index, so index - 1
			// pass the object to helper function
			if (choice.getId() != null) {
				Borrower borrower = result.get(choice.getId() - 1);

				borrower.setName(null); // to indicate deletion

				// try to execute the edits
				try {
					adminService.saveBorrower(borrower);
				} catch (SQLException e) {
					System.out.println("Save borrower librarian service failed");
				}
			}
		}
	}

	/**
	 * GENRE DELETE
	 * 
	 * For the Genre object type. This method queries the database for a list of all
	 * objects of this type and prompts the user to select one to delete.
	 */
	public void admTwoMenuGenresDelete() {

		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// holds the result of a query
		List<String> optList = new ArrayList<>();

		// holds the result of a query
		List<Genre> result = null;

		while (choice != null) {

			// query the database for all genres
			try {
				result = adminService.readGenres(null, null);
			} catch (SQLException e) {
				System.out.println("Read authors librarian service failed");
				e.printStackTrace();
			}

			// create a String for each option from the query result and store in a list
			optList = result.stream().map(x -> x.getGenreName()).collect(Collectors.toList());

			// pass the constructed list to serve as menu options.
			// ask user to make a choice, and make sure that choice is not null
			choice = optMenu.displayMenuWithPrevious("\nSelect genre to delete:", optList);
			if (choice == null) {
				return;
			}

			// try to get the object matching user's choice.
			// index needs to be changed to a zero-based index, so index - 1
			// pass the object to helper function
			if (choice.getId() != null) {
				Genre genre = result.get(choice.getId() - 1);

				genre.setGenreName(null); // to indicate deletion

				// try to execute the edits
				try {
					adminService.saveGenre(genre);
				} catch (SQLException e) {
					System.out.println("Save genre librarian service failed");
				}
			}
		}
	}

	/**
	 * AUTHOR DELETE
	 * 
	 * For the Author object type. This method queries the database for a list of
	 * all objects of this type and prompts the user to select one to delete.
	 */
	public void admTwoMenuAuthorsDelete() {

		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// holds the result of a query
		List<String> optList = new ArrayList<>();

		// holds the result of a query
		List<Author> result = null;

		while (choice != null) {

			// query the database for all authors
			try {
				result = adminService.readAuthors(null, null);
			} catch (SQLException e) {
				System.out.println("Read authors librarian service failed");
				e.printStackTrace();
			}

			// create a String for each option from the query result and store in a list
			optList = result.stream().map(x -> x.getAuthorName()).collect(Collectors.toList());

			// pass the constructed list to serve as menu options.
			// ask user to make a choice, and make sure that choice is not null
			choice = optMenu.displayMenuWithPrevious("\nSelect author to delete:", optList);
			if (choice == null) {
				return;
			}

			// try to get the object matching user's choice.
			// index needs to be changed to a zero-based index, so index - 1
			// pass the object to helper function
			if (choice.getId() != null) {
				Author author = result.get(choice.getId() - 1);

				// deleting an author deletes its books as well
				try {
					adminService.deleteBooksByAuthorId(author.getAuthorId());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				author.setAuthorName(null); // to indicate deletion

				// try to execute the edits
				try {
					adminService.saveAuthor(author);
					
				} catch (SQLException e) {
					System.out.println("author deletion librarian service failed");
				}
			}
		}
	}

	/**
	 * BOOKS ADD
	 * 
	 * Inserts new object of the above type into the database
	 */
	public void admTwoMenuBooksAdd() {
		// create a UserInput object to assist in getting user input from console
		UserInput userInput = new UserInput(); // gets user input from console
		String authorStr; // user changes for string
		String edit;
		String decision; // how to handle user input
		Integer bookKey;

		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// create a new object and set its name to the newName
		Book book = new Book();
		Author author = null;
		List<Integer> authorKeys = new ArrayList<>();
		List<Publisher> publishers = null;

		// prompt user for new name
		edit = userInput.askForString("\nPlease enter the book title:");

		// set object name property to new name
		book.setTitle(edit);
		
		try {
			adminService.saveBook(book);
			bookKey = adminService.getBookKey(book.getTitle());
			book.setBookId(bookKey);
		} catch (SQLException e) {
			System.out.println("Error creating book");
		}


		// set display menu method argument here
		List<String> optList = new ArrayList<>();
		List<Author> result = null;

		// query the database for all objects of this type
		try {
			result = adminService.readAuthors(null, null);
		} catch (SQLException e) {
			System.out.println("Read admin service failed");
			e.printStackTrace();
		}

		// create a String for each option from the query result and store in a list
		optList = result.stream().map(x -> x.getAuthorName()).collect(Collectors.toList());

		// just display the options. no selections allowed in read operation
		choice = optMenu.displayMenuWithCustomChoice("\nSelect one or more existing authors for this book here."
				+ "\nYou can create your own authors in the next step.", optList, "Continue to Next Step");

		while (choice != null && optList != null && choice.getId() != optList.size() + 1) {

			author = result.get(choice.getId() - 1);
			authorKeys.add(author.getAuthorId());

			// update lists to show deletion effect
			result.remove(choice.getId() - 1);
			optList.remove(choice.getId() - 1);

			choice = optMenu.displayMenuWithCustomChoice("\nSelect one or more existing authors for this book here."
					+ "\nYou can create your own authors in the next step.", optList, "Continue to Next Step");

		}

		if (authorKeys.size() == 0) {
			// prompt user for new name
			authorStr = userInput.askForString("\nPlease enter a new author for this book:");
			try {
				List<Author> a = adminService.readAuthors(null, authorStr);
				while (a != null && a.size() > 0) {
					authorStr = userInput.askForString(
							"\nAuthor already exists or is invalid.\nPlease enter a new author for this book:");
					a = adminService.readAuthors(null, authorStr);
				}
				author = new Author();
				author.setAuthorName(authorStr);
				adminService.insertAuthor(author);
				Integer key = adminService.getAuthorKey(authorStr);
				authorKeys.add(key);

			} catch (Exception e) {
				System.out.println("Author creation failed");
			}
		}

		authorStr = userInput.askForString("\nEnter any additional authors now or type 'done' to finish:");
		while (authorStr != null && !authorStr.equalsIgnoreCase("done")) {
			try {
				List<Author> a = adminService.readAuthors(null, authorStr);
				if (a != null && a.size() > 0) {
					System.out.println("Name already exists or is invalid.");
				} else {
					author = new Author();
					author.setAuthorName(authorStr);
					adminService.insertAuthor(author);
					Integer key = adminService.getAuthorKey(authorStr);
					authorKeys.add(key);
				}

				authorStr = userInput.askForString("\nEnter any additional authors now or type 'done' to finish:");
			} catch (Exception e) {
				System.out.println("Author creation failed");
			}
		}

//		
//
		
		// query the database for all objects of this type
		try {
			publishers = adminService.readPublishers(null, null);
		} catch (SQLException e) {
			System.out.println("Could not read publishers");
		}

		// create a String for each option from the query result and store in a list
		optList = publishers.stream().map(x -> x.getPublisherName()).collect(Collectors.toList());

		// just display the options. no selections allowed in read operation
		choice = optMenu.displayMenuWithCustomChoice("\nChoose a publisher for this book.", optList,
				"Create a New Publisher");

		Publisher newPublisher;
		String pubName;
		String pubAddr;
		String pubPhone;
		if (choice == null) {
			// create a new publisher
			pubName = userInput.askForString("\nEnter a new publisher name:");

			while (pubName != "quit") {
				try {
					List<Publisher> p = adminService.readPublishers(null, pubName);
					if (p != null && p.size() > 0) {
						System.out.println("Publisher already exists or is invalid.");
					} else {
						pubAddr = userInput.askForString("\nEnter a new publisher address:");
						pubPhone = userInput.askForString("\nEnter a new publisher phone:");

						newPublisher = new Publisher();
						newPublisher.setPublisherName(pubName);
						newPublisher.setPublisherAddress(pubAddr);
						newPublisher.setPublisherPhone(pubPhone);

						adminService.savePublisher(newPublisher);
						Integer key = adminService.getPublisherKey(pubName);
						if (key != null) {
							book.setPubId(key);
							break;
						}
					}
				} catch (Exception e) {
					System.out.println("Error creating new publisher");
				}
				// create a new publisher
				pubName = userInput.askForString("\nEnter a new publisher name:");
			}

		} else {
			int pubId = publishers.get(choice.getId() - 1).getPublisherId();
			book.setPubId(pubId);
		}

		try {
			adminService.saveBook(book);
		} catch (SQLException e) {
			System.out.println("Error creating book");
		}

		for (Integer a : authorKeys) {
			try {
				adminService.insertBookAuthorRelationship(book.getBookId(), a);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		untagBookWithGenres(book);
		tagBookWithGenres(book);

	}
	
	/**
	 * BRANCH ADD
	 * 
	 * Inserts new object of the above type into the database
	 */
	public void admTwoMenuBranchesAdd() {

		// create a UserInput object to assist in getting user input from console
		UserInput userInput = new UserInput();

		// ask user for a new name
		String newName = userInput.askForString("\nPlease enter branch's name:");

		// ask user for a new name
		String newAddress = userInput.askForString("\nPlease enter branch's address:");

		// create a new object and set its name to the newName
		Branch branch = new Branch();
		branch.setBranchName(newName);
		branch.setBranchAddress(newAddress);

		// try to insert the new object into the database
		try {
			adminService.saveBranch(branch);
		} catch (SQLException e1) {
			System.out.println("Error adding branch from admin");
		}
	}

	/**
	 * PUBLISHER ADD
	 * 
	 * Inserts new object of the above type into the database
	 */
	public void admTwoMenuPublishersAdd() {

		// create a UserInput object to assist in getting user input from console
		UserInput userInput = new UserInput();

		// ask user for a new name
		String newName = userInput.askForString("\nPlease enter publisher's name:");

		// ask user for a new name
		String newAddress = userInput.askForString("\nPlease enter publisher's address:");

		// ask user for a new name
		String newPhone = userInput.askForString("\nPlease enter publisher's phone:");

		// create a new object and set its name to the newName
		Publisher publisher = new Publisher();
		publisher.setPublisherName(newName);
		publisher.setPublisherAddress(newAddress);
		publisher.setPublisherPhone(newPhone);

		// try to insert the new object into the database
		try {
			adminService.savePublisher(publisher);
		} catch (SQLException e1) {
			System.out.println("Error adding publisher from admin");
		}
	}

	/**
	 * BORROWER ADD
	 * 
	 * Inserts new object of the above type into the database
	 */
	public void admTwoMenuBorrowersAdd() {

		// create a UserInput object to assist in getting user input from console
		UserInput userInput = new UserInput();

		// ask user for a new name
		String newName = userInput.askForString("\nPlease enter borrower's name:");

		// ask user for a new name
		String newAddress = userInput.askForString("\nPlease enter borrower's address:");

		// ask user for a new name
		String newPhone = userInput.askForString("\nPlease enter borrower's phone:");

		// create a new object and set its name to the newName
		Borrower borrower = new Borrower();
		borrower.setName(newName);
		borrower.setAddress(newAddress);
		borrower.setPhone(newPhone);

		// try to insert the new object into the database
		try {
			adminService.saveBorrower(borrower);
		} catch (SQLException e1) {
			System.out.println("Error adding borrower from admin");
		}
	}

	/**
	 * AUTHOR ADD
	 * 
	 * Inserts new object of the above type into the database
	 */
	public void admTwoMenuAuthorsAdd() {

		// create a UserInput object to assist in getting user input from console
		UserInput userInput = new UserInput();

		// ask user for a new name
		String newName = userInput.askForString("\nPlease enter a new author to add:");

		// create a new object and set its name to the newName
		Author author = new Author();
		author.setAuthorName(newName);

		// try to insert the new object into the database
		try {
			adminService.saveAuthor(author);
		} catch (SQLException e1) {
			System.out.println("Error adding author from admin");
		}
	}

	/**
	 * GENRE ADD
	 * 
	 * Inserts new object of the above type into the database
	 */
	public void admTwoMenuGenresAdd() {

		// create a UserInput object to assist in getting user input from console
		UserInput userInput = new UserInput();

		// ask user for a new name
		String newName = userInput.askForString("\nPlease enter a new genre to add:");

		// create a new object and set its name to the newName
		Genre genre = new Genre();
		genre.setGenreName(newName);

		// try to insert the new object into the database
		try {
			adminService.saveGenre(genre);
		} catch (SQLException e1) {
			System.out.println("Error adding author from admin");
		}
	}

	/**
	 * BOOK UPDATE
	 * 
	 * UI console view for selecting and item to update from a list of results
	 */
	public void admTwoMenuBooksUpdate() {

		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// holds the result of a query
		List<String> optList = new ArrayList<>();

		// holds the result of a query
		List<Book> result = null;

		while (choice != null) {

			// query the database for all books
			try {
				result = adminService.readBooks(null, null);
			} catch (SQLException e) {
				System.out.println("Read books librarian service failed");
				e.printStackTrace();
			}

			// create a String for each option from the query result and store in a list
			optList = makeTitleByAuthorStrings(result);

			// pass the constructed list to serve as menu options.
			// ask user to make a choice, and make sure that choice is not null
			choice = optMenu.displayMenuWithPrevious("\nSelect book to update:", optList);
			if (choice == null) {
				return;
			}

			// try to get the object matching user's choice.
			// index needs to be changed to a zero-based index, so index - 1
			// pass the object to helper function
			if (choice.getId() != null) {
				Book book = result.get(choice.getId() - 1);
				admBooksUpdateMenu(book);
			}
		}
	}

	public void admBooksUpdateMenu(Book book) {

		UserInput userInput = new UserInput(); // gets user input from console
		String edit; // user changes for string
		String decision; // how to handle user input
		List<Publisher> publishers = null;
		Publisher publisher = null;

		setBookTitle(book);

		for (Author a : book.getAuthors()) {
			// prompt user for new name
			edit = userInput.askForString("\nPlease enter a new value for " + a.getAuthorName() + ":");

			// determine how to handle input
			decision = userInput.validateResponse(edit);
			if (decision == "ok") {
				// set object name property to new name
				a.setAuthorName(edit);
			} else if (decision == "quit" || decision == "error") {
				return;
			}

			// try to execute the edit
			try {
				adminService.saveAuthor(a);
			} catch (SQLException e) {
				System.out.println("Save branch librarian service failed");
			}
		}

		try {
			publishers = adminService.readPublishers(book.getPubId(), null);
		} catch (SQLException e1) {
			System.out.println("Publisher query failed in AdminService");
		}

		if (publishers != null && publishers.size() > 0) {
			publisher = publishers.get(0);
		}

		// prompt user for new name
		edit = userInput.askForString("\nPlease enter a new value for " + publisher.getPublisherName() + ":");

		// determine how to handle input
		decision = userInput.validateResponse(edit);
		if (decision == "ok") {
			// set object name property to new name
			publisher.setPublisherName(edit);
		} else if (decision == "quit" || decision == "error") {
			return;
		}

		// try to execute the edit
		try {
			adminService.savePublisher(publisher);
		} catch (SQLException e) {
			System.out.println("Save publisher admin service failed");
		}

		untagBookWithGenres(book);
		tagBookWithGenres(book);

	}

	public void setBookTitle(Book book) {
		UserInput userInput = new UserInput(); // gets user input from console
		String edit; // user changes for string
		String decision; // how to handle user input

		System.out.println("\nUpdate Options: Enter a new value, or type 'N/A' for no change or 'Quit' to cancel");

		// prompt user for new name
		edit = userInput.askForString("\nPlease enter a new value for " + book.getTitle() + ":");

		// determine how to handle input
		decision = userInput.validateResponse(edit);
		if (decision == "ok") {
			// set object name property to new name
			book.setTitle(edit);
		} else if (decision == "quit" || decision == "error") {
			return;
		}

		// try to execute the edit
		try {
			adminService.saveBook(book);
		} catch (SQLException e) {
			System.out.println("Save branch librarian service failed");
		}
	}

	public void untagBookWithGenres(Book book) {
		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		List<Genre> genres = null;
		List<String> optList = null;

		Genre genre = null;

		try {
			genres = adminService.getGenresByBookId(book.getBookId());
		} catch (SQLException e) {
			System.out.println("Get genres by bookId AdminService failed");
		}

		// create list of author names for book
		optList = genres.stream().map(x -> x.getGenreName()).collect(Collectors.toList());

		choice = optMenu.displayMenuWithCustomChoice(
				"\nGenre(s) associated with this book.\nEnter genre number to untag this book from.", optList,
				"Continue with Making Changes");

		while (choice != null && choice.getId() != optList.size() + 1) {

			genre = genres.get(choice.getId() - 1);

			try {
				adminService.removeBookGenreRelationship(genre.getGenreId(), book.getBookId());
			} catch (SQLException e) {
				System.out.println("Genre deletion failed in Admin Service");
			}

			// update lists to show deletion effect
			genres.remove(choice.getId() - 1);
			optList.remove(choice.getId() - 1);

			choice = optMenu.displayMenuWithCustomChoice(
					"\nGenre(s) associated with this book.\nEnter genre number to untag this book from it.", optList,
					"Continue with Making Changes");
		}
	}

	public void tagBookWithGenres(Book book) {
		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		List<Genre> genres = null;
		List<String> optList = null;

		Genre genre = null;

		try {
			genres = adminService.getGenresByNotBookId(book.getBookId());
		} catch (SQLException e) {
			System.out.println("Get genres by bookId AdminService failed");
		}

		// create list of author names for book
		optList = genres.stream().map(x -> x.getGenreName()).collect(Collectors.toList());

		choice = optMenu.displayMenuWithCustomChoice(
				"\nGenre(s) associated with this book.\nEnter genre number to tag this book with.", optList,
				"Continue without Making Changes");

		while (choice != null && choice.getId() != optList.size() + 1) {

			genre = genres.get(choice.getId() - 1);
			genre.setGenreName(null); // null name indicates deletion

			try {
				adminService.insertBookGenreRelationship(genre.getGenreId(), book.getBookId());
			} catch (SQLException e) {
				System.out.println("Book/Genre relationship insertion failed in Admin Service");
			}

			// update lists to show deletion effect
			genres.remove(choice.getId() - 1);
			optList.remove(choice.getId() - 1);

			choice = optMenu.displayMenuWithCustomChoice(
					"\nGenre(s) associated with this book.\nEnter genre number to untag this book from it.", optList,
					"Continue without Making Changes");
		}

	}

	/**
	 * BRANCH UPDATE
	 * 
	 * UI console view for selecting and item to update from a list of results
	 */
	public void admTwoMenuBranchesUpdate() {

		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// holds the result of a query
		List<String> optList = new ArrayList<>();

		// holds the result of a query
		List<Branch> result = null;

		while (choice != null) {

			// query the database for all branches
			try {
				result = adminService.readBranches(null, null);
			} catch (SQLException e) {
				System.out.println("Read branches librarian service failed");
				e.printStackTrace();
			}

			// create a String for each option from the query result and store in a list
			optList = result.stream().map(x -> x.getBranchName()).collect(Collectors.toList());

			// pass the constructed list to serve as menu options.
			// ask user to make a choice, and make sure that choice is not null
			choice = optMenu.displayMenuWithPrevious("\nSelect branch to update:", optList);
			if (choice == null) {
				return;
			}

			// try to get the object matching user's choice.
			// index needs to be changed to a zero-based index, so index - 1
			// pass the object to helper function
			if (choice.getId() != null) {
				Branch branch = result.get(choice.getId() - 1);
				admBranchesUpdate(branch);
			}
		}
	}

	/**
	 * PUBLISHER UPDATE
	 * 
	 * UI console view for selecting and item to update from a list of results
	 */
	public void admTwoMenuPublishersUpdate() {

		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// holds the result of a query
		List<String> optList = new ArrayList<>();

		// holds the result of a query
		List<Publisher> result = null;

		while (choice != null) {

			// query the database for all publishers
			try {
				result = adminService.readPublishers(null, null);
			} catch (SQLException e) {
				System.out.println("Read publishers librarian service failed");
				e.printStackTrace();
			}

			// create a String for each option from the query result and store in a list
			optList = result.stream().map(x -> x.getPublisherName()).collect(Collectors.toList());

			// pass the constructed list to serve as menu options.
			// ask user to make a choice, and make sure that choice is not null
			choice = optMenu.displayMenuWithPrevious("\nSelect publisher to update:", optList);
			if (choice == null) {
				return;
			}

			// try to get the object matching user's choice.
			// index needs to be changed to a zero-based index, so index - 1
			// pass the object to helper function
			if (choice.getId() != null) {
				Publisher publisher = result.get(choice.getId() - 1);
				admPublishersUpdate(publisher);
			}
		}
	}

	/**
	 * BORROWER UPDATE
	 * 
	 * UI console view for selecting and item to update from a list of results
	 */
	public void admTwoMenuBorrowersUpdate() {

		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// holds the result of a query
		List<String> optList = new ArrayList<>();

		// holds the result of a query
		List<Borrower> result = null;

		while (choice != null) {

			// query the database for all borrowers
			try {
				result = adminService.readBorrowers(null, null);
			} catch (SQLException e) {
				System.out.println("Read authors librarian service failed");
				e.printStackTrace();
			}

			// create a String for each option from the query result and store in a list
			optList = result.stream().map(x -> x.getName()).collect(Collectors.toList());

			// pass the constructed list to serve as menu options.
			// ask user to make a choice, and make sure that choice is not null
			choice = optMenu.displayMenuWithPrevious("\nSelect borrower to update:", optList);
			if (choice == null) {
				return;
			}

			// try to get the object matching user's choice.
			// index needs to be changed to a zero-based index, so index - 1
			// pass the object to helper function
			if (choice.getId() != null) {
				Borrower borrower = result.get(choice.getId() - 1);
				admBorrowersUpdate(borrower);
			}
		}
	}

	/**
	 * GENRE UPDATE
	 * 
	 * UI console view for selecting and item to update from a list of results
	 */
	public void admTwoMenuGenresUpdate() {

		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// holds the result of a query
		List<String> optList = new ArrayList<>();

		// holds the result of a query
		List<Genre> result = null;

		while (choice != null) {

			// query the database for all genres
			try {
				result = adminService.readGenres(null, null);
			} catch (SQLException e) {
				System.out.println("Read authors librarian service failed");
				e.printStackTrace();
			}

			// create a String for each option from the query result and store in a list
			optList = result.stream().map(x -> x.getGenreName()).collect(Collectors.toList());

			// pass the constructed list to serve as menu options.
			// ask user to make a choice, and make sure that choice is not null
			choice = optMenu.displayMenuWithPrevious("\nSelect genre to update:", optList);
			if (choice == null) {
				return;
			}

			// try to get the object matching user's choice.
			// index needs to be changed to a zero-based index, so index - 1
			// pass the object to helper function
			if (choice.getId() != null) {
				Genre genre = result.get(choice.getId() - 1);
				admGenresUpdate(genre);
			}
		}
	}

	/**
	 * AUTHOR UPDATE
	 * 
	 * UI console view for selecting and item to update from a list of results
	 */
	public void admTwoMenuAuthorsUpdate() {

		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// holds the result of a query
		List<String> optList = new ArrayList<>();

		// holds the result of a query
		List<Author> result = null;

		while (choice != null) {

			// query the database for all genres
			try {
				result = adminService.readAuthors(null, null);
			} catch (SQLException e) {
				System.out.println("Read authors librarian service failed");
				e.printStackTrace();
			}

			// create a String for each option from the query result and store in a list
			optList = result.stream().map(x -> x.getAuthorName()).collect(Collectors.toList());

			// pass the constructed list to serve as menu options.
			// ask user to make a choice, and make sure that choice is not null
			choice = optMenu.displayMenuWithPrevious("\nSelect author to update:", optList);
			if (choice == null) {
				return;
			}

			// try to get the object the user selected
			// index needs to be changed to a zero-based index, so index - 1
			// pass the object to helper function
			if (choice.getId() != null) {
				Author author = result.get(choice.getId() - 1);
				admAuthorsUpdate(author);
			}
		}
	}

	/**
	 * For Branch object types. Updates objects name attribute to a new name
	 * 
	 * @param branch object to update
	 */
	public void admBranchesUpdate(Branch branch) {
		UserInput userInput = new UserInput(); // gets user input from console
		String edit; // user changes for string
		String decision; // how to handle user input

		System.out.println("\nUpdate Options: Enter a new value, or type 'N/A' for no change or 'Quit' to cancel");

		// prompt user for new name
		edit = userInput.askForString("\nPlease enter a new value for " + branch.getBranchName() + ":");

		// determine how to handle input
		decision = userInput.validateResponse(edit);
		if (decision == "ok") {
			// set object name property to new name
			branch.setBranchName(edit);
		} else if (decision == "quit" || decision == "error") {
			return;
		}

		// prompt user for new address
		edit = userInput.askForString("\nPlease enter a new value for " + branch.getBranchAddress() + ":");

		// determine how to handle input
		decision = userInput.validateResponse(edit);
		if (decision == "ok") {
			// set object address property to new address
			branch.setBranchAddress(edit);
		} else if (decision == "quit" || decision == "error") {
			return;
		}

		// try to execute the edits
		try {
			adminService.saveBranch(branch);
		} catch (SQLException e) {
			System.out.println("Save branch librarian service failed");
		}
	}

	/**
	 * For Publisher object types. Updates objects name attribute to a new name
	 * 
	 * @param publisher object to update
	 */
	public void admPublishersUpdate(Publisher publisher) {
		UserInput userInput = new UserInput(); // gets user input from console
		String edit; // user changes for string
		String decision; // how to handle user input

		System.out.println("\nUpdate Options: Enter a new value, or type 'N/A' for no change or 'Quit' to cancel");

		// prompt user for new name
		edit = userInput.askForString("\nPlease enter a new value for " + publisher.getPublisherName() + ":");

		// determine how to handle input
		decision = userInput.validateResponse(edit);
		if (decision == "ok") {
			// set object name property to new name
			publisher.setPublisherName(edit);
		} else if (decision == "quit" || decision == "error") {
			return;
		}

		// prompt user for new address
		edit = userInput.askForString("\nPlease enter a new value for " + publisher.getPublisherAddress() + ":");

		// determine how to handle input
		decision = userInput.validateResponse(edit);
		if (decision == "ok") {
			// set object address property to new address
			publisher.setPublisherAddress(edit);
		} else if (decision == "quit" || decision == "error") {
			return;
		}

		// prompt user for new string value
		edit = userInput.askForString("\nPlease enter a new value for " + publisher.getPublisherPhone() + ":");

		// determine how to handle input
		decision = userInput.validateResponse(edit);
		if (decision == "ok") {
			// set existing object string property to new edit string
			publisher.setPublisherPhone(edit);
		} else if (decision == "quit" || decision == "error") {
			return;
		}

		// try to execute the edits
		try {
			adminService.savePublisher(publisher);
		} catch (SQLException e) {
			System.out.println("Save publisher librarian service failed");
		}
	}

	/**
	 * For Genre object types. Updates objects name attribute to a new name
	 * 
	 * @param genre object to update
	 */
	public void admBorrowersUpdate(Borrower borrower) {
		UserInput userInput = new UserInput(); // gets user input from console
		String edit; // user changes for string
		String decision; // how to handle user input

		System.out.println("\nUpdate Options: Enter a new value, or type 'N/A' for no change or 'Quit' to cancel");

		// prompt user for new name
		edit = userInput.askForString("\nPlease enter a new value for " + borrower.getName() + ":");

		// determine how to handle input
		decision = userInput.validateResponse(edit);
		if (decision == "ok") {
			// set object name property to new name
			borrower.setName(edit);
		} else if (decision == "quit" || decision == "error") {
			return;
		}

		// prompt user for new address
		edit = userInput.askForString("\nPlease enter a new value for " + borrower.getAddress() + ":");

		// determine how to handle input
		decision = userInput.validateResponse(edit);
		if (decision == "ok") {
			// set object address property to new address
			borrower.setAddress(edit);
		} else if (decision == "quit" || decision == "error") {
			return;
		}

		// prompt user for new string value
		edit = userInput.askForString("\nPlease enter a new value for " + borrower.getPhone() + ":");

		// determine how to handle input
		decision = userInput.validateResponse(edit);
		if (decision == "ok") {
			// set existing object string property to new edit string
			borrower.setPhone(edit);
		} else if (decision == "quit" || decision == "error") {
			return;
		}

		// try to execute the edits
		try {
			adminService.saveBorrower(borrower);
		} catch (SQLException e) {
			System.out.println("Save borrower librarian service failed");
		}
	}

	/**
	 * For Genre object types. Updates objects name attribute to a new name
	 * 
	 * @param genre object to update
	 */
	public void admGenresUpdate(Genre genre) {
		UserInput userInput = new UserInput(); // gets user input from console
		String nameEdit; // user changes for name

		// prompt user for new name
		nameEdit = userInput.askForString("\nPlease enter a new name for " + genre.getGenreName() + ":");

		// set object name property to new name
		genre.setGenreName(nameEdit);

		// try to execute the edits
		try {
			adminService.saveGenre(genre);
		} catch (SQLException e) {
			System.out.println("Save genre librarian service failed");
		}
	}

	/**
	 * For Author object types. Updates objects name attribute to a new name
	 * 
	 * @param genre object to update
	 */
	public void admAuthorsUpdate(Author author) {
		UserInput userInput = new UserInput();

		String branchNameEdit; // user changes for name

		branchNameEdit = userInput.askForString("\nPlease enter a new name for " + author.getAuthorName() + ":");

		author.setAuthorName(branchNameEdit);

		// try to execute the edits
		try {
			adminService.saveAuthor(author);
		} catch (SQLException e) {
			System.out.println("Save branch librarian service failed");
		}
	}

	/**
	 * BOOKS READ
	 * 
	 * UI console view for just display a list of results of this object type
	 */
	public void admTwoMenuBooksRead() {
		// variables for console ui navigation
		OptionMenu optMenu = new OptionMenu();
		MenuChoice choice = new MenuChoice();

		// set display menu method argument here
		List<String> optList = new ArrayList<>();
		List<Book> result = null;
		List<Publisher> publisherList = null;
		List<Genre> genreList = null;

		if (choice != null) {

			// query the database for all objects of this type
			try {
				result = adminService.readBooks(null, null);
			} catch (SQLException e) {
				System.out.println("Read books librarian service failed");
				e.printStackTrace();
			}

			String optString = "";
			int counter = 1;
			List<String> strList = new ArrayList<>();

			for (Book b : result) {

				optString = b.getTitle().concat(" by ");

				counter = 1; // used to determine whether comma separator necessary
				for (Author a : b.getAuthors()) {
					optString = optString.concat(a.getAuthorName());

					// for lists of more than one author, add comma separator between authors
					if (counter < b.getAuthors().size()) {
						optString = optString.concat(", ");
					}
					counter++;
				}

				try {
					publisherList = adminService.readPublishers(b.getPubId(), null);
				} catch (SQLException e) {
					System.out.println("Error getting publisher");
				}

				if (publisherList != null && publisherList.size() > 0) {
					optString = optString.concat("\n\t").concat(publisherList.get(0).getPublisherName()).concat("\n");
				}

				try {
					genreList = adminService.getGenresByBookId(b.getBookId());
				} catch (SQLException e1) {
					System.out.println("SQL problem in getting genre");
				} catch (Exception e2) {
					System.out.println("Error getting genre list");
				}

				if (genreList != null && genreList.size() > 0) {

					optString = optString.concat("\tGenre(s): ");

					counter = 1; // used to determine whether comma separator necessary
					for (Genre g : genreList) {
						optString = optString.concat(g.getGenreName());

						// for lists of more than one author, add comma separator between authors
						if (counter < genreList.size()) {
							optString = optString.concat(", ");
						}
						counter++;
					}
					optString = optString.concat("\n");
				}
				strList.add(optString);
				optString = "";

			}

			// just display the options. no selections allowed in read operation
			optMenu.displayOnly("\nBooks:", strList);
			return;

		}
	}

	/**
	 * BRANCHES READ
	 * 
	 * UI console view for just display a list of results of this object type
	 */
	public void admTwoMenuBranchesRead() {
		// variables for console ui navigation
		OptionMenu optMenu = new OptionMenu();
		MenuChoice choice = new MenuChoice();

		// set display menu method argument here
		List<String> optList = new ArrayList<>();
		List<Branch> result = null;

		if (choice != null) {

			// query the database for all objects of this type
			try {
				result = adminService.readBranches(null, null);
			} catch (SQLException e) {
				System.out.println("Read branches librarian service failed");
				e.printStackTrace();
			}

			// create a String for each option from the query result and store in a list
			optList = result.stream().map(x -> "\t".concat(x.getBranchName()).concat("\n").concat("\tAddress: ")
					.concat(x.getBranchAddress()).concat("\n")).collect(Collectors.toList());

			// just display the options. no selections allowed in read operation
			optMenu.displayOnly("\nBranches:", optList);
			return;

		}
	}

	/**
	 * PUBLISHER READ
	 * 
	 * UI console view for just display a list of results of this object type
	 */
	public void admTwoMenuPublishersRead() {
		// variables for console ui navigation
		OptionMenu optMenu = new OptionMenu();
		MenuChoice choice = new MenuChoice();

		// set display menu method argument here
		List<String> optList = new ArrayList<>();
		List<Publisher> result = null;

		if (choice != null) {

			// query the database for all objects of this type
			try {
				result = adminService.readPublishers(null, null);
			} catch (SQLException e) {
				System.out.println("Read branches librarian service failed");
				e.printStackTrace();
			}

			// create a String for each option from the query result and store in a list
			optList = result.stream()
					.map(x -> "\t".concat(x.getPublisherName()).concat("\n").concat("\tAddress: ")
							.concat(x.getPublisherAddress()).concat("\n").concat("\tPhone: ")
							.concat(x.getPublisherPhone()).concat("\n"))
					.collect(Collectors.toList());

			// just display the options. no selections allowed in read operation
			optMenu.displayOnly("\nPublishers:", optList);
			return;

		}

	}

	/**
	 * BARROWER READ
	 * 
	 * UI console view for just display a list of results of this object type
	 */
	public void admTwoMenuBorrowersRead() {
		// variables for console ui navigation
		OptionMenu optMenu = new OptionMenu();
		MenuChoice choice = new MenuChoice();

		// set display menu method argument here
		List<String> optList = new ArrayList<>();
		List<Borrower> result = null;

		if (choice != null) {

			// query the database for all objects of this type
			try {
				result = adminService.readBorrowers(null, null);
			} catch (SQLException e) {
				System.out.println("Read branches librarian service failed");
				e.printStackTrace();
			}

			// create a String for each option from the query result and store in a list
			optList = result.stream()
					.map(x -> "\t".concat(x.getName()).concat("\n").concat("\tCard No: ")
							.concat(x.getCardNo().toString().concat("\n").concat("\tAddress: ").concat(
									x.getAddress().concat("\n").concat("\tPhone: ").concat(x.getPhone()).concat("\n"))))
					.collect(Collectors.toList());

			// just display the options. no selections allowed in read operation
			optMenu.displayOnly("\nBorrowers:", optList);
			return;

		}

	}

	/**
	 * AUTHOR READ
	 * 
	 * UI console view for just display a list of results of this object type
	 */
	public void admTwoMenuAuthorsRead() {
		// variables for console ui navigation
		OptionMenu optMenu = new OptionMenu();
		MenuChoice choice = new MenuChoice();

		// set display menu method argument here
		List<String> optList = new ArrayList<>();
		List<Author> result = null;

		if (choice != null) {

			// query the database for all objects of this type
			try {
				result = adminService.readAuthors(null, null);
			} catch (SQLException e) {
				System.out.println("Read branches librarian service failed");
				e.printStackTrace();
			}

			// create a String for each option from the query result and store in a list
			optList = result.stream().map(x -> x.getAuthorName()).collect(Collectors.toList());

			// just display the options. no selections allowed in read operation
			optMenu.displayOnly("\nAuthors:", optList);
			return;

		}

	}

	/**
	 * GENRE READ
	 * 
	 * UI console view for just display a list of results of this object type
	 */
	public void admTwoMenuGenresRead() {
		// variables for console ui navigation
		OptionMenu optMenu = new OptionMenu();
		MenuChoice choice = new MenuChoice();

		// set display menu method argument here
		List<String> optList = new ArrayList<>();
		List<Genre> result = null;

		if (choice != null) {

			// query the database for all objects of this type
			try {
				result = adminService.readGenres(null, null);
			} catch (SQLException e) {
				System.out.println("Read genres librarian service failed");
				e.printStackTrace();
			}

			// create a String for each option from the query result and store in a list
			optList = result.stream().map(x -> x.getGenreName()).collect(Collectors.toList());

			// just display the options. no selections allowed in read operation
			optMenu.displayOnly("\nGenres:", optList);
			return;

		}

	}

	public void libOneMenu() {
		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();

		// set display menu method argument here
		List<String> optLIB1 = new ArrayList<>();
		optLIB1.add("Enter Branch you manage");

		// repeatedly loop menu until user chooses to Quit
		while (choice != null) {

			// display menu options and remember choice
			choice = optMenu.displayMenuWithPrevious("\nLIB1:", optLIB1);
			if (choice == null) {
				return;
			}

			// execute next step according to user selection
			switch (choice.getId()) {
			case 1: // go to librarian LIB2 menu
				libTwoMenu();
				break;
			case 2: // quit to previous option
				return;
			default:
				System.out.println("ERROR: User choice not recognized");
				choice = null;
			}

		}

	}

	public void libTwoMenu() {
		OptionMenu optMenu = new OptionMenu();
		MenuChoice choice = new MenuChoice();

		List<String> optList = new ArrayList<>();
		List<Branch> result = null;

		while (choice != null) {

			try {
				result = librarianService.readBranches(null, null);
			} catch (SQLException e) {
				System.out.println("Read branches librarian service failed");
				e.printStackTrace();
			}

			optList = result.stream().map(x -> x.getBranchName().concat(", ").concat(x.getBranchAddress()))
					.collect(Collectors.toList());

			choice = optMenu.displayMenuWithPrevious("\nLIB2:", optList);
			if (choice == null) {
				return;
			}

			if (choice.getId() != null) {
				Branch branch = result.get(choice.getId() - 1);
				libThreeMenu(branch);
			}

		}

	}

	public void libThreeMenu(Branch branch) {
		OptionMenu optMenu = new OptionMenu();
		MenuChoice choice = new MenuChoice();

		List<String> optList = new ArrayList<>();
		optList.add("Update the details of the Library");
		optList.add("Add copies of Book to the Branch");

		while (choice != null) {
			choice = optMenu.displayMenuWithPrevious("\nLIB3:", optList);
			if (choice == null) {
				return;
			}

			switch (choice.getId()) {
			case 1: // LIB1 menu
				libThreeUpdate(branch);
				break;
			case 2: // LIB1 menu
				libThreeAddCopies(branch);
//				choice = null;
				break;
			case 3: // LIB1 menu
				choice = null;
				break;
			default:
				System.out.println("ERROR: User choice not recognized");
				choice = null;
			}

		}

	}

	public void libThreeUpdate(Branch branch) {
		UserInput userInput = new UserInput();

		String branchNameEdit; // user changes for branch name
		String branchAddressEdit; // user changes for branch address

		System.out.println("\nYou have chosen to update the Branch with Branch Id: " + branch.getBranchId().toString()
				+ " and Branch Name: " + branch.getBranchName() + ".");
		System.out.println("Enter ‘quit’ at any prompt to cancel operation.");

		branchNameEdit = userInput.askForString("\nPlease enter new branch name or enter N/A for no change:");

		if (!branchNameEdit.isEmpty() && !"quit".equalsIgnoreCase(branchNameEdit)
				&& !"N/A".equalsIgnoreCase(branchNameEdit)) {
			// ok
			branch.setBranchName(branchNameEdit);
		} else if ("N/A".equalsIgnoreCase(branchNameEdit)) {
			// do nothing. continue
		} else if ("quit".equalsIgnoreCase(branchNameEdit)) {
			// quit
			System.out.println("Quitting branch update");
			return;
		} else {
			// error
			System.out.println("ERROR: Invalid branch name edit");
			return;
		}

		branchAddressEdit = userInput.askForString("\nPlease enter new branch address or enter N/A for no change:");

		if (!branchAddressEdit.isEmpty() && !"quit".equalsIgnoreCase(branchAddressEdit)
				&& !"N/A".equalsIgnoreCase(branchAddressEdit)) {
			// ok
			branch.setBranchAddress(branchAddressEdit);
		} else if ("N/A".equalsIgnoreCase(branchAddressEdit)) {
			// do nothing. continue
		} else if ("quit".equalsIgnoreCase(branchAddressEdit)) {
			// quit
			System.out.println("Quitting branch update");
			return;
		} else {
			// error
			System.out.println("ERROR: Invalid branch address edit");
			return;
		}

		// try to execute the branch edits
		try {
			librarianService.saveBranch(branch);
		} catch (SQLException e) {
			System.out.println("Save branch librarian service failed");
		}

	}

	/**
	 * @param branch
	 */
	public void libThreeAddCopies(Branch branch) {
		MenuChoice choice;
		OptionMenu optMenu = new OptionMenu();
		List<String> optList = new ArrayList<>();
		List<Book> bookList = new ArrayList<>(); // list of books shown to user to select from
		UserInput userInput = new UserInput();
		int noCopies = 0;

		// get list of all books in the database
		try {
			bookList = librarianService.readBooks(null, null);
		} catch (SQLException e) {
			System.out.println("Read books librarian service failed");
		}

		// make a list of those books in a title by author format
		optList = makeTitleByAuthorStrings(bookList);

		// ask the user to choose which book to add copies to
		choice = optMenu.displayMenuWithPrevious("\nPick the Book you want to add copies of, to your branch:", optList);

		// get reference to actual book user chose
		if (choice == null) {
			return;
		}
		Book book = bookList.get(choice.getId() - 1);

		// get number of existing copies at branch of book user selected
		try {
			noCopies = librarianService.readBookCopies(book.getBookId(), branch.getBranchId());
			System.out.println("\nExisting number of copies: " + noCopies);
		} catch (SQLException e) {
			System.out.println("Could not get number of book copies");
		}

		
		int resultInt = userInput.askForInt("\nEnter new number of copies:");

		try {
			librarianService.saveBookCopies(book.getBookId(), branch.getBranchId(), resultInt);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public boolean passValidation() {

		// object for receiving user input from console
		UserInput userInput = new UserInput();

		// ask for a card number
		cardNo = userInput.askForInt("\nEnter the your Card Number:");

		// check database to see if card is valid. Return the boolean value
		try {
			return borrowerService.cardNoIsValid(cardNo);
		} catch (SQLException e) {

			e.printStackTrace();
			return false;

		}

	}

	public void borrOneMenu() {
		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();
		Branch branch;

		// set display menu method argument here
		List<String> optBORR1 = new ArrayList<>();
		optBORR1.add("Check out a book");
		optBORR1.add("Return a Book");

		// repeatedly loop menu until user chooses to Quit
		while (choice != null) {

			// display menu options and remember choice
			choice = optMenu.displayMenuWithPrevious("\nBORR1:", optBORR1);
			if (choice == null) {
				return;
			}

			// execute next step according to user selection
			switch (choice.getId()) {
			case 1: // go to borrower checkout menu
				branch = borrCheckoutReturnBranchMenu("\nPick the Branch you want to check out from:");
				if (branch != null) {
					borrCheckoutBookMenu(branch);
				}
				break;
			case 2: // go to borrower checkout menu
				branch = borrCheckoutReturnBranchMenu("\nPick the Branch you want to return from:");
				if (branch != null) {
					borrReturnBookMenu(branch);
				}
				break;
			case 3: // quit to previous option
				return;
			default:
				System.out.println("ERROR: User choice not recognized");
				choice = null;
			}

		}
	}

	public Branch borrCheckoutReturnBranchMenu(String prompt) {
		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();
		List<Branch> result = null;
		Branch branch = null;

		// set display menu method argument here
		List<String> opt = new ArrayList<>();

		// repeatedly loop menu until user chooses to Quit
		while (choice != null) {

			try {
				result = borrowerService.readBranches(null, null);
			} catch (SQLException e) {
				System.out.println("Read branches borrower service failed");
				e.printStackTrace();
			}

			opt = result.stream().map(x -> x.getBranchName().concat(", ").concat(x.getBranchAddress()))
					.collect(Collectors.toList());

			// display menu options and remember choice
			choice = optMenu.displayMenuWithPrevious(prompt, opt);
			if (choice == null) {
				return null;
			}

			if (choice.getId() != null) {
				branch = result.get(choice.getId() - 1);
				if (branch != null) {
					return branch;
				}
			}

		}
		return null;

	}

	public void borrCheckoutBookMenu(Branch branch) {
		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();
		List<Book> result = null;

		// set display menu method argument here
		List<String> opt = new ArrayList<>();

		// repeatedly loop menu until user chooses to Quit
		while (choice != null) {

			try {
				result = borrowerService.readAvailBooksByBranchId(branch.getBranchId());
			} catch (SQLException e) {
				System.out.println("Read available books by branch id borrower service failed");
			}

			opt = makeTitleByAuthorStrings(result);

			// display menu options and remember choice
			choice = optMenu.displayMenuWithPrevious("\nPick the Book you want to check out", opt);
			if (choice == null) {
				return;
			}

			if (choice.getId() != null) {
				Book book = result.get(choice.getId() - 1);
				borrCheckoutBook(branch, book);
			}

		}
	}

	public void borrReturnBookMenu(Branch branch) {
		// variables for console ui navigation
		MenuChoice choice = new MenuChoice();
		OptionMenu optMenu = new OptionMenu();
		List<Book> result = null;

		// set display menu method argument here
		List<String> opt = new ArrayList<>();

		// repeatedly loop menu until user chooses to Quit
		while (choice != null) {

			try {
				result = borrowerService.readBooksCheckedOutByCardNo(cardNo);
			} catch (SQLException e) {
				System.out.println("Read available books by branch id borrower service failed");
			}

			opt = makeTitleByAuthorStrings(result);

			// display menu options and remember choice
			choice = optMenu.displayMenuWithPrevious("\nPick the Book you want to return", opt);
			if (choice == null) {
				return;
			}

			if (choice.getId() != null) {
				Book book = result.get(choice.getId() - 1);
				borrReturnBook(branch, book);
			}

		}
	}

	public void borrReturnBook(Branch branch, Book book) {
		boolean isSuccessful = false;

		try {
			isSuccessful = borrowerService.updateBookLoans(book, branch, cardNo);
		} catch (SQLException e) {
			System.out.println("Borrower Service book return failed");
		}

		if (isSuccessful) {
			try {
				borrowerService.alterBookCopiesAtBranch(book, branch, 1);
			} catch (SQLException e) {
				System.out.println("Error trying to alter book copies after checkout");
			}
		}
	}

	public void borrCheckoutBook(Branch branch, Book book) {
		boolean isSuccessful = false;

		try {
			isSuccessful = borrowerService.saveBookLoans(book, branch, cardNo);
		} catch (SQLException e) {
			System.out.println("Borrower Service book checkout failed");
		}

		if (isSuccessful) {
			try {
				borrowerService.alterBookCopiesAtBranch(book, branch, -1);
			} catch (SQLException e) {
				System.out.println("Error trying to alter book copies after checkout");
			}
		}
	}

	public List<String> makeTitleByAuthorStrings(List<Book> books) {

		String optString = "";
		int counter = 1;
		List<String> strList = new ArrayList<>();
		
		// exit function on null list
		if (books == null || books.size() == 0)	{
			return null;
		}

		for (Book b : books) {

			optString = b.getTitle().concat(" by ");

			counter = 1; // used to determine whether comma separator necessary
			for (Author a : b.getAuthors()) {
				optString = optString.concat(a.getAuthorName());

				// for lists of more than one author, add comma separator between authors
				if (counter < b.getAuthors().size()) {
					optString = optString.concat(", ");
				}
				counter++;
			}
			strList.add(optString);
			optString = "";
		}
		return strList;
	}

	/**
	 * @return the adminService
	 */
	public AdminService getAdminService() {
		return adminService;
	}

	/**
	 * @param adminService the adminService to set
	 */
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * @return the librarianService
	 */
	public LibrarianService getLibrarianService() {
		return librarianService;
	}

	/**
	 * @param librarianService the librarianService to set
	 */
	public void setLibrarianService(LibrarianService librarianService) {
		this.librarianService = librarianService;
	}

}
