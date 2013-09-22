package edu.sjsu.cmpe.library.domain;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.sjsu.cmpe.library.dto.LinksDto;

public class Book {
	
	private long isbn;
	@NotNull
    @JsonProperty
    private String title;
    private String publicationDate;
    private String language;
    private int numPages;
    private String status;
    private String[] review;
    private LinksDto author;
    
    
    
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

	public String[] getReview() {
		return review;
	}

	public void setReview(String[] review) {
		this.review = review;
	}

	public LinksDto getAuthor() {
		return author;
	}

	public void setAuthor(LinksDto author) {
		this.author = author;
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
