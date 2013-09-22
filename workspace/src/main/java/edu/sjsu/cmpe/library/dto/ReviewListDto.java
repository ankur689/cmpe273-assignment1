package edu.sjsu.cmpe.library.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Review;

@JsonPropertyOrder(value ={"reviews, links"})
public class ReviewListDto extends LinksDto  {
	
	private List<Review> reviews =  new ArrayList<Review>();

	
	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}


	public ReviewListDto( List<Review>  review){
		super();
		this.reviews = review;
	}


	
	
}
