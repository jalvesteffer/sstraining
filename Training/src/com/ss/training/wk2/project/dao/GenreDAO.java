/**
 * 
 */
package com.ss.training.wk2.project.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.wk2.project.entity.Author;
import com.ss.training.wk2.project.entity.Genre;

/**
 * @author jalveste
 *
 */
public class GenreDAO extends BaseDAO<Genre> {

	public GenreDAO(Connection conn) {
		super(conn);
	}

	public List<Genre> readAllGenres() throws ClassNotFoundException, SQLException {

		return read("SELECT * FROM tbl_genre", null);

	}

	public void deleteGenre(Genre genre) throws ClassNotFoundException, SQLException {

		save("DELETE FROM tbl_genre WHERE genre_id=?", new Object[] { genre.getGenreId() });
	}

	public void addGenre(Genre genre) throws SQLException, ClassNotFoundException {

		// create a statement Object
		save("INSERT INTO tbl_genre (genre_name) VALUES (?)", new Object[] { genre.getGenreName() });
	}
	
	public void addBookGenreRelationship(Integer genre_id, Integer bookId) throws SQLException, ClassNotFoundException {

		// create a statement Object
		save("INSERT INTO tbl_book_genres (genre_id, bookId) VALUES (?, ?)", new Object[] { genre_id, bookId });
	}
	
	public void removeBookGenreRelationship(Integer genre_id, Integer bookId) throws SQLException, ClassNotFoundException {

		// create a statement Object
		save("DELETE FROM tbl_book_genres WHERE genre_id=? AND bookId=?", new Object[] { genre_id, bookId });
	}

	public void updateGenre(Genre genre) throws SQLException, ClassNotFoundException {

		save("UPDATE tbl_genre SET genre_name=? WHERE genre_id=?",
				new Object[] { genre.getGenreName(), genre.getGenreId() });
	}

	public List<Genre> readGenresByBookId(Integer bookId) throws SQLException, ClassNotFoundException {

		return read("SELECT * FROM tbl_genre WHERE genre_id IN (SELECT genre_id FROM tbl_book_genres WHERE bookId=?)",
				new Object[] { bookId });
	}
	
	public List<Genre> readGenresByNotBookId(Integer bookId) throws SQLException, ClassNotFoundException {

		return read("SELECT * FROM tbl_genre WHERE NOT genre_id IN (SELECT genre_id FROM tbl_book_genres WHERE bookId=?)",
				new Object[] { bookId });
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<>();
		while (rs.next()) {
			Genre publisher = new Genre();
			publisher.setGenreId(rs.getInt("genre_id"));
			publisher.setGenreName(rs.getString("genre_name"));
			genres.add(publisher);
		}

		return genres;
	}

}
