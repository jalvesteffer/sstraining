/**
 * 
 */
package com.ss.training.wk2.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.wk2.project.UserInput;
import com.ss.training.wk2.project.entity.Author;
import com.ss.training.wk2.project.entity.Book;
import com.ss.training.wk2.project.entity.Genre;

/**
 * @author jalveste
 *
 */
public class AuthorDAO extends BaseDAO<Author> {

	public AuthorDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void addAuthor(Author author) throws SQLException, ClassNotFoundException {

		// create a statement Object
		save("INSERT INTO tbl_author (authorName) VALUES (?)", new Object[] { author.getAuthorName() });
	}
	
	public void updateAuthor(Author author) throws SQLException, ClassNotFoundException {

		save("UPDATE tbl_author SET authorName=? WHERE authorId=?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException {

		save("DELETE FROM tbl_author WHERE authorId=?", new Object[] { author.getAuthorId() });
	}
	
	public List<Author> readAllAuthors() throws ClassNotFoundException, SQLException {

		return read("SELECT * FROM tbl_author", null);

	}

	public List<Author> readAllAuthorsByBookId(Integer bookId) throws ClassNotFoundException, SQLException {

		return read(
				"SELECT * FROM tbl_author WHERE authorId IN (SELECT authorId FROM tbl_book_authors WHERE bookId= ?)",
				new Object[] { bookId });

	}

	public List<Author> readAuthorById(Integer authorId) throws ClassNotFoundException, SQLException {

		return read("SELECT * FROM tbl_author WHERE authorId=?", new Object[] { authorId });

	}
	
	public List<Author> readAuthorByName(String name) throws ClassNotFoundException, SQLException {

		return read("SELECT * FROM tbl_author WHERE authorName=?", new Object[] { name });

	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
			authors.add(author);
		}

		return authors;
	}
}
