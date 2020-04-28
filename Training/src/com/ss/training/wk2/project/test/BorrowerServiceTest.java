package com.ss.training.wk2.project.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ss.training.wk2.project.dao.BookCopiesDAO;
import com.ss.training.wk2.project.dao.BookLoansDAO;
import com.ss.training.wk2.project.entity.Author;
import com.ss.training.wk2.project.entity.Book;
import com.ss.training.wk2.project.entity.BookLoans;
import com.ss.training.wk2.project.entity.Borrower;
import com.ss.training.wk2.project.entity.Branch;
import com.ss.training.wk2.project.entity.Genre;
import com.ss.training.wk2.project.entity.Publisher;
import com.ss.training.wk2.project.service.AdminService;
import com.ss.training.wk2.project.service.BorrowerService;
import com.ss.training.wk2.project.service.ConnectionUtil;
import com.ss.training.wk2.project.service.LibrarianService;

public class BorrowerServiceTest {

	
	
	
	@Test
	public void readAvailBooksByBranchId() {
		BorrowerService borrowerService = new BorrowerService();

		List<Book> books1 = new ArrayList<>();
		List<Book> books2 = new ArrayList<>();
		List<Book> booksInvalidId = new ArrayList<>();

		try {
			books1 = borrowerService.readAvailBooksByBranchId(1);
			books2 = borrowerService.readAvailBooksByBranchId(2);
			booksInvalidId = borrowerService.readAvailBooksByBranchId(1000);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(books1.size(), 3);
		assertEquals(books2.size(), 1);
		assertEquals(booksInvalidId.size(), 0);

	}

	@Test
	public void readBooksCheckedOutByCardNo() {
		BorrowerService borrowerService = new BorrowerService();

		List<Book> books1 = new ArrayList<>();
		List<Book> books2 = new ArrayList<>();
		List<Book> booksInvalidId = new ArrayList<>();

		try {
			books1 = borrowerService.readBooksCheckedOutByCardNo(1);
			books2 = borrowerService.readBooksCheckedOutByCardNo(8);
			booksInvalidId = borrowerService.readBooksCheckedOutByCardNo(1000);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(books1.size(), 1);
		assertEquals(books2.size(), 2);
		assertEquals(booksInvalidId.size(), 0);

	}

	@Test
	public void cardNoIsValid() {
		BorrowerService borrowerService = new BorrowerService();

		boolean validCard1 = false;
		boolean validCard2 = false;
		boolean invalidCard = false;

		try {
			validCard1 = borrowerService.cardNoIsValid(1);
			validCard2 = borrowerService.cardNoIsValid(10);
			invalidCard = borrowerService.cardNoIsValid(1000);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(validCard1, true);
		assertEquals(validCard2, true);
		assertEquals(invalidCard, false);

	}
	
	
	
	
	
	@Test
	public void alterBookCopiesAtBranch() {
		ConnectionUtil connUtil = new ConnectionUtil();
		Connection conn = null; // database connection
		conn = connUtil.getConnection();
		BookCopiesDAO bcDAO = new BookCopiesDAO(conn);

		BorrowerService borrowerService = new BorrowerService();
		AdminService adminService = new AdminService();
		LibrarianService librarianService = new LibrarianService();

		Book book = null;
		Branch branch = null;
		
		int noCopies = -1;

		try {
			// start with 3 copies
			noCopies = librarianService.readBookCopies(1, 1);
			assertEquals(noCopies, 3);
			
			book = adminService.getBookById(1);
			branch = adminService.readBranches(1, null).get(0);

			// add 2 copies = 5
			borrowerService.alterBookCopiesAtBranch(book, branch, 2);
			noCopies = librarianService.readBookCopies(1, 1);
			assertEquals(noCopies, 5);
			
			// undo last action
			borrowerService.alterBookCopiesAtBranch(book, branch, -2);
			
			// try to change copies to negative; change ignored
			borrowerService.alterBookCopiesAtBranch(book, branch, -10);
			noCopies = librarianService.readBookCopies(1, 1);
			assertEquals(noCopies, 3);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	@Test
	public void updateBookLoans() {
		//
		BorrowerService borrowerService = new BorrowerService();
		AdminService adminService = new AdminService();
		List<Book> books = null;
		List<Branch> branches = null;
		Book book = null;
		Branch branch = null;
		boolean isSuccessful = false;
		
		try {
			books = borrowerService.readAvailBooksByBranchId(1);
			assertEquals(books.size(), 3);
			
			book = adminService.getBookById(1);
			branches = adminService.readBranches(1, null);
			branch = branches.get(0);
			
			// return book adds one to available books
			borrowerService.updateBookLoans(book, branch, 1);
			
			books = borrowerService.readAvailBooksByBranchId(1);
			assertEquals(books.size(), 4);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void saveBookLoans() {
		//
		ConnectionUtil connUtil = new ConnectionUtil();
		Connection conn = null; // database connection
		conn = connUtil.getConnection();
		BookLoansDAO blDAO = new BookLoansDAO(conn);
		
		BorrowerService borrowerService = new BorrowerService();
		AdminService adminService = new AdminService();
		boolean isSuccessful = false;
		
		List<BookLoans> loans = new ArrayList<>();

		try {
			loans = blDAO.readBookLoansDue("2020-04-28 00:00:00");
			assertEquals(loans.size(), 6);
			
			Book book = adminService.getBookById(6);
	
			List<Branch> branches = adminService.readBranches(17, null);
			Branch branch = branches.get(0);
			
			isSuccessful = borrowerService.saveBookLoans(book, branch, 8);
			loans = blDAO.readBookLoansDue("2020-04-28 00:00:00");
			assertEquals(loans.size(), 5);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(isSuccessful, true);
	}
}