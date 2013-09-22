

package edu.sjsu.cmpe.library.dto;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.cmpe.library.domain.Author;

//@JsonPropertyOrder(alphabetic = true)
public class AuthorListDto extends LinksDto  {
	
	private List<Author> authors =  new ArrayList<Author>();

	
	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}


	public AuthorListDto( List<Author>  author){
		super();
		this.authors = author;
	}


	
	
}
