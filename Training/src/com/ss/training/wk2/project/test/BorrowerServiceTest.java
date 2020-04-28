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

public class BorrowerServiceTest {

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
	/*	
	@Test
	public void readBranchesbyName() {
		AdminService librarianService = new AdminService();

		try {
			assertEquals(librarianService.readBranches(null, "City of Fairfax Regional Library").get(0).getBranchName(),
					"City of Fairfax Regional Library");
			assertEquals(librarianService.readBranches(null, "City of Fairfax Regional Library").size(), 1);

			assertEquals(librarianService.readBranches(null, "Fairfax County Law Library").get(0).getBranchName(), "Fairfax County Law Library");
			assertEquals(librarianService.readBranches(null, "Fairfax County Law Library").size(), 1);

			assertEquals(librarianService.readBranches(null, "Invalid").size(), 0); // no result
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void readBranchesReadAll() {
		AdminService librarianService = new AdminService();
		List<Branch> branches = new ArrayList<>();

		try {
			branches = librarianService.readBranches(null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(branches.get(0).getBranchName(), "City of Fairfax Regional Library");
		assertEquals(branches.get(1).getBranchName(), "Fairfax County Law Library");
		assertEquals(branches.get(2).getBranchName(), "Johnson Center Library");
		assertEquals(branches.size(), 20); // total results
	}
	
	

	
	
	
	
	
	
	
	
	
	
	

	
	@Test
	public void readGenresByKey() {
		AdminService librarianService = new AdminService();

		try {
			assertEquals(librarianService.readGenres(1, null).get(0).getGenreName(), "Fantasy");
			assertEquals(librarianService.readGenres(1, null).size(), 1);

			assertEquals(librarianService.readGenres(2, null).get(0).getGenreName(), "Science Fiction");
			assertEquals(librarianService.readGenres(2, null).size(), 1);

			assertEquals(librarianService.readGenres(1000, null).size(), 0); // no result
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readGenresByName() {
		AdminService librarianService = new AdminService();

		try {
			assertEquals(librarianService.readGenres(null, "Fantasy").get(0).getGenreName(), "Fantasy");
			assertEquals(librarianService.readGenres(null, "Fantasy").size(), 1);

			assertEquals(librarianService.readGenres(null, "Science Fiction").get(0).getGenreName(), "Science Fiction");
			assertEquals(librarianService.readGenres(null, "Science Fiction").size(), 1);

			assertEquals(librarianService.readGenres(null, "Invalid").size(), 0); // no result
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readGenresReadAll() {
		AdminService librarianService = new AdminService();
		List<Genre> genres = new ArrayList<>();
		try {
			genres = librarianService.readGenres(null, null);
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
		AdminService librarianService = new AdminService();

		try {
			assertEquals(librarianService.readAuthors(1, null).get(0).getAuthorName(), "Neil Gaiman");
			assertEquals(librarianService.readAuthors(1, null).size(), 1);

			assertEquals(librarianService.readAuthors(2, null).size(), 0); // no results

			assertEquals(librarianService.readAuthors(3, null).get(0).getAuthorName(), "Stephen Hawking");
			assertEquals(librarianService.readAuthors(3, null).size(), 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readAuthorsByName() {
		AdminService librarianService = new AdminService();

		try {
			assertEquals(librarianService.readAuthors(null, "Neil Gaiman").get(0).getAuthorName(), "Neil Gaiman");
			assertEquals(librarianService.readAuthors(null, "Neil Gaiman").size(), 1);

			assertEquals(librarianService.readAuthors(null, "Stephen Hawking").get(0).getAuthorName(), "Stephen Hawking");
			assertEquals(librarianService.readAuthors(null, "Stephen Hawking").size(), 1);

			assertEquals(librarianService.readAuthors(null, "Invalid").size(), 0); // no result
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readAuthorsReadAll() {
		AdminService librarianService = new AdminService();
		List<Author> authors = new ArrayList<>();

		try {
			authors = librarianService.readAuthors(null, null);
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
		AdminService librarianService = new AdminService();

		try {
			assertEquals(librarianService.readBorrowers(1, null).get(0).getName(), "John Doe");
			assertEquals(librarianService.readBorrowers(1, null).size(), 1);

			assertEquals(librarianService.readBorrowers(2, null).get(0).getName(), "Jack Smith");
			assertEquals(librarianService.readBorrowers(2, null).size(), 1);

			assertEquals(librarianService.readBorrowers(1000, null).size(), 0); // no results
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readBorrowersbyName() {
		AdminService librarianService = new AdminService();

		try {
			assertEquals(librarianService.readBorrowers(null, "John Doe").get(0).getName(), "John Doe");
			assertEquals(librarianService.readBorrowers(null, "John Doe").size(), 1);

			assertEquals(librarianService.readBorrowers(null, "Jack Smith").get(0).getName(), "Jack Smith");
			assertEquals(librarianService.readBorrowers(null, "Jack Smith").size(), 1);

			assertEquals(librarianService.readBorrowers(null, "Invalid").size(), 0); // no result
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readBorrowersReadAll() {
		AdminService librarianService = new AdminService();
		List<Borrower> borrowers = new ArrayList<>();

		try {
			borrowers = librarianService.readBorrowers(null, null);
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
		AdminService librarianService = new AdminService();

		try {
			assertEquals(librarianService.readPublishers(1, null).get(0).getPublisherName(), "Penguin Random House");
			assertEquals(librarianService.readPublishers(1, null).size(), 1);

			assertEquals(librarianService.readPublishers(2, null).get(0).getPublisherName(), "Hachette");
			assertEquals(librarianService.readPublishers(2, null).size(), 1);

			assertEquals(librarianService.readPublishers(1000, null).size(), 0); // no results
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readPublishersbyName() {
		AdminService librarianService = new AdminService();

		try {
			assertEquals(librarianService.readPublishers(null, "Penguin Random House").get(0).getPublisherName(),
					"Penguin Random House");
			assertEquals(librarianService.readPublishers(null, "Penguin Random House").size(), 1);

			assertEquals(librarianService.readPublishers(null, "Hachette").get(0).getPublisherName(), "Hachette");
			assertEquals(librarianService.readPublishers(null, "Hachette").size(), 1);

			assertEquals(librarianService.readPublishers(null, "Invalid").size(), 0); // no result
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readPublishersReadAll() {
		AdminService librarianService = new AdminService();
		List<Publisher> publishers = new ArrayList<>();

		try {
			publishers = librarianService.readPublishers(null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(publishers.get(0).getPublisherName(), "Penguin Random House");
		assertEquals(publishers.get(1).getPublisherName(), "Hachette");
		assertEquals(publishers.get(2).getPublisherName(), "HarperCollins");
		assertEquals(publishers.size(), 22); // total results
	}
	

	
	@Test
	public void readBooksbyKey() {
		AdminService librarianService = new AdminService();

		try {
			assertEquals(librarianService.readBooks(1, null).get(0).getTitle(), "A Brief History of Time");
			assertEquals(librarianService.readBooks(1, null).size(), 1);

			assertEquals(librarianService.readBooks(2, null).get(0).getTitle(), "The Graveyard Book");
			assertEquals(librarianService.readBooks(2, null).size(), 1);
			
			assertEquals(librarianService.readBooks(3, null).size(), 0); // no results
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readBooksbyName() {
		AdminService librarianService = new AdminService();

		try {
			assertEquals(librarianService.readBooks(null, "A Brief History of Time").get(0).getTitle(),
					"A Brief History of Time");
			assertEquals(librarianService.readBooks(null, "A Brief History of Time").size(), 1);

			assertEquals(librarianService.readBooks(null, "The Graveyard Book").get(0).getTitle(), "The Graveyard Book");
			assertEquals(librarianService.readBooks(null, "The Graveyard Book").size(), 1);

			assertEquals(librarianService.readBooks(null, "Invalid").size(), 0); // no result
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readBooksReadAll() {
		AdminService librarianService = new AdminService();
		List<Book> books = new ArrayList<>();

		try {
			books = librarianService.readBooks(null, null);
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
		AdminService librarianService = new AdminService();
		List<Genre> genres = new ArrayList<>();
		List<Genre> genresInvalidId = new ArrayList<>();
		
		try {
			genres = librarianService.getGenresByBookId(1);
			genresInvalidId = librarianService.getGenresByBookId(1000);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(genres.size(), 2); // book has two genres
		assertEquals(genresInvalidId.size(), 0); // no result
	}
	
	@Test
	public void getGenresByNotBookId() {
		AdminService librarianService = new AdminService();
		List<Genre> genres = new ArrayList<>();
		List<Genre> genresInvalidId = new ArrayList<>();
		
		try {
			genres = librarianService.getGenresByNotBookId(1);
			genresInvalidId = librarianService.getGenresByNotBookId(1000);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(genres.size(), 21);
		assertEquals(genresInvalidId.size(), 23); // returns all genres
	}
	
	@Test
	public void getBookLoansDue() {
		AdminService librarianService = new AdminService();
		List<BookLoans> loansDueList = new ArrayList<>();
		
		try {
			loansDueList = librarianService.getBookLoansDue();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(loansDueList.size(), 6);
	}
	
	@Test
	public void getBookKey() {
		AdminService librarianService = new AdminService();
		int key1 = -1;
		int key2 = -1;
		
		try {
			key1 = librarianService.getBookKey("The Graveyard Book");
			key2 = librarianService.getBookKey("Invalid Book");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		assertEquals(key1, 2);
		assertEquals(key2, -1);
		
	}
	
	@Test
	public void getBookById() {
		AdminService librarianService = new AdminService();
		Book book = new Book();
		Book bookInvalid = new Book();
		
		try {
			book = librarianService.getBookById(1);
			bookInvalid = librarianService.getBookById(1000);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		assertEquals(book.getTitle(), "A Brief History of Time");
		assertEquals(bookInvalid, null);
		
	}
	
	@Test
	public void getPublisherKey() {
		AdminService librarianService = new AdminService();
		int key1 = -1;
		int key2 = -1;
		
		try {
			key1 = librarianService.getPublisherKey("Penguin Random House");
			key2 = librarianService.getPublisherKey("Invalid Publisher");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		assertEquals(key1, 1);
		assertEquals(key2, -1);
		
	}
	
	@Test
	public void getAuthorKey() {
		AdminService librarianService = new AdminService();
		int key1 = -1;
		int key2 = -1;
		
		try {
			key1 = librarianService.getAuthorKey("Stephen Hawking");
			key2 = librarianService.getAuthorKey("Invalid");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		assertEquals(key1, 3);
		assertEquals(key2, -1);
		
	}
	
	@Test
	public void getAuthorsByBookId() {
		AdminService librarianService = new AdminService();
		List<Author> authorsSingle = new ArrayList<>();
		List<Author> authorsMultiple = new ArrayList<>();
		List<Author> authorsInvalidId = new ArrayList<>();
		
		try {
			authorsSingle = librarianService.getAuthorsByBookId(2);
			authorsMultiple = librarianService.getAuthorsByBookId(13);
			authorsInvalidId = librarianService.getAuthorsByBookId(1000);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(authorsSingle.size(), 1);
		assertEquals(authorsMultiple.size(), 3);
		assertEquals(authorsInvalidId.size(), 0);
	}
	
*/	
}
