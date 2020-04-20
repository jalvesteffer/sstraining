package com.smoothstack.training.wk1.project;

/**
 * This class represents a Author object. It has an ID and name. The class
 * extends an IDObject.
 * 
 * @author jalveste
 *
 */
public class Author extends IDObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1722788522363072931L;

	/**
	 * @param id
	 * @param name
	 */
	public Author(Integer id, String name) {
		super(id, name);
	}

}
