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

/**
 * @author jalveste
 *
 */
public class AuthorDAO extends BaseDAO<Author> {

	public AuthorDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public Integer addAuthor(Author author) throws SQLException, ClassNotFoundException {

		// create a statement Object
		return saveWithPK("INSERT INTO tbl_authors FROM tbl_author (authorName) VALUES (?)",
				new Object[] { author.getAuthorId() });
	}

	public void updateAuthor(Author author) throws SQLException, ClassNotFoundException {

		save("UPDATE tbl_author SET authorName=? WHERE authorId=?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException {

		save("DELETE FROM tbl_author (authorName) WHERE authorId=?", new Object[] { author.getAuthorId() });
	}

	public List<Author> readAllAuthors() throws ClassNotFoundException, SQLException {

		return read("SELECT * FROM tbl_author", null);

	}

	public List<Author> readAuthorById(Integer authorId) throws ClassNotFoundException, SQLException {

		return read("SELECT * FROM tbl_author WHERE authorId=?", new Object[] { authorId });

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
