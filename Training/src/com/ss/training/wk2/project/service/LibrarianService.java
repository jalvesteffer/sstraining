/**
 * 
 */
package com.ss.training.wk2.project.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.wk2.project.dao.AuthorDAO;
import com.ss.training.wk2.project.dao.BookCopiesDAO;
import com.ss.training.wk2.project.dao.BookDAO;
import com.ss.training.wk2.project.dao.BranchDAO;
import com.ss.training.wk2.project.dao.PublisherDAO;
import com.ss.training.wk2.project.entity.Author;
import com.ss.training.wk2.project.entity.Book;
import com.ss.training.wk2.project.entity.BookCopies;
import com.ss.training.wk2.project.entity.Branch;
import com.ss.training.wk2.project.entity.Publisher;

/**
 * @author jalveste
 *
 */
public class LibrarianService {

	public ConnectionUtil connUtil = new ConnectionUtil();


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

	public List<Book> readBooks(Integer pk, String bookTitle) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bDAO = new BookDAO(conn);
			AuthorDAO aDAO = new AuthorDAO(conn);
			if (pk != null) {
				System.out.println("GET BOOKS BY PRIMARY KEY");
				List<Book> books = bDAO.readBookById(pk);
				return books;
			} else if (bookTitle != null) {
				// searchAuthors
			} else {
				List<Book> books = bDAO.readAllBooks();

				// populate books authors list
				for (Book b : books) {
					List<Author> authors = new ArrayList<>();
					authors = aDAO.readAllAuthorsByBookId(b.getBookId());
					b.setAuthors(authors);
				}
				return books;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;

	}

	/**
	 * @param branch
	 * @throws SQLException
	 */
	public void saveBranch(Branch branch) throws SQLException {

		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a BranchDAO
			conn = connUtil.getConnection();
			BranchDAO aDAO = new BranchDAO(conn);

			// perform CRUD operation depending on which branch variables are set
			if (branch.getBranchId() != null && branch.getBranchName() != null) {
				// perform update
				aDAO.updateBranch(branch);
			} else if (branch.getBranchId() != null) {
				// perform deletion
				aDAO.deleteBranch(branch);
			} else {
				// perform creation
				aDAO.addBranch(branch);
			}

			// commit transaction and display success message
			conn.commit();
			System.out.println("\nUpdated " + branch.getBranchName() + " Branch Successfully");

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

	}

	public void saveBookCopies(Integer bookId, Integer branchId, Integer numCopies) throws SQLException {

		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a BranchDAO
			conn = connUtil.getConnection();

			BookCopiesDAO bcDAO = new BookCopiesDAO(conn);

			// check to see if an entry exists for this book at this branch
			BookCopies bookCopies = bcDAO.readBookCopiesByBookIdBranchId(bookId, branchId);
			
			if (bookCopies == null)	{
				bcDAO.createBookCopies(bookId, branchId, numCopies);
			}	else	{
				bcDAO.updateBookCopies(bookId, branchId, numCopies);
			}
			
			// commit transaction and display success message
			conn.commit();
			System.out.println("\nSet copies of book to " + numCopies);

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

	}

	public Integer readBookCopies(Integer bookId, Integer branchId) throws SQLException {
		Connection conn = null;
		BookCopies bookCopies = null;
		try {
			conn = connUtil.getConnection();
			BookCopiesDAO bcDAO = new BookCopiesDAO(conn);

			bookCopies = bcDAO.readBookCopiesByBookIdBranchId(bookId, branchId);

		} catch (Exception e) {
			return 0;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		// if the bookCopiesList is 0, there are no book copies in the result
		// otherwise, return the number of book copies
		if (bookCopies == null) {
			return 0;
		} else {
			return bookCopies.getNoOfCopies();
		}
	}
}
