/**
 * 
 */
package com.ss.training.wk2.project.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.wk2.project.entity.BookCopies;

/**
 * @author jalveste
 *
 */
public class BookCopiesDAO extends BaseDAO<BookCopies> {

	public BookCopiesDAO(Connection conn) {
		super(conn);
	}

	public void createBookCopies(Integer bookId, Integer branchId, Integer numCopies)
			throws SQLException, ClassNotFoundException {

		save("INSERT INTO tbl_book_copies (bookId, branchId, noOfCopies) VALUES (?, ?, ?)",
				new Object[] { bookId, branchId, numCopies });
	}

//	public Integer addBookCopies(BookCopies bookCopies) throws SQLException, ClassNotFoundException {
//
//		// create a statement Object
//		return saveWithPK("INSERT INTO tbl_book_copiess FROM tbl_book_copies (bookCopiesName, bookCopiesAddress) VALUES (?, ?)",
//				new Object[] { bookCopies.getBookCopiesName(), bookCopies.getBookCopiesAddress() });
//	}
//
	public void updateBookCopies(Integer bookId, Integer branchId, Integer numCopies)
			throws SQLException, ClassNotFoundException {

		save("UPDATE tbl_book_copies SET noOfCopies=? WHERE bookId=? AND branchId=?",
				new Object[] { numCopies, bookId, branchId });
	}

	/**
	 * Deletes all book copies in the book copies table where the author matches the
	 * passed bookId parameter
	 * 
	 * @param bookId deletes all book copies matching this bookId
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void deleteBookCopiesByAuthorId(Integer authorId) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_copies WHERE bookId IN (SELECT bookId FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_authors WHERE authorId=?))",
				new Object[] { authorId });
	}
	
	/**
	 * Deletes all book copies in the book copies table where the author matches the
	 * passed bookId parameter
	 * 
	 * @param bookId deletes all book copies matching this bookId
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void deleteBookCopiesByBookId(Integer bookId) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_copies WHERE bookId IN (SELECT bookId FROM tbl_book WHERE bookId=?)",
				new Object[] { bookId });
	}

	public void deleteBookCopiesByPubId(Integer publisherId) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_copies WHERE bookId IN (SELECT bookId FROM tbl_book WHERE pubId=?)",
				new Object[] { publisherId });
	}
	
	public void deleteBookCopiesByBranchId(Integer branchId) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_copies WHERE branchId=?",
				new Object[] { branchId });
	}

	public List<BookCopies> readAvailableBooksByBranchId(Integer branchId) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book_copies WHERE branchId=? AND noOfCopies > 0", new Object[] { branchId });
	}

	public BookCopies readBookCopiesByBookIdBranchId(Integer bookId, Integer branchId)
			throws ClassNotFoundException, SQLException {

		List<BookCopies> bookCopies = read("SELECT * FROM tbl_book_copies WHERE bookId=? AND branchId=?",
				new Object[] { bookId, branchId });

		if (bookCopies != null && bookCopies.size() > 0) {
			return bookCopies.get(0);
		} else {
			return null;
		}

	}

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws SQLException {
		List<BookCopies> bookCopiesList = new ArrayList<>();
		while (rs.next()) {
			BookCopies bookCopies = new BookCopies();
			bookCopies.setNoOfCopies(rs.getInt("noOfCopies"));
			bookCopiesList.add(bookCopies);
		}

		return bookCopiesList;
	}

}
