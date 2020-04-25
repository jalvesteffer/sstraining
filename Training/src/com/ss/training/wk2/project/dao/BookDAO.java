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

/**
 * @author jalveste
 *
 */
public class BookDAO extends BaseDAO<Book> {
	
	public BookDAO(Connection conn) {
		super(conn);
	}

	public Integer addBook(Book book) throws ClassNotFoundException, SQLException{
		return saveWithPK("INSERT INTO tbl_book (title) VALUES (?)", new Object[] {book.getTitle()});
	}

	public void updateBook(Book book)  throws ClassNotFoundException, SQLException{
		save("UPDATE tbl_book SET title = ? WHERE bookId = ?", new Object[] {book.getTitle(), book.getBookId()});
	}

	public void deleteBook(Book book)  throws ClassNotFoundException, SQLException{
		save("DELETE FROM tbl_book WHERE bookId = ?", new Object[]{book.getBookId()});
	}
	
	public List<Book> readAllBooks() throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM tbl_book", null);
	}
	
	public List<Book> readAllBooksByAuthor(Integer authorId) throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM tbl_book where bookId IN (select bookId from tbl_book_authors where authorId= ?)", new Object[]{authorId});
	}
	
	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while(rs.next()){
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			books.add(book);
		}
		return books;
	}
}
