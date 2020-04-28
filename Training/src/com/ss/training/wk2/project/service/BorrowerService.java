/**
 * 
 */
package com.ss.training.wk2.project.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.wk2.project.dao.AuthorDAO;
import com.ss.training.wk2.project.dao.BookCopiesDAO;
import com.ss.training.wk2.project.dao.BookDAO;
import com.ss.training.wk2.project.dao.BookLoansDAO;
import com.ss.training.wk2.project.dao.BorrowerDAO;
import com.ss.training.wk2.project.dao.BranchDAO;
import com.ss.training.wk2.project.entity.Author;
import com.ss.training.wk2.project.entity.Book;
import com.ss.training.wk2.project.entity.BookCopies;
import com.ss.training.wk2.project.entity.Borrower;
import com.ss.training.wk2.project.entity.Branch;

/**
 * @author jalveste
 *
 */
public class BorrowerService {

	public ConnectionUtil connUtil = new ConnectionUtil();


	// checkout
	public boolean saveBookLoans(Book book, Branch branch, Integer cardNo) throws SQLException {

		Connection conn = null; // database connection
		boolean isSuccessful = false;

		try {
			// get a new database connection and pass it to a BranchDAO
			conn = connUtil.getConnection();

			BookLoansDAO blDAO = new BookLoansDAO(conn);

			LocalDateTime dateTimeNow = LocalDateTime.now();
			LocalDateTime dateTimePlusWeek = dateTimeNow.plusDays(7);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String strDateTimeNow = dateTimeNow.format(formatter);
			String strDateTimePlusWeek = dateTimePlusWeek.format(formatter);

			blDAO.addBookLoans(book.getBookId(), branch.getBranchId(), cardNo, strDateTimeNow, strDateTimePlusWeek);

			// commit transaction and display success message
			conn.commit();
			System.out.println("\n" + book.getTitle() + " has been checked out at " + branch.getBranchName()
					+ " with a due date of " + strDateTimePlusWeek);

			isSuccessful = true;

		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			conn.rollback();
			System.out.println("Branch transaction failed.  Transaction rolled back.");

		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
		return isSuccessful;

	}

	// checkin
	public boolean updateBookLoans(Book book, Branch branch, Integer cardNo) throws SQLException {

		Connection conn = null; // database connection
		boolean isSuccessful = false;

		try {
			// get a new database connection and pass it to a BranchDAO
			conn = connUtil.getConnection();

			BookLoansDAO blDAO = new BookLoansDAO(conn);

			LocalDateTime dateTimeNow = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String strDateTimeNow = dateTimeNow.format(formatter);

			blDAO.updateBookLoans(book.getBookId(), branch.getBranchId(), cardNo, strDateTimeNow);

			// commit transaction and display success message
			conn.commit();
			System.out.println("\n" + book.getTitle() + " has been returned to " + branch.getBranchName() + " on "
					+ strDateTimeNow);

			isSuccessful = true;

		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			conn.rollback();
			System.out.println("Branch transaction failed.  Transaction rolled back.");

		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
		return isSuccessful;

	}

	public void alterBookCopiesAtBranch(Book book, Branch branch, Integer change) throws SQLException {

		Connection conn = null; // database connection
		BookCopies bookCopies;
		int newValue;

		try {
			// get a new database connection and pass it to a BranchDAO
			conn = connUtil.getConnection();

			BookCopiesDAO bcDAO = new BookCopiesDAO(conn);

			bookCopies = bcDAO.readBookCopiesByBookIdBranchId(book.getBookId(), branch.getBranchId());

			if (bookCopies == null) {
				bcDAO.createBookCopies(book.getBookId(), branch.getBranchId(), 0);
				newValue = change;
			} else	{
				newValue = bookCopies.getNoOfCopies() + change;	
			}

			if (newValue >= 0) {

				bcDAO.updateBookCopies(book.getBookId(), branch.getBranchId(), newValue);
			}
			
			// commit transaction and display success message
			conn.commit();
			System.out.println(
					"\nAt " + branch.getBranchName() + ", copies of " + book.getTitle() + " have changed by " + change);

		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			conn.rollback();
			System.out.println("Alter book copies operation failed.  Transaction rolled back.");

		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}

	}

	public List<Book> readAvailBooksByBranchId(Integer branchId) throws SQLException {
		Connection conn = null; // reference to database connection

		try {
			// get a database connection and pass it to a new branch DAO
			conn = connUtil.getConnection();
			BookDAO bDAO = new BookDAO(conn);
			AuthorDAO aDAO = new AuthorDAO(conn);

			if (branchId != null) {
				// gets branch by primary key
				List<Book> books = bDAO.readAvailBooksByBranchId(branchId);

				// populate books authors list
				for (Book b : books) {
					List<Author> authors = new ArrayList<>();
					authors = aDAO.readAllAuthorsByBookId(b.getBookId());
					b.setAuthors(authors);
				}

				return books;
			}
		} catch (Exception e) {
			System.out.println("BorrowerService failed to read all available books at branchId");
		} finally {

			// close the database connection
			if (conn != null) {
				conn.close();
			}
		}
		return null;

	}

	public List<Book> readBooksCheckedOutByCardNo(Integer cardNo) throws SQLException {
		Connection conn = null; // reference to database connection

		try {
			// get a database connection and pass it to a new branch DAO
			conn = connUtil.getConnection();
			BookDAO bDAO = new BookDAO(conn);
			AuthorDAO aDAO = new AuthorDAO(conn);

			if (cardNo > 0) {
				// gets branch by primary key
				List<Book> books = bDAO.readBooksCheckedOutByCardNo(cardNo);

				// populate books authors list
				for (Book b : books) {
					List<Author> authors = new ArrayList<>();
					authors = aDAO.readAllAuthorsByBookId(b.getBookId());
					b.setAuthors(authors);
				}

				return books;
			}
		} catch (Exception e) {
			System.out.println("BorrowerService failed to read all books checked out by cardNo");
		} finally {

			// close the database connection
			if (conn != null) {
				conn.close();
			}
		}
		return null;

	}

	public List<Branch> readBranches(Integer pk, String branchName) throws SQLException {
		AdminService adminService = new AdminService();

		return adminService.readBranches(pk, branchName);

	}

	public Boolean cardNoIsValid(Integer cardNo) throws SQLException {
		Connection conn = null;
		Borrower borrower = null;
		try {
			conn = connUtil.getConnection();
			BorrowerDAO borrDAO = new BorrowerDAO(conn);

			borrower = borrDAO.readBorrowerByCardNo(cardNo);

		} catch (Exception e) {
			return false;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		if (borrower != null) {
			return true;
		} else {
			return false;
		}
	}
}
