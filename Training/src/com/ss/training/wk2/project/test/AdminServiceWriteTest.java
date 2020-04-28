package com.ss.training.wk2.project.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ss.training.wk2.project.dao.BookCopiesDAO;
import com.ss.training.wk2.project.entity.Author;
import com.ss.training.wk2.project.entity.Book;
import com.ss.training.wk2.project.entity.BookCopies;
import com.ss.training.wk2.project.entity.BookLoans;
import com.ss.training.wk2.project.entity.Borrower;
import com.ss.training.wk2.project.entity.Branch;
import com.ss.training.wk2.project.entity.Genre;
import com.ss.training.wk2.project.entity.Publisher;
import com.ss.training.wk2.project.service.AdminService;
import com.ss.training.wk2.project.service.BorrowerService;
import com.ss.training.wk2.project.service.ConnectionUtil;
import com.ss.training.wk2.project.service.LibrarianService;

public class AdminServiceWriteTest {

	@Test
	public void saveGenre() {
		
		AdminService adminService = new AdminService();

		Genre genre = null;
		
		try {
			// get genre id 27: Genre B
			genre = adminService.readGenres(27, null).get(0);
			assertEquals(genre.getGenreName(), "Genre B");
			
			// set Genre to Genre A
			genre.setGenreName("Genre A");
			adminService.saveGenre(genre);
			genre = adminService.readGenres(27, null).get(0);
			assertEquals(genre.getGenreName(), "Genre A");
			
			// reset value
			genre.setGenreName("Genre B");
			adminService.saveGenre(genre);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void saveAuthor() {
		
		AdminService adminService = new AdminService();

		Author author = null;
		
		try {
			// get author name with id 58: Jason Alvesteffer
			author = adminService.readAuthors(58, null).get(0);
			assertEquals(author.getAuthorName(), "Jason Alvesteffer");
			
			// set author to Jay Alvesteffer
			author.setAuthorName("Jay Alvesteffer");
			adminService.saveAuthor(author);
			author = adminService.readAuthors(58, null).get(0);
			assertEquals(author.getAuthorName(), "Jay Alvesteffer");
			
			// reset value
			author.setAuthorName("Jason Alvesteffer");
			adminService.saveAuthor(author);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void saveBorrower() {
		
		AdminService adminService = new AdminService();

		Borrower borrower = null;
		
		try {
			// get author name with id 1: John Doe
			borrower = adminService.readBorrowers(1, null).get(0);
			assertEquals(borrower.getName(), "John Doe");
			
			// set author to Johnny Doe
			borrower.setName("Johnny Doe");
			adminService.saveBorrower(borrower);
			borrower = adminService.readBorrowers(1, null).get(0);
			assertEquals(borrower.getName(), "Johnny Doe");
			
			// reset value
			borrower.setName("John Doe");
			adminService.saveBorrower(borrower);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void savePublisher() {
		
		AdminService adminService = new AdminService();

		Publisher publisher = null;
		
		try {
			// get author name with id 1: Penguin Random House
			publisher = adminService.readPublishers(1, null).get(0);
			assertEquals(publisher.getPublisherName(), "Penguin Random House");
			
			// set publisher to new value
			publisher.setPublisherName("A Penguin House");
			adminService.savePublisher(publisher);
			publisher = adminService.readPublishers(1, null).get(0);
			assertEquals(publisher.getPublisherName(), "A Penguin House");
			
			// reset value
			publisher.setPublisherName("Penguin Random House");
			adminService.savePublisher(publisher);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void saveBranch() {
		
		AdminService adminService = new AdminService();

		Branch branch = null;
		
		try {
			// get current value
			branch = adminService.readBranches(1, null).get(0);
			assertEquals(branch.getBranchName(), "City of Fairfax Regional Library");
			
			// set to new value
			branch.setBranchName("Fairfax Library");
			adminService.saveBranch(branch);
			branch = adminService.readBranches(1, null).get(0);
			assertEquals(branch.getBranchName(), "Fairfax Library");
			
			// reset value
			branch.setBranchName("City of Fairfax Regional Library");
			adminService.saveBranch(branch);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void saveBook() {
		
		AdminService adminService = new AdminService();

		Book book = null;
		
		try {
			// get current value
			book = adminService.readBooks(1, null).get(0);
			assertEquals(book.getTitle(), "A Brief History of Time");
			
			// set to new value
			book.setTitle("A Long History of Time");
			adminService.saveBook(book);
			book = adminService.readBooks(1, null).get(0);
			assertEquals(book.getTitle(), "A Long History of Time");
			
			// reset value
			book.setTitle("A Brief History of Time");
			adminService.saveBook(book);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void insertAndRemoveBookGenreRelationship() {
		
		AdminService adminService = new AdminService();

		List<Genre> genres = null;
		
		try {
			// book id 1 currently has 2 genres
			genres = adminService.getGenresByBookId(1);
			assertEquals(genres.size(), 2);
			
			// add a genre, now has 3 genres
			adminService.insertBookGenreRelationship(1, 1);
			assertEquals(genres.size(), 3);
			
			// remove that genre, now has 2 genres
			adminService.removeBookGenreRelationship(1, 1);
			assertEquals(genres.size(), 2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void insertAuthor() {
		
		AdminService adminService = new AdminService();

		List<Author> authors = null;
		
		Author author = null;
		
		try {
			// get a list of all authors
			authors = adminService.readAuthors(null, null);
			assertEquals(authors.size(), 25);
			
			// add an author
			author = new Author();
			author.setAuthorName("Inserted Author");
			adminService.insertAuthor(author);
			
			// reread authors, and check size
			authors = adminService.readAuthors(null, null);
			assertEquals(authors.size(), 26);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Test
	public void insertBookAuthorRelationship() {
		
		AdminService adminService = new AdminService();

		List<Author> authors = null;
		
		try {
			// book id 1 currently has 2 genres
			authors = adminService.getAuthorsByBookId(1);
			assertEquals(authors.size(), 2);
			
			// add a genre, now has 3 genres
			adminService.insertBookAuthorRelationship(1, 18);
			authors = adminService.getAuthorsByBookId(1);
			assertEquals(authors.size(), 3);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateDueDate() {
		
		AdminService adminService = new AdminService();

		List<BookLoans> bookLoans = null;
		
		BookLoans loan = null;
		
		try {
			// see number of overdue books
			bookLoans = adminService.getBookLoansDue();
			assertEquals(bookLoans.size(), 5);
			
			// get overdue loan
			loan = bookLoans.get(0);
			
			// extend due date so it is no longer overdue
			adminService.updateDueDate(loan, "2020-08-01 00:00:00");
			
			// get updated count
			bookLoans = adminService.getBookLoansDue();
			assertEquals(bookLoans.size(), 4);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}