package com.smoothstack.training.wk1.project;

import java.util.ArrayList;

/**
 * This class represents a Book object. It has an ID and name, as well as a list
 * of one or more author IDs and a publisher ID. The class extends an IDObject.
 * 
 * @author jalveste
 *
 */
public class Book extends IDObject {

	private static final long serialVersionUID = 5914719989132805280L;

	private Integer publisherID; // books publisher as key
	private ArrayList<Integer> authorIDList; // books author as list of keys

	/**
	 * Overloaded class constructor
	 * 
	 * @param id           new book id
	 * @param name         book title
	 * @param publisherID  books publisher id
	 * @param authorIDList list of author ids for book
	 */
	public Book(Integer id, String name, Integer publisherID, ArrayList<Integer> authorIDList) {
		super(id, name);
		this.publisherID = publisherID;
		this.authorIDList = authorIDList;
	}

	/**
	 * @return the publisherID
	 */
	public Integer getPublisherID() {
		return publisherID;
	}

	/**
	 * @param publisherID the publisherID to set
	 */
	public void setPublisherID(Integer publisherID) {
		this.publisherID = publisherID;
	}

	/**
	 * @return the authorIDList
	 */
	public ArrayList<Integer> getAuthorIDList() {
		return authorIDList;
	}

	/**
	 * @param authorIDList the authorIDList to set
	 */
	public void setAuthorIDList(ArrayList<Integer> authorIDList) {
		this.authorIDList = authorIDList;
	}

}
