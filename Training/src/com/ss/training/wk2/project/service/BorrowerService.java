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
import com.ss.training.wk2.project.dao.PublisherDAO;
import com.ss.training.wk2.project.entity.Author;
import com.ss.training.wk2.project.entity.Book;
import com.ss.training.wk2.project.entity.BookCopies;
import com.ss.training.wk2.project.entity.Borrower;
import com.ss.training.wk2.project.entity.Branch;
import com.ss.training.wk2.project.entity.Publisher;

/**
 * @author jalveste
 *
 */
public class BorrowerService {

	public ConnectionUtil connUtil = new ConnectionUtil();

//	public List<Publisher> readPublishers(Integer pk, String publisherName) throws SQLException	{
//		Connection conn = null;
//		try {
//			conn = connUtil.getConnection();
////			AuthorDAO aDAO = new AuthorDAO(conn);
//			PublisherDAO pDAO = new PublisherDAO(conn);
//			if (pk != null)	{
//				System.out.println("GET PUBLISHER BY PRIMARY KEY");
//				List<Publisher> publishers = pDAO.readPublisherById(pk);
//				return publishers;
//			} else if (publisherName != null)	{
//				//searchAuthors
//			} else	{
//				List<Publisher> publishers = pDAO.readAllPublishers();
//
//				return publishers;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				conn.close();
//			}
//		}
//		return null;
//
//		
//		
//	}

//	public List<Branch> readBranches(Integer pk, String branchName) throws SQLException {
//		Connection conn = null;
//		try {
//			conn = connUtil.getConnection();
////			AuthorDAO aDAO = new AuthorDAO(conn);
//			BranchDAO pDAO = new BranchDAO(conn);
//			if (pk != null) {
//				System.out.println("GET PUBLISHER BY PRIMARY KEY");
//				List<Branch> branches = pDAO.readBranchById(pk);
//				return branches;
//			} else if (branchName != null) {
//				// searchAuthors
//			} else {
//				List<Branch> branches = pDAO.readAllBranches();
//
//				return branches;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				conn.close();
//			}
//		}
//		return null;
//
//	}
//
//	public List<Book> readBooks(Integer pk, String bookTitle) throws SQLException {
//		Connection conn = null;
//		try {
//			conn = connUtil.getConnection();
//			BookDAO bDAO = new BookDAO(conn);
//			AuthorDAO aDAO = new AuthorDAO(conn);
//			if (pk != null) {
//				System.out.println("GET BOOKS BY PRIMARY KEY");
//				List<Book> books = bDAO.readBookById(pk);
//				return books;
//			} else if (bookTitle != null) {
//				// searchAuthors
//			} else {
//				List<Book> books = bDAO.readAllBooks();
//
//				// populate books authors list
//				for (Book b : books) {
//					List<Author> authors = new ArrayList<>();
//					authors = aDAO.readAllAuthorsByBookId(b.getBookId());
//					b.setAuthors(authors);
//				}
//				return books;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				conn.close();
//			}
//		}
//		return null;
//
//	}
//
//	/**
//	 * @param branch
//	 * @throws SQLException
//	 */
//	public void saveBranch(Branch branch) throws SQLException {
//
//		Connection conn = null; // database connection
//
//		try {
//			// get a new database connection and pass it to a BranchDAO
//			conn = connUtil.getConnection();
//			BranchDAO aDAO = new BranchDAO(conn);
//
//			// perform CRUD operatoin depending on which branch variables are set
//			if (branch.getBranchId() != null && branch.getBranchName() != null) {
//				// perform update
//				aDAO.updateBranch(branch);
//			} else if (branch.getBranchId() != null) {
//				// perform deletion
//				aDAO.deleteBranch(branch);
//			} else {
//				// perform creation
//				aDAO.addBranch(branch);
//			}
//
//			// commit transaction and display success message
//			conn.commit();
//			System.out.println("\nUpdated " + branch.getBranchName() + " Branch Successfully");
//
//		} catch (ClassNotFoundException | SQLException e) {
//
//			// transaction failed. Rollback changes made
//			conn.rollback();
//			System.out.println("Branch transaction failed.  Transaction rolled back.");
//
//		} finally {
//
//			// close database connection
//			if (conn != null) {
//				conn.close();
//			}
//		}
//
//	}

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
		Connection conn = null; // reference to database connection

		try {
			// get a database connection and pass it to a new branch DAO
			conn = connUtil.getConnection();
			BranchDAO pDAO = new BranchDAO(conn);

			if (pk != null) {
				// gets branch by primary key
				List<Branch> branches = pDAO.readBranchById(pk);
				return branches;
			} else if (branchName != null) {
				// gets branch by name
			} else {
				// returns all branches
				List<Branch> branches = pDAO.readAllBranches();

				return branches;
			}
		} catch (Exception e) {
			System.out.println("read BranchDAO failed");
			e.printStackTrace();
		} finally {

			// close the database connection
			if (conn != null) {
				conn.close();
			}
		}
		return null;

	}

	public Boolean cardNoIsValid(Integer cardNo) throws SQLException {
		Connection conn = null;
		Borrower borrower = null;
		try {
			conn = connUtil.getConnection();
			BorrowerDAO borrDAO = new BorrowerDAO(conn);

			borrower = borrDAO.readBorrowerById(cardNo);

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
