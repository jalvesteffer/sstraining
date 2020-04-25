/**
 * 
 */
package com.ss.training.wk2.project.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.training.wk2.project.dao.AuthorDAO;
import com.ss.training.wk2.project.dao.BookDAO;
import com.ss.training.wk2.project.dao.PublisherDAO;
import com.ss.training.wk2.project.entity.Author;
import com.ss.training.wk2.project.entity.Publisher;

/**
 * @author jalveste
 *
 */
public class LibrarianService {
	
	public ConnectionUtil connUtil = new ConnectionUtil();
	
	public List<Publisher> readPublishers(Integer pk, String publisherName) throws SQLException	{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
//			AuthorDAO aDAO = new AuthorDAO(conn);
			PublisherDAO pDAO = new PublisherDAO(conn);
			if (pk != null)	{
				System.out.println("GET PUBLISHER BY PRIMARY KEY");
				List<Publisher> publishers = pDAO.readPublisherById(pk);
				return publishers;
			} else if (publisherName != null)	{
				//searchAuthors
			} else	{
				List<Publisher> publishers = pDAO.readAllPublishers();

				return publishers;
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
