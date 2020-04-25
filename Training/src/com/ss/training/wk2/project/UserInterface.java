/**
 * 
 */
package com.ss.training.wk2.project;

import static org.hamcrest.CoreMatchers.containsString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.SQLException;

import com.ss.training.wk2.project.entity.Publisher;
import com.ss.training.wk2.project.service.AdminService;
import com.ss.training.wk2.project.service.LibrarianService;

/**
 * @author jalveste
 *
 */
public class UserInterface {

	private AdminService adminService;
	private LibrarianService librarianService;

	/**
	 * @param adminService
	 */
	public UserInterface() {
		// create new service objects
		adminService = new AdminService();
		librarianService = new LibrarianService();
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		UserInterface ui = new UserInterface();
		ui.start();

	}

	public void start() {
		// program title
		System.out.println("LIBRARY MANAGEMENT SYSTEM");

		// launches the main menu
		mainMenu();

	}

	public void mainMenu() {
		OptionMenu optMenu = new OptionMenu();
		MenuChoice choice = null;

		// set display menu method argument here
		String prompt = "\nMAIN:\nWelcome to the GCIT Library Management System. Which category of a user are you?";
		List<String> optList = new ArrayList<>();
		optList.add("Librarian");
		optList.add("Administrator");
		optList.add("Borrower");

		choice = optMenu.displayMainMenu(prompt, optList);

		while (choice != null) {
			switch (choice.getId()) {
			case 1: // LIB1 menu
				libOneMenu();
				break;
			case 2:
				System.out.println("User chose option 2");
				break;
			case 3:
				System.out.println("User chose option 3");
				break;
			default:
				System.out.println("ERROR: User choice not recognized");
				choice = null;
			}

			choice = optMenu.displayMainMenu(prompt, optList);
		}

	}

	public void libOneMenu() {
		MenuChoice choice;
		OptionMenu optMenu = new OptionMenu();

		List<String> optLIB1 = new ArrayList<>();
		optLIB1.add("Enter Branch you manage");

		choice = optMenu.displayMenuWithPrevious("\nLIB1:", optLIB1);

		while (choice != null) {
			switch (choice.getId()) {
			case 1: // LIB1 menu
				libTwoMenu();
				break;
			default:
				System.out.println("ERROR: User choice not recognized");
				choice = null;
			}

			choice = optMenu.displayMenuWithPrevious("\nLIB1:", optLIB1);
		}

	}

	public void libTwoMenu() {
		OptionMenu optMenu = new OptionMenu();

		List<String> optList = new ArrayList<>();
		List<Publisher> result = null;

		try {
			result = librarianService.readPublishers(null, null);
		} catch (SQLException e) {
			System.out.println("Read publishers librarian service failed");
			e.printStackTrace();
		}

		optList = result.stream().map(x -> x.getPublisherName().concat(", ").concat(x.getPublisherAddress())).collect(Collectors.toList());   
				
		
		// .concat(" ").concat(x.getPublisherAddress()).collect(Collectors.toList()));
		MenuChoice choice = optMenu.displayMenuWithPrevious("\nLIB2:", optList);

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
