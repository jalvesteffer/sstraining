/**
 * 
 */
package com.ss.training.wk2.project.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.wk2.project.entity.Borrower;
import com.ss.training.wk2.project.entity.Publisher;

/**
 * @author jalveste
 *
 */
public class PublisherDAO extends BaseDAO<Publisher> {

	public PublisherDAO(Connection conn) {
		super(conn);
	}

	public void addPublisher(Publisher publisher) throws SQLException, ClassNotFoundException {

		// create a statement Object
		save("INSERT INTO tbl_publisher (publisherName, publisherAddress, publisherPhone) VALUES (?, ?, ?)",
				new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress(),
						publisher.getPublisherPhone() });
	}

	public void deletePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {

		save("DELETE FROM tbl_publisher WHERE PublisherId=?", new Object[] { publisher.getPublisherId() });
	}

	public List<Publisher> readAllPublishers() throws ClassNotFoundException, SQLException {

		return read("SELECT * FROM tbl_publisher", null);

	}

	public void updatePublisher(Publisher publisher) throws SQLException, ClassNotFoundException {

		save("UPDATE tbl_publisher SET publisherName=?, publisherAddress=?, publisherPhone=? WHERE PublisherId=?",
				new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress(),
						publisher.getPublisherPhone(), publisher.getPublisherId() });
	}

	public List<Publisher> readPublisherById(Integer publisherId) throws ClassNotFoundException, SQLException {

		return read("SELECT * FROM tbl_publisher WHERE publisherId=?", new Object[] { publisherId });

	}
	
	public List<Publisher> readPublisherByName(String name) throws ClassNotFoundException, SQLException {

		return read("SELECT * FROM tbl_publisher WHERE publisherName=?", new Object[] { name });

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
