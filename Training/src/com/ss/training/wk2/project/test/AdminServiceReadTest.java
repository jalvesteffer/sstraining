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

public class AdminServiceReadTest {

	@Test
	public void readGenresByKey() {
		AdminService adminService = new AdminService();

		try {
			assertEquals(adminService.readGenres(1, null).get(0).getGenreName(), "Fantasy");
			assertEquals(adminService.readGenres(1, null).size(), 1);

			assertEquals(adminService.readGenres(2, null).get(0).getGenreName(), "Science Fiction");
			assertEquals(adminService.readGenres(2, null).size(), 1);

			assertEquals(adminService.readGenres(1000, null).size(), 0); // no result
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readGenresByName() {
		AdminService adminService = new AdminService();

		try {
			assertEquals(adminService.readGenres(null, "Fantasy").get(0).getGenreName(), "Fantasy");
			assertEquals(adminService.readGenres(null, "Fantasy").size(), 1);

			assertEquals(adminService.readGenres(null, "Science Fiction").get(0).getGenreName(), "Science Fiction");
			assertEquals(adminService.readGenres(null, "Science Fiction").size(), 1);

			assertEquals(adminService.readGenres(null, "Invalid").size(), 0); // no result
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readGenresReadAll() {
		AdminService adminService = new AdminService();
		List<Genre> genres = new ArrayList<>();
		try {
			genres = adminService.readGenres(null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(genres.get(0).getGenreName(), "Fantasy");
		assertEquals(genres.get(1).getGenreName(), "Science Fiction");
		assertEquals(genres.get(2).getGenreName(), "Economics");
		assertEquals(genres.size(), 23); // total results
	}

	@Test
	public void readAuthorsByKey() {
		AdminService adminService = new AdminService();

		try {
			assertEquals(adminService.readAuthors(1, null).get(0).getAuthorName(), "Neil Gaiman");
			assertEquals(adminService.readAuthors(1, null).size(), 1);

			assertEquals(adminService.readAuthors(2, null).size(), 0); // no results

			assertEquals(adminService.readAuthors(3, null).get(0).getAuthorName(), "Stephen Hawking");
			assertEquals(adminService.readAuthors(3, null).size(), 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readAuthorsByName() {
		AdminService adminService = new AdminService();

		try {
			assertEquals(adminService.readAuthors(null, "Neil Gaiman").get(0).getAuthorName(), "Neil Gaiman");
			assertEquals(adminService.readAuthors(null, "Neil Gaiman").size(), 1);

			assertEquals(adminService.readAuthors(null, "Stephen Hawking").get(0).getAuthorName(), "Stephen Hawking");
			assertEquals(adminService.readAuthors(null, "Stephen Hawking").size(), 1);

			assertEquals(adminService.readAuthors(null, "Invalid").size(), 0); // no result
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readAuthorsReadAll() {
		AdminService adminService = new AdminService();
		List<Author> authors = new ArrayList<>();

		try {
			authors = adminService.readAuthors(null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(authors.get(0).getAuthorName(), "Neil Gaiman");
		assertEquals(authors.get(1).getAuthorName(), "Stephen Hawking");
		assertEquals(authors.get(2).getAuthorName(), "Dale Carnegie");
		assertEquals(authors.size(), 25); // total results
	}

	@Test
	public void readBorrowersbyKey() {
		AdminService adminService = new AdminService();

		try {
			assertEquals(adminService.readBorrowers(1, null).get(0).getName(), "John Doe");
			assertEquals(adminService.readBorrowers(1, null).size(), 1);

			assertEquals(adminService.readBorrowers(2, null).get(0).getName(), "Jack Smith");
			assertEquals(adminService.readBorrowers(2, null).size(), 1);

			assertEquals(adminService.readBorrowers(1000, null).size(), 0); // no results
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readBorrowersbyName() {
		AdminService adminService = new AdminService();

		try {
			assertEquals(adminService.readBorrowers(null, "John Doe").get(0).getName(), "John Doe");
			assertEquals(adminService.readBorrowers(null, "John Doe").size(), 1);

			assertEquals(adminService.readBorrowers(null, "Jack Smith").get(0).getName(), "Jack Smith");
			assertEquals(adminService.readBorrowers(null, "Jack Smith").size(), 1);

			assertEquals(adminService.readBorrowers(null, "Invalid").size(), 0); // no result
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readBorrowersReadAll() {
		AdminService adminService = new AdminService();
		List<Borrower> borrowers = new ArrayList<>();

		try {
			borrowers = adminService.readBorrowers(null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(borrowers.get(0).getName(), "John Doe");
		assertEquals(borrowers.get(1).getName(), "Jack Smith");
		assertEquals(borrowers.get(2).getName(), "Amy Johnson");
		assertEquals(borrowers.size(), 20); // total results
	}

	@Test
	public void readPublishersbyKey() {
		AdminService adminService = new AdminService();

		try {
			assertEquals(adminService.readPublishers(1, null).get(0).getPublisherName(), "Penguin Random House");
			assertEquals(adminService.readPublishers(1, null).size(), 1);

			assertEquals(adminService.readPublishers(2, null).get(0).getPublisherName(), "Hachette");
			assertEquals(adminService.readPublishers(2, null).size(), 1);

			assertEquals(adminService.readPublishers(1000, null).size(), 0); // no results
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readPublishersbyName() {
		AdminService adminService = new AdminService();

		try {
			assertEquals(adminService.readPublishers(null, "Penguin Random House").get(0).getPublisherName(),
					"Penguin Random House");
			assertEquals(adminService.readPublishers(null, "Penguin Random House").size(), 1);

			assertEquals(adminService.readPublishers(null, "Hachette").get(0).getPublisherName(), "Hachette");
			assertEquals(adminService.readPublishers(null, "Hachette").size(), 1);

			assertEquals(adminService.readPublishers(null, "Invalid").size(), 0); // no result
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readPublishersReadAll() {
		AdminService adminService = new AdminService();
		List<Publisher> publishers = new ArrayList<>();

		try {
			publishers = adminService.readPublishers(null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(publishers.get(0).getPublisherName(), "Penguin Random House");
		assertEquals(publishers.get(1).getPublisherName(), "Hachette");
		assertEquals(publishers.get(2).getPublisherName(), "HarperCollins");
		assertEquals(publishers.size(), 22); // total results
	}
	
	@Test
	public void readBranchesbyKey() {
		AdminService adminService = new AdminService();

		try {
			assertEquals(adminService.readBranches(1, null).get(0).getBranchName(), "City of Fairfax Regional Library");
			assertEquals(adminService.readBranches(1, null).size(), 1);

			assertEquals(adminService.readBranches(2, null).get(0).getBranchName(), "Fairfax County Law Library");
			assertEquals(adminService.readBranches(2, null).size(), 1);

			assertEquals(adminService.readBranches(1000, null).size(), 0); // no results
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readBranchesbyName() {
		AdminService adminService = new AdminService();

		try {
			assertEquals(adminService.readBranches(null, "City of Fairfax Regional Library").get(0).getBranchName(),
					"City of Fairfax Regional Library");
			assertEquals(adminService.readBranches(null, "City of Fairfax Regional Library").size(), 1);

			assertEquals(adminService.readBranches(null, "Fairfax County Law Library").get(0).getBranchName(), "Fairfax County Law Library");
			assertEquals(adminService.readBranches(null, "Fairfax County Law Library").size(), 1);

			assertEquals(adminService.readBranches(null, "Invalid").size(), 0); // no result
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readBranchesReadAll() {
		AdminService adminService = new AdminService();
		List<Branch> branches = new ArrayList<>();

		try {
			branches = adminService.readBranches(null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(branches.get(0).getBranchName(), "City of Fairfax Regional Library");
		assertEquals(branches.get(1).getBranchName(), "Fairfax County Law Library");
		assertEquals(branches.get(2).getBranchName(), "Johnson Center Library");
		assertEquals(branches.size(), 20); // total results
	}
	
	@Test
	public void readBooksbyKey() {
		AdminService adminService = new AdminService();

		try {
			assertEquals(adminService.readBooks(1, null).get(0).getTitle(), "A Brief History of Time");
			assertEquals(adminService.readBooks(1, null).size(), 1);

			assertEquals(adminService.readBooks(2, null).get(0).getTitle(), "The Graveyard Book");
			assertEquals(adminService.readBooks(2, null).size(), 1);
			
			assertEquals(adminService.readBooks(3, null).size(), 0); // no results
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readBooksbyName() {
		AdminService adminService = new AdminService();

		try {
			assertEquals(adminService.readBooks(null, "A Brief History of Time").get(0).getTitle(),
					"A Brief History of Time");
			assertEquals(adminService.readBooks(null, "A Brief History of Time").size(), 1);

			assertEquals(adminService.readBooks(null, "The Graveyard Book").get(0).getTitle(), "The Graveyard Book");
			assertEquals(adminService.readBooks(null, "The Graveyard Book").size(), 1);

			assertEquals(adminService.readBooks(null, "Invalid").size(), 0); // no result
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readBooksReadAll() {
		AdminService adminService = new AdminService();
		List<Book> books = new ArrayList<>();

		try {
			books = adminService.readBooks(null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(books.get(0).getTitle(), "A Brief History of Time");
		assertEquals(books.get(1).getTitle(), "The Graveyard Book");
		assertEquals(books.get(2).getTitle(), "How to Survive");
		assertEquals(books.size(), 22); // total results
	}
	
	@Test
	public void getGenresByBookId() {
		AdminService adminService = new AdminService();
		List<Genre> genres = new ArrayList<>();
		List<Genre> genresInvalidId = new ArrayList<>();
		
		try {
			genres = adminService.getGenresByBookId(1);
			genresInvalidId = adminService.getGenresByBookId(1000);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(genres.size(), 2); // book has two genres
		assertEquals(genresInvalidId.size(), 0); // no result
	}
	
	@Test
	public void getGenresByNotBookId() {
		AdminService adminService = new AdminService();
		List<Genre> genres = new ArrayList<>();
		List<Genre> genresInvalidId = new ArrayList<>();
		
		try {
			genres = adminService.getGenresByNotBookId(1);
			genresInvalidId = adminService.getGenresByNotBookId(1000);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(genres.size(), 21);
		assertEquals(genresInvalidId.size(), 23); // returns all genres
	}
	
	@Test
	public void getBookLoansDue() {
		AdminService adminService = new AdminService();
		List<BookLoans> loansDueList = new ArrayList<>();
		
		try {
			loansDueList = adminService.getBookLoansDue();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(loansDueList.size(), 6);
	}
	
	@Test
	public void getBookKey() {
		AdminService adminService = new AdminService();
		int key1 = -1;
		int key2 = -1;
		
		try {
			key1 = adminService.getBookKey("The Graveyard Book");
			key2 = adminService.getBookKey("Invalid Book");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		assertEquals(key1, 2);
		assertEquals(key2, -1);
		
	}
	
	@Test
	public void getBookById() {
		AdminService adminService = new AdminService();
		Book book = new Book();
		Book bookInvalid = new Book();
		
		try {
			book = adminService.getBookById(1);
			bookInvalid = adminService.getBookById(1000);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		assertEquals(book.getTitle(), "A Brief History of Time");
		assertEquals(bookInvalid, null);
		
	}
	
	@Test
	public void getPublisherKey() {
		AdminService adminService = new AdminService();
		int key1 = -1;
		int key2 = -1;
		
		try {
			key1 = adminService.getPublisherKey("Penguin Random House");
			key2 = adminService.getPublisherKey("Invalid Publisher");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		assertEquals(key1, 1);
		assertEquals(key2, -1);
		
	}
	
	@Test
	public void getAuthorKey() {
		AdminService adminService = new AdminService();
		int key1 = -1;
		int key2 = -1;
		
		try {
			key1 = adminService.getAuthorKey("Stephen Hawking");
			key2 = adminService.getAuthorKey("Invalid");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		assertEquals(key1, 3);
		assertEquals(key2, -1);
		
	}
	
	@Test
	public void getAuthorsByBookId() {
		AdminService adminService = new AdminService();
		List<Author> authorsSingle = new ArrayList<>();
		List<Author> authorsMultiple = new ArrayList<>();
		List<Author> authorsInvalidId = new ArrayList<>();
		
		try {
			authorsSingle = adminService.getAuthorsByBookId(2);
			authorsMultiple = adminService.getAuthorsByBookId(13);
			authorsInvalidId = adminService.getAuthorsByBookId(1000);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(authorsSingle.size(), 1);
		assertEquals(authorsMultiple.size(), 3);
		assertEquals(authorsInvalidId.size(), 0);
	}
	
	
}
