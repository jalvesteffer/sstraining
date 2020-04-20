package com.smoothstack.training.wk1day3.practice;

public class Movie implements Comparable<Movie>	{
	private Integer year;
	private String name;
	private float rating;

	/**
	 * @param year
	 * @param name
	 * @param rating
	 */
	public Movie(Integer year, String name, float rating) {
		super();
		this.year = year;
		this.name = name;
		this.rating = rating;
	}

	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the rating
	 */
	public float getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(float rating) {
		this.rating = rating;
	}

	@Override
	public int compareTo(Movie o) {
		// TODO Auto-generated method stub
		
		float diff = this.rating - o.getRating();
		if 		(diff < 0)	return -1;
		else if (diff > 0)	return 1;
		else				return 0;
	}

}
