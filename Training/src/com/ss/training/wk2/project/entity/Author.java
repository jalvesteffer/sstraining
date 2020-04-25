/**
 * 
 */
package com.ss.training.wk2.project.entity;

import java.util.List;

/**
 * @author jalveste
 *
 */
public class Author {
	private Integer authorId;
	private String authorName;
	private List<Book> books;
	
	/**
	 * @return the authorId
	 */
	public Integer getAuthorId() {
		return authorId;
	}
	/**
	 * @param authorId the authorId to set
	 */
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	/**
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}
	/**
	 * @param authorName the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	/**
	 * @return the bookIds
	 */
	public List<Book> getBooks() {
		return books;
	}
	/**
	 * @param bookIds the bookIds to set
	 */
	public void setBooks(List<Book> bookIds) {
		this.books = bookIds;
	}
}
