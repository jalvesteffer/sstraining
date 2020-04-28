package com.ss.training.wk2.project.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ss.training.wk2.project.entity.Author;
import com.ss.training.wk2.project.entity.Book;
import com.ss.training.wk2.project.entity.BookLoans;
import com.ss.training.wk2.project.entity.Borrower;
import com.ss.training.wk2.project.entity.Branch;
import com.ss.training.wk2.project.entity.Genre;
import com.ss.training.wk2.project.entity.Publisher;
import com.ss.training.wk2.project.service.AdminService;
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
		
		assertEquals(keySingleCopy, 1);
		assertEquals(keyMultipleCopies, 2);
		assertEquals(keyNoCopies, 0);
	}
}