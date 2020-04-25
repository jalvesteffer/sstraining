/**
 * 
 */
package com.ss.training.wk2.project.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.training.wk2.project.dao.AuthorDAO;
import com.ss.training.wk2.project.dao.BookDAO;
import com.ss.training.wk2.project.entity.Author;

/**
 * @author jalveste
 *
 */
public class AdminService {

	public ConnectionUtil connUtil = new ConnectionUtil();

	public void saveAuthor(Author author) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO aDAO = new AuthorDAO(conn);
			if (author.getAuthorId() != null && author.getAuthorName() != null) {
				aDAO.updateAuthor(author);
			} else if (author.getAuthorId() != null)	{
				aDAO.deleteAuthor(author);
			} else	{
				aDAO.addAuthor(author);
			}
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

	}
	
	public List<Author> readAuthors(Integer pk, String authorName) throws SQLException	{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO aDAO = new AuthorDAO(conn);
			BookDAO bDAO = new BookDAO(conn);
			if (pk != null)	{
				System.out.println("GET AUTHOR BY PRIMARY KEY");
				List<Author> authors = aDAO.readAuthorById(pk);
				return authors;
			} else if (authorName != null)	{
				//searchAuthors
			} else	{
				List<Author> authors = aDAO.readAllAuthors();
				for (Author a : authors)	{
					a.setBooks(bDAO.readAllBooksByAuthor(a.getAuthorId()));
				}
				return authors;
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
}
