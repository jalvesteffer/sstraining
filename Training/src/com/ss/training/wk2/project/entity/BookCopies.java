/**
 * 
 */
package com.ss.training.wk2.project.entity;

/**
 * @author jalveste
 *
 */
public class BookCopies {

	private Book book;
	private Branch branch;
	private Integer noOfCopies;
	
	/**
	 * @return the noOfCopies
	 */
	public Integer getNoOfCopies() {
		return noOfCopies;
	}
	/**
	 * @param noOfCopies the noOfCopies to set
	 */
	public void setNoOfCopies(Integer noOfCopies) {
		this.noOfCopies = noOfCopies;
	}
	/**
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}
	/**
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}
	/**
	 * @return the branch
	 */
	public Branch getBranch() {
		return branch;
	}
	/**
	 * @param branch the branch to set
	 */
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	
}
