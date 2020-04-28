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
import com.ss.training.wk2.project.entity.Genre;

/**
 * @author jalveste
 *
 */
public class BorrowerDAO extends BaseDAO<Borrower> {

	public BorrowerDAO(Connection conn) {
		super(conn);
	}

	public void addBorrower(Borrower borrower) throws SQLException, ClassNotFoundException {

		// create a statement Object
		save("INSERT INTO tbl_borrower (name, address, phone) VALUES (?, ?, ?)",
				new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone() });
	}

	public void updateBorrower(Borrower borrower) throws SQLException, ClassNotFoundException {

		save("UPDATE tbl_borrower SET name=?, address=?, phone=? WHERE cardNo=?",
				new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNo() });
	}

	public void deleteBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {

		save("DELETE FROM tbl_borrower WHERE cardNo=?", new Object[] { borrower.getCardNo() });
	}

	public List<Borrower> readAllBorrowers() throws ClassNotFoundException, SQLException {

		return read("SELECT * FROM tbl_borrower", null);

	}

	public Borrower readBorrowerById(Integer cardNo) throws ClassNotFoundException, SQLException {
		List<Borrower> borrowerList = new ArrayList<>();
		borrowerList = read("SELECT * FROM tbl_borrower WHERE cardNo=?", new Object[] { cardNo });

		if (borrowerList != null && borrowerList.size() > 0) {
			return borrowerList.get(0);
		} else {
			return null;
		}

	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setName(rs.getString("name"));
			borrower.setAddress(rs.getString("address"));
			borrower.setPhone(rs.getString("phone"));
			borrowers.add(borrower);
		}

		return borrowers;
	}

}
