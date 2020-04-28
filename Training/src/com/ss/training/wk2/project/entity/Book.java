/**
 * 
 */
package com.ss.training.wk2.project.entity;

import java.util.List;

/**
 * @author jalveste
 *
 */
public class Book {
	private Integer bookId;
	private String title;
	private List<Author> authors;
	private Integer pubId;
	// private List generes, branches, copies, etc.

	/**
	 * @return the bookId
	 */
	public Integer getBookId() {
		return bookId;
	}

	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the authors
	 */
	public List<Author> getAuthors() {
		return authors;
	}

	/**
	 * @param authors the authors to set
	 */
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	/**
	 * @return the pubId
	 */
	public Integer getPubId() {
		return pubId;
	}

	/**
	 * @param pubId the pubId to set
	 */
	public void setPubId(Integer pubId) {
		this.pubId = pubId;
	}


	

}
