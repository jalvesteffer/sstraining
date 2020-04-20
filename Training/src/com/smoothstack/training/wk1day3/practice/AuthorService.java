package com.smoothstack.training.wk1day3.practice;

import java.util.List;

public class AuthorService {

	public String addAuthor(Author author) {
		if (author == null) {
			return "Author Name Cannot be Null";
		} else if (author.getAuthorName() != null && author.getAuthorName().length() > 3) {
			return "Success";
		}

		return null;

	}

	public void updateAuthor() {

	}

	public void deleteAuthor() {

	}

	public List<Author> readAuthor() {

		return null;

	}

}
