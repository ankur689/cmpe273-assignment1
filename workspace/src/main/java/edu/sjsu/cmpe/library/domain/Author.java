
package edu.sjsu.cmpe.library.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Author {
	@JsonProperty("id")
	private Long id;
//	private Long rating;
	@JsonProperty("name")
	private String name;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
//	public Long getRating() {
//		return rating;
//	}
//	public void setRating(Long rating) {
//		this.rating = rating;
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
