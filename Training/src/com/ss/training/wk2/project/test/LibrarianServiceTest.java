package com.ss.training.wk2.project.test;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.ss.training.wk2.project.dao.BookCopiesDAO;
import com.ss.training.wk2.project.entity.Book;
import com.ss.training.wk2.project.entity.BookCopies;
import com.ss.training.wk2.project.entity.Branch;
import com.ss.training.wk2.project.service.AdminService;
import com.ss.training.wk2.project.service.BorrowerService;
import com.ss.training.wk2.project.service.ConnectionUtil;
import com.ss.training.wk2.project.service.LibrarianService;

public class LibrarianServiceTest {

	@Test
	public void readBookCopies() {
		LibrarianService librarianService = new LibrarianService();

		int keySingleCopy = -1;
		int keyMultipleCopies = -1;
		int keyNoCopies = -1;
		
		try {
			keySingleCopy = librarianService.readBookCopies(1, 1);
			keyMultipleCopies = librarianService.readBookCopies(1, 5);
			keyNoCopies = librarianService.readBookCopies(1000, 1000);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		assertEquals(keySingleCopy, 3);
		assertEquals(keyMultipleCopies, 2);
		assertEquals(keyNoCopies, 0);
	}

	@Test
	public void saveBookCopies() {
		//
		ConnectionUtil connUtil = new ConnectionUtil();
		Connection conn = null; // database connection
		conn = connUtil.getConnection();
		BookCopiesDAO bcDAO = new BookCopiesDAO(conn);
		
		BorrowerService borrowerService = new BorrowerService();
		AdminService adminService = new AdminService();
		LibrarianService librarianService = new LibrarianService();

		Book book = null;
		Branch branch = null;
		BookCopies bookCopies = null;
		int noCopies = -1;
		
		try {
			// get branch id 3: Fairfax County Law Library
			branch = librarianService.readBranches(2, null).get(0);

			// get book with id 12
			book = adminService.getBookById(12);

			// get book id 12: has one copy currently
			bookCopies = bcDAO.readBookCopiesByBookIdBranchId(12, 2);
			noCopies = bookCopies.getNoOfCopies();
			
			// set to 10 copies
			librarianService.saveBookCopies(12, 2, 10);
			bookCopies = bcDAO.readBookCopiesByBookIdBranchId(12, 2);
			noCopies = bookCopies.getNoOfCopies();
			assertEquals(noCopies, 10);
			
			System.out.println(noCopies);
			
			// test reset
			librarianService.saveBookCopies(12, 2, 1);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}