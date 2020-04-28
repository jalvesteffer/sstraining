/**
 * 
 */
package com.ss.training.wk2.project.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.wk2.project.dao.AuthorDAO;
import com.ss.training.wk2.project.dao.BookCopiesDAO;
import com.ss.training.wk2.project.dao.BookDAO;
import com.ss.training.wk2.project.dao.BookLoansDAO;
import com.ss.training.wk2.project.dao.BorrowerDAO;
import com.ss.training.wk2.project.dao.BranchDAO;
import com.ss.training.wk2.project.dao.GenreDAO;
import com.ss.training.wk2.project.dao.PublisherDAO;
import com.ss.training.wk2.project.entity.Author;
import com.ss.training.wk2.project.entity.Book;
import com.ss.training.wk2.project.entity.BookCopies;
import com.ss.training.wk2.project.entity.BookLoans;
import com.ss.training.wk2.project.entity.Borrower;
import com.ss.training.wk2.project.entity.Branch;
import com.ss.training.wk2.project.entity.Genre;
import com.ss.training.wk2.project.entity.Publisher;

/**
 * @author jalveste
 *
 */
/**
 * @author jalveste
 *
 */
public class AdminService {

	public ConnectionUtil connUtil = new ConnectionUtil();

	/**
	 * This method deletes all books matching the passed authorId
	 * 
	 * @param authorId delete books matching this authorId
	 * @throws SQLException 
	 */
	public void deleteBooksByAuthorId(Integer authorId) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			BookDAO bDAO = new BookDAO(conn);
			BookCopiesDAO bcDAO = new BookCopiesDAO(conn);
			BookLoansDAO blDAO = new BookLoansDAO(conn);

			// delete entries from all tables where authorId to be deleted is referred to
			blDAO.deleteBookLoansByAuthorId(authorId);
			bcDAO.deleteBookCopiesByAuthorId(authorId);
			bDAO.deleteBooksByAuthorId(authorId);

			// commit transaction and display success message
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			System.out.println("getBooksDue failed in AdminService");
			conn.rollback();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
	}

	public List<BookLoans> getBookLoansDue() throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			BookLoansDAO blDAO = new BookLoansDAO(conn);

			LocalDateTime dateTimeNow = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String strDateTimeNow = dateTimeNow.format(formatter);

			List<BookLoans> bookLoans = blDAO.readBookLoansDue(strDateTimeNow);

			// commit transaction and display success message
			conn.commit();
			return bookLoans;
		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			System.out.println("getBookLoansDue failed in AdminService");
			conn.rollback();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	public void updateDueDate(BookLoans loan, String newDate) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			BookLoansDAO blDAO = new BookLoansDAO(conn);

			blDAO.updateBookLoans(loan.getBookId(), loan.getBranchId(), loan.getCardNo(), loan.getDateOut(), newDate);

			// commit transaction and display success message
			conn.commit();

		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			System.out.println("Due date update failed in AdminService");
			conn.rollback();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}

	}

	public void insertBookAuthorRelationship(Integer bookId, Integer authorId) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			BookDAO bDAO = new BookDAO(conn);

			bDAO.addBookAuthorRelationship(bookId, authorId);

			// commit transaction and display success message
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			System.out.println("Book/Author insertion failed in AdminService");
			conn.rollback();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
	}

	public Integer getBookKey(String title) throws SQLException {
		Connection conn = null; // database connection
		Book book = null;

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			BookDAO bDAO = new BookDAO(conn);

			List<Book> books = bDAO.readBookByTitle(title);
			if (books != null && books.size() > 0) {
				book = books.get(0);
			}

			// commit transaction and display success message
			conn.commit();

			return book.getBookId();

		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			System.out.println("getBookKey failed in AdminService");
			conn.rollback();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	public Book getBookById(Integer bookId) throws SQLException {
		Connection conn = null; // database connection
		Book book = null;

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			BookDAO bDAO = new BookDAO(conn);

			List<Book> books = bDAO.readBookById(bookId);
			if (books != null && books.size() > 0) {
				book = books.get(0);
			}

			// commit transaction and display success message
			conn.commit();

			return book;

		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			System.out.println("getBookById failed in AdminService");
			conn.rollback();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	public Integer getPublisherKey(String name) throws SQLException {
		Connection conn = null; // database connection
		Publisher publisher = null;

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			PublisherDAO pDAO = new PublisherDAO(conn);

			List<Publisher> publishers = pDAO.readPublisherByName(name);
			if (publishers != null && publishers.size() > 0) {
				publisher = publishers.get(0);
			}

			// commit transaction and display success message
			conn.commit();

			return publisher.getPublisherId();

		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			System.out.println("Book/Genre insertion failed in AdminService");
			conn.rollback();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	public Integer getAuthorKey(String name) throws SQLException {
		Connection conn = null; // database connection
		Author author = null;

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			AuthorDAO aDAO = new AuthorDAO(conn);

			List<Author> authors = aDAO.readAuthorByName(name);
			if (authors != null && authors.size() > 0) {
				author = authors.get(0);
			}

			// commit transaction and display success message
			conn.commit();

			return author.getAuthorId();

		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			System.out.println("Book/Genre insertion failed in AdminService");
			conn.rollback();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	public void insertAuthor(Author author) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			AuthorDAO aDAO = new AuthorDAO(conn);

			aDAO.addAuthor(author);

			// commit transaction and display success message
			conn.commit();

		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			System.out.println("Book/Genre insertion failed in AdminService");
			conn.rollback();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}

	}

	public void insertBookGenreRelationship(Integer genreId, Integer bookId) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			GenreDAO gDAO = new GenreDAO(conn);

			gDAO.addBookGenreRelationship(genreId, bookId);

			// commit transaction and display success message
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			System.out.println("Book/Genre insertion failed in AdminService");
			conn.rollback();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
	}

	public void removeBookGenreRelationship(Integer genreId, Integer bookId) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			GenreDAO gDAO = new GenreDAO(conn);

			gDAO.removeBookGenreRelationship(genreId, bookId);

			// commit transaction and display success message
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			System.out.println("Book/Genre insertion failed in AdminService");
			conn.rollback();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
	}

	public List<Author> getAuthorsByBookId(Integer bookId) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			AuthorDAO aDAO = new AuthorDAO(conn);

			return aDAO.readAllAuthorsByBookId(bookId);
		} catch (Exception e) {
			System.out.println("Error in AdminService getting authors by bookId");
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	public List<Genre> getGenresByBookId(Integer bookId) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			GenreDAO gDAO = new GenreDAO(conn);

			return gDAO.readGenresByBookId(bookId);

		} catch (Exception e) {
			System.out.println("Error in AdminService getting genres");
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	public List<Genre> getGenresByNotBookId(Integer bookId) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			GenreDAO gDAO = new GenreDAO(conn);

			return gDAO.readGenresByNotBookId(bookId);

		} catch (Exception e) {
			System.out.println("Error in AdminService getting genres");
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	/**
	 * For Book objects. The method provides update, deletion and insertion database
	 * operations for passed object of this type
	 * 
	 * @param book
	 * @throws SQLException
	 */
	public void saveBook(Book book) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			BookDAO bDAO = new BookDAO(conn);
			BookCopiesDAO bcDAO = new BookCopiesDAO(conn);
			BookLoansDAO blDAO = new BookLoansDAO(conn);

			// perform write operation depending on which object variables are set
			// update case where both a key and name are given
			if (book.getBookId() != null && book.getTitle() != null) {
				bDAO.updateBook(book);
			}

			// deletion case when an id is given but no name
			else if (book.getBookId() != null) {
				
				
				
				
				bcDAO.deleteBookCopiesByBookId(book.getBookId());
				
				
				
				
				bDAO.deleteBook(book);
				System.out.println("\nBook deleted");
			}

			// insertion case otherwise
			else {
				bDAO.addBook(book);
				System.out.println("Adding " + book.getTitle() + " to books");
			}

			// commit transaction and display success message
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			System.out.println("Book transaction failed in AdminService");
			conn.rollback();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
	}

	/**
	 * For Branch objects. The method provides update, deletion and insertion
	 * database operations for passed object of this type
	 * 
	 * @param branch
	 * @throws SQLException
	 */
	public void saveBranch(Branch branch) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			BranchDAO brDAO = new BranchDAO(conn);
			BookLoansDAO blDAO = new BookLoansDAO(conn);
			BookCopiesDAO bcDAO = new BookCopiesDAO(conn);

			// perform write operation depending on which object variables are set
			// update case where both a key and name are given
			if (branch.getBranchId() != null && branch.getBranchName() != null) {
				brDAO.updateBranch(branch);
				System.out.println("Updated Branch");
			}

			// deletion case when an id is given but no name
			else if (branch.getBranchId() != null) {
				blDAO.deleteBookLoansByBranchId(branch.getBranchId());
				bcDAO.deleteBookCopiesByBranchId(branch.getBranchId());
				brDAO.deleteBranch(branch);
				System.out.println("\nBranch deleted");
			}

			// insertion case otherwise
			else {
				brDAO.addBranch(branch);
				System.out.println("Adding " + branch.getBranchName() + " to branches");
			}

			// commit transaction and display success message
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			System.out.println("Branch transaction failed in AdminService");
			conn.rollback();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
	}

	/**
	 * For Publisher objects. The method provides update, deletion and insertion
	 * database operations for passed object of this type
	 * 
	 * @param publisher
	 * @throws SQLException
	 */
	public void savePublisher(Publisher publisher) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			PublisherDAO pDAO = new PublisherDAO(conn);
			BookDAO bDAO = new BookDAO(conn);
			BookLoansDAO blDAO = new BookLoansDAO(conn);
			BookCopiesDAO bcDAO = new BookCopiesDAO(conn);

			// perform write operation depending on which object variables are set
			// update case where both a key and name are given
			if (publisher.getPublisherId() != null && publisher.getPublisherName() != null) {
				pDAO.updatePublisher(publisher);
			}

			// deletion case when an id is given but no name
			else if (publisher.getPublisherId() != null) {
				blDAO.deleteBookLoansByPubId(publisher.getPublisherId());
				bcDAO.deleteBookCopiesByPubId(publisher.getPublisherId());
				bDAO.deleteBooksByPubId(publisher.getPublisherId());
				pDAO.deletePublisher(publisher);
				System.out.println("\nPublisher deleted");
			}

			// insertion case otherwise
			else {
				pDAO.addPublisher(publisher);
				System.out.println("Adding " + publisher.getPublisherName() + " to publishers");
			}

			// commit transaction and display success message
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			System.out.println("Publisher transaction failed in AdminService");
			conn.rollback();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
	}

	/**
	 * For Borrower objects. The method provides update, deletion and insertion
	 * database operations for passed object of this type
	 * 
	 * @param borrower
	 * @throws SQLException
	 */
	public void saveBorrower(Borrower borrower) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			BorrowerDAO borrDAO = new BorrowerDAO(conn);
			BookLoansDAO blDAO = new BookLoansDAO(conn);

			// perform write operation depending on which object variables are set
			// update case where both a key and name are given
			if (borrower.getCardNo() != null && borrower.getName() != null) {
				borrDAO.updateBorrower(borrower);
				System.out.println("Updated borrower");
			}

			// deletion case when an id is given but no name
			else if (borrower.getCardNo() != null) {
				blDAO.deleteBookLoansByCardNo(borrower.getCardNo());
				borrDAO.deleteBorrower(borrower);
				System.out.println("\nBorrower deleted");
			}

			// insertion case otherwise
			else {
				borrDAO.addBorrower(borrower);
				System.out.println("Adding " + borrower.getName() + " to borrowers");
			}

			// commit transaction and display success message
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			System.out.println("Borrower transaction failed in AdminService");
			conn.rollback();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
	}

	/**
	 * For Author objects. The method provides update, deletion and insertion
	 * database operations for passed object of this type
	 * 
	 * @param author
	 * @throws SQLException
	 */
	public void saveAuthor(Author author) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			AuthorDAO aDAO = new AuthorDAO(conn);
			

			// perform write operation depending on which object variables are set
			// update case where both a key and name are given
			if (author.getAuthorId() != null && author.getAuthorName() != null) {
				aDAO.updateAuthor(author);
			}

			// deletion case when an id is given but no name
			else if (author.getAuthorId() != null) {
				aDAO.deleteAuthor(author);
			}

			// insertion case otherwise
			else {
				aDAO.addAuthor(author);
				System.out.println("Adding " + author.getAuthorName() + " to authors");
			}

			// commit transaction and display success message
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			System.out.println("Author transaction failed in AdminService");
			conn.rollback();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
	}

	/**
	 * For Genre objects. The method provides update, deletion and insertion
	 * database operations for passed object of this type
	 * 
	 * @param genre
	 * @throws SQLException
	 */
	public void saveGenre(Genre genre) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO
			conn = connUtil.getConnection();
			GenreDAO gDAO = new GenreDAO(conn);

			// perform write operation depending on which object variables are set
			// update case where both a key and name are given
			if (genre.getGenreId() != null && genre.getGenreName() != null) {

				gDAO.updateGenre(genre);
			}

			// deletion case when an id is given but no name
			else if (genre.getGenreId() != null) {
				gDAO.deleteGenre(genre);
			}

			// insertion case otherwise
			else {
				gDAO.addGenre(genre);
				System.out.println("Adding " + genre.getGenreName() + " to genres");
			}

			// commit transaction and display success message
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {

			// transaction failed. Rollback changes made
			System.out.println("Genre transaction failed in AdminService");
			conn.rollback();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}

	}

	/**
	 * For Book objects. The method provides read operations for passed object of
	 * this type. A ready by primary key, read by name, or read everything operation
	 * can be performed depending on which method parameters are null
	 * 
	 * @param pk       primary key for this object type
	 * @param bookName name for this object type
	 * @return a list of this object type; null on failure
	 * @throws SQLException
	 */
	public List<Book> readBooks(Integer pk, String bookName) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO(s)
			conn = connUtil.getConnection();
			BookDAO bDAO = new BookDAO(conn);
			AuthorDAO aDAO = new AuthorDAO(conn);

			// perform read operation depending on which object variables are set
			// if primary key is provided, perform a read by primary key
			if (pk != null) {
				System.out.println("GET BOOKS BY PRIMARY KEY");
				List<Book> books = bDAO.readBookById(pk);
				return books;
			}

			// if a name is provided, perform a search by name
			else if (bookName != null) {

			}

			// otherwise, read everything
			else {
				List<Book> books = bDAO.readAllBooks();

				// populate books authors list
				for (Book b : books) {
					List<Author> authors = new ArrayList<>();
					authors = aDAO.readAllAuthorsByBookId(b.getBookId());

					b.setAuthors(authors);
				}

				// get publisher object

				return books;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
		return null;

	}

	/**
	 * For Branch objects. The method provides read operations for passed object of
	 * this type. A ready by primary key, read by name, or read everything operation
	 * can be performed depending on which method parameters are null
	 * 
	 * @param pk         primary key for this object type
	 * @param branchName name for this object type
	 * @return a list of this object type; null on failure
	 * @throws SQLException
	 */
	public List<Branch> readBranches(Integer pk, String branchName) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO(s)
			conn = connUtil.getConnection();
			BranchDAO brDAO = new BranchDAO(conn);

			// perform read operation depending on which object variables are set
			// if primary key is provided, perform a read by primary key
			if (pk != null) {
//				System.out.println("GET AUTHOR BY PRIMARY KEY");
//				List<Branch> branches = brDAO.readBranchById(pk);
//				return branches;
			}

			// if a name is provided, perform a search by name
			else if (branchName != null) {
				// searchBranches
			}

			// otherwise, read everything
			else {
				List<Branch> branches = brDAO.readAllBranches();
				return branches;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
		return null;

	}

	/**
	 * For Publisher objects. The method provides read operations for passed object
	 * of this type. A ready by primary key, read by name, or read everything
	 * operation can be performed depending on which method parameters are null
	 * 
	 * @param pk            primary key for this object type
	 * @param publisherName name for this object type
	 * @return a list of this object type; null on failure
	 * @throws SQLException
	 */
	public List<Publisher> readPublishers(Integer pk, String publisherName) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO(s)
			conn = connUtil.getConnection();
			PublisherDAO pDAO = new PublisherDAO(conn);
//			BookDAO bDAO = new BookDAO(conn);

			// perform read operation depending on which object variables are set
			// if primary key is provided, perform a read by primary key
			if (pk != null) {
				List<Publisher> publishers = pDAO.readPublisherById(pk);
				return publishers;
			}

			// if a name is provided, perform a search by name
			else if (publisherName != null) {
				List<Publisher> publishers = pDAO.readPublisherByName(publisherName);
				return publishers;
			}

			// otherwise, read everything
			else {
				List<Publisher> publishers = pDAO.readAllPublishers();
				return publishers;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
		return null;

	}

	/**
	 * For Borrower objects. The method provides read operations for passed object
	 * of this type. A ready by primary key, read by name, or read everything
	 * operation can be performed depending on which method parameters are null
	 * 
	 * @param pk           primary key for this object type
	 * @param borrowerName name for this object type
	 * @return a list of this object type; null on failure
	 * @throws SQLException
	 */
	public List<Borrower> readBorrowers(Integer pk, String borrowerName) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO(s)
			conn = connUtil.getConnection();
			BorrowerDAO borrDAO = new BorrowerDAO(conn);
//			BookDAO bDAO = new BookDAO(conn);

			// perform read operation depending on which object variables are set
			// if primary key is provided, perform a read by primary key
			if (pk != null) {
//				System.out.println("GET AUTHOR BY PRIMARY KEY");
//				List<Borrower> borrowers = borrDAO.readBorrowerById(pk);
//				return borrowers;
			}

			// if a name is provided, perform a search by name
			else if (borrowerName != null) {
				// searchBorrowers
			}

			// otherwise, read everything
			else {
				List<Borrower> borrowers = borrDAO.readAllBorrowers();
//				for (Borrower a : borrowers) {
//					a.setBooks(bDAO.readAllBooksByBorrower(a.getBorrowerId()));
//				}
				return borrowers;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
		return null;

	}

	/**
	 * For Author objects. The method provides read operations for passed object of
	 * this type. A ready by primary key, read by name, or read everything operation
	 * can be performed depending on which method parameters are null
	 * 
	 * @param pk         primary key for this object type
	 * @param authorName name for this object type
	 * @return a list of this object type; null on failure
	 * @throws SQLException
	 */
	public List<Author> readAuthors(Integer pk, String authorName) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO(s)
			conn = connUtil.getConnection();
			AuthorDAO aDAO = new AuthorDAO(conn);
			BookDAO bDAO = new BookDAO(conn);

			// perform read operation depending on which object variables are set
			// if primary key is provided, perform a read by primary key
			if (pk != null) {
				System.out.println("GET AUTHOR BY PRIMARY KEY");
				List<Author> authors = aDAO.readAuthorById(pk);
				return authors;
			}

			// if a name is provided, perform a search by name
			else if (authorName != null) {
				List<Author> authors = aDAO.readAuthorByName(authorName);
				return authors;
			}

			// otherwise, read everything
			else {
				List<Author> authors = aDAO.readAllAuthors();
				for (Author a : authors) {
					a.setBooks(bDAO.readAllBooksByAuthor(a.getAuthorId()));
				}
				return authors;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
		return null;

	}

	/**
	 * For Genre objects. The method provides read operations for passed object of
	 * this type. A ready by primary key, read by name, or read everything operation
	 * can be performed depending on which method parameters are null
	 * 
	 * @param pk        primary key for this object type
	 * @param genreName name for this object type
	 * @return a list of this object type; null on failure
	 * @throws SQLException
	 */
	public List<Genre> readGenres(Integer pk, String genreName) throws SQLException {
		Connection conn = null; // database connection

		try {
			// get a new database connection and pass it to a DAO(s)
			conn = connUtil.getConnection();
			GenreDAO gDAO = new GenreDAO(conn);

			// perform read operation depending on which object variables are set
			// if primary key is provided, perform a read by primary key
			if (pk != null) {
				System.out.println("GET Genre BY PRIMARY KEY");
				List<Genre> genres = gDAO.readAllGenres();
				return genres;
			}

			// if a name is provided, perform a search by name
			else if (genreName != null) {
				// searchAuthors
			}

			// otherwise, read everything
			else {
				List<Genre> genres = gDAO.readAllGenres();
				return genres;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// close database connection
			if (conn != null) {
				conn.close();
			}
		}
		return null;

	}
}
