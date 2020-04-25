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
import com.ss.training.wk2.project.entity.Publisher;

/**
 * @author jalveste
 *
 */
public class PublisherDAO extends BaseDAO<Publisher> {

	public PublisherDAO(Connection conn) {
		super(conn);
	}

	public List<Publisher> readAllPublishers() throws ClassNotFoundException, SQLException {

		return read("SELECT * FROM tbl_publisher", null);

	}

	public List<Publisher> readPublisherById(Integer publisherId) throws ClassNotFoundException, SQLException {

		return read("SELECT * FROM tbl_publisher WHERE publisherId=?", new Object[] { publisherId });

	}

	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<>();
		while (rs.next()) {
			Publisher publisher = new Publisher();
			publisher.setPublisherId(rs.getInt("publisherId"));
			publisher.setPublisherName(rs.getString("publisherName"));
			publisher.setPublisherAddress(rs.getString("publisherAddress"));
			publisher.setPublisherPhone(rs.getString("publisherPhone"));
			publishers.add(publisher);
		}

		return publishers;
	}

}
