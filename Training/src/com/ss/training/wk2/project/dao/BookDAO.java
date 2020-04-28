/**
 * 
 */
package com.ss.training.wk2.project.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.wk2.project.entity.Book;
import com.ss.training.wk2.project.entity.Genre;
import com.ss.training.wk2.project.entity.Publisher;

/**
 * @author jalveste
 *
 */
public class BookDAO extends BaseDAO<Book> {

	public BookDAO(Connection conn) {
		super(conn);
	}

	public void addBook(Book book) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_book (title, pubId) VALUES (?, ?)", new Object[] { book.getTitle(), book.getPubId() });
	}

	public void addBookAuthorRelationship(Integer bookId, Integer authorId)
			throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_book_authors (bookId, authorId) VALUES (?, ?)", new Object[] { bookId, authorId });
	}

	public void updateBook(Book book) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_book SET title=?, pubId=? WHERE bookId = ?",
				new Object[] { book.getTitle(), book.getPubId(), book.getBookId() });
	}

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book WHERE bookId = ?", new Object[] { book.getBookId() });
	}

	/**
	 * Deletes all books in the book table where the author matches the passed
	 * authorId parameter
	 * 
	 * @param authorId deletes all books matching this authorId
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void deleteBooksByAuthorId(Integer authorId) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_authors WHERE authorId=?)",
				new Object[] { authorId });
	}

	public void deleteBooksByPubId(Integer pubId) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book WHERE pubId=?", new Object[] { pubId });
	}

	public List<Book> readAllBooks() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book", null);
	}

	public List<Book> readAllBooksByAuthor(Integer authorId) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_authors WHERE authorId= ?)",
				new Object[] { authorId });
	}

	public List<Book> readAvailBooksByBranchId(Integer branchId) throws ClassNotFoundException, SQLException {
		return read(
				"SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_copies WHERE branchId=? AND noOfCopies > 0)",
				new Object[] { branchId });
	}

	public List<Book> readBooksCheckedOutByCardNo(Integer cardNo) throws ClassNotFoundException, SQLException {
		return read(
				"SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_loans WHERE cardNo=? AND dateIn IS NULL)",
				new Object[] { cardNo });
	}

	public List<Book> readBookById(Integer bookId) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book WHERE bookId=?", new Object[] { bookId });
	}

	public List<Book> readBookByTitle(String title) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book WHERE title=?", new Object[] { title });
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			book.setPubId(rs.getInt("pubId"));
			books.add(book);
		}
		return books;
	}
}
