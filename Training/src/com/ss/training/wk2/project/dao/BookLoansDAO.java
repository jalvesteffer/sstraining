package com.ss.training.wk2.project.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.wk2.project.entity.BookLoans;

/**
 * @author jalveste
 *
 */
public class BookLoansDAO extends BaseDAO<BookLoans> {

	public BookLoansDAO(Connection conn) {
		super(conn);
	}

	public void addBookLoans(Integer bookId, Integer branchId, Integer cardNo, String dateOut, String dueDate)
			throws SQLException, ClassNotFoundException {

		// create a statement Object
		save("INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) VALUES (?, ?, ?, ?, ?)",
				new Object[] { bookId, branchId, cardNo, dateOut, dueDate });
	}

	public void updateBookLoans(Integer bookId, Integer branchId, Integer cardNo, String dateIn)
			throws SQLException, ClassNotFoundException {

		// create a statement Object
		save("UPDATE tbl_book_loans SET dateIn=? WHERE bookId=? AND branchId=? AND cardNo=? AND dateIn IS NULL",
				new Object[] { dateIn, bookId, branchId, cardNo });
	}

	public void updateBookLoans(Integer bookId, Integer branchId, Integer cardNo, String dateOut, String dueDate)
			throws SQLException, ClassNotFoundException {

		// create a statement Object
		save("UPDATE tbl_book_loans SET dueDate=? WHERE bookId=? AND branchId=? AND cardNo=? AND dateOut=?",
				new Object[] { dueDate, bookId, branchId, cardNo, dateOut });
	}

	public List<BookLoans> readBookLoansDue(String currentDateTime) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book_Loans WHERE dueDate < ? AND dateIn IS NULL",
				new Object[] { currentDateTime });

	}

	public BookLoans readBookLoansById(Integer cardNo) throws ClassNotFoundException, SQLException {
		List<BookLoans> bookLoansList = new ArrayList<>();
		bookLoansList = read("SELECT * FROM tbl_book_Loans WHERE cardNo=?", new Object[] { cardNo });

		if (bookLoansList != null && bookLoansList.size() > 0) {
			return bookLoansList.get(0);
		} else {
			return null;
		}

	}

	public void deleteBookLoansByPubId(Integer publisherId) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_loans WHERE bookId IN (SELECT bookId FROM tbl_book WHERE pubId=?)",
				new Object[] { publisherId });
	}

	public void deleteBookLoansByBranchId(Integer branchId) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_loans WHERE branchId=?", new Object[] { branchId });
	}

	public void deleteBookLoansByCardNo(Integer cardNo) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_loans WHERE cardNo=?", new Object[] { cardNo });
	}

	public void deleteBookLoansByAuthorId(Integer authorId) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_loans WHERE bookId IN (SELECT bookId FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_authors WHERE authorId=?))",
				new Object[] { authorId });
	}

	public void deleteBookLoansByBookId(Integer bookId) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_loans WHERE bookId IN (SELECT bookId FROM tbl_book WHERE bookId=?)",
				new Object[] { bookId });
	}

	@Override
	public List<BookLoans> extractData(ResultSet rs) throws SQLException {
		List<BookLoans> bookLoansList = new ArrayList<>();
		while (rs.next()) {
			BookLoans bookLoans = new BookLoans();
			// DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			bookLoans.setBookId(rs.getInt("bookId"));
			bookLoans.setBranchId(rs.getInt("branchId"));
			bookLoans.setCardNo(rs.getInt("cardNo"));
			bookLoans.setDateIn(rs.getString("dateIn"));
			bookLoans.setDateOut(rs.getString("dateOut"));
			bookLoans.setDueDate(rs.getString("dueDate"));
			bookLoansList.add(bookLoans);
		}

		return bookLoansList;
	}

}
