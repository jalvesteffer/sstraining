package com.smoothstack.training.wk1.project;

/**
 * This class represents a Publisher object. It has an ID and name, as well as
 * an address String. The class extends an IDObject.
 * 
 * @author jalveste
 *
 */
public class Publisher extends IDObject {

	private static final long serialVersionUID = -2080780561441209752L;
	private String publisherAddress; // city and country of publisher

	/**
	 * @param id
	 * @param name
	 * @param publisherAddress
	 */
	public Publisher(Integer id, String name, String publisherAddress) {
		super(id, name);
		this.publisherAddress = publisherAddress;
	}

	/**
	 * @return the publisherAddress
	 */
	public String getPublisherAddress() {
		return publisherAddress;
	}

	/**
	 * @param publisherAddress the publisherAddress to set
	 */
	public void setPublisherAddress(String publisherAddress) {
		this.publisherAddress = publisherAddress;
	}

}
