package edu.sjsu.cmpe.library.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Book implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 12121212243434L;
	private long isbn;
	@NotEmpty
	@NotNull
    @JsonProperty("title")
    private String title;
	@NotEmpty
	@JsonProperty("publication-date")
    private String publicationDate;
	
	@JsonProperty("language")
    private String language;
	
	@JsonProperty("num-pages")
    private int numPages;
    
	@JsonProperty("status")
	private String status;
    
	@JsonProperty("reviews")
	private List<Review> reviews;
    
	@JsonProperty("authors" )
	private  List<Author> authors;
    
    
    
    // add more fields here

    public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getNumPages() {
		return numPages;
	}

	public void setNumPages(int numPages) {
		this.numPages = numPages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Author> getAuthors() {
		if(this.authors == null)
			this.authors = new ArrayList<Author>();
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public List<Review> getReviews() {
		if(this.reviews == null)
			this.reviews = new ArrayList<Review>();
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}	

	/**
     * @return the isbn
     */
    public long getIsbn() {
	return isbn;
    }

    /**
     * @param isbn
     *            the isbn to set
     */
    public void setIsbn(long isbn) {
	this.isbn = isbn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }
}
