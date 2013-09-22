package edu.sjsu.cmpe.library.api.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.AuthorListDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;
import edu.sjsu.cmpe.library.dto.ReviewListDto;

@Path("/v1/books")
public class BookResource {

    public BookResource() {
	// do nothing
    }
/**
 * <Method>
 * Method type : Create Book
 * Payload : @POST 
 * Path: library/v1/books
 * @param isbn
 * @return
 */

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(name = "create-book")
   public Response createBookByIsbn(Book book) {
		System.out.println("------------" +book.getTitle());
		
   		LinksDto linkResponse=new LinksDto();
           linkResponse.addLink(new LinkDto("view-book","/books/1","GET"));   
           linkResponse.addLink(new LinkDto("update","/books/1","PUT"));
           linkResponse.addLink(new LinkDto("delete-book","/books/1" ,"DELETE"));
           linkResponse.addLink(new LinkDto("create-book","/books/1","POST"));
           Response builder = Response.ok(linkResponse,MediaType.APPLICATION_JSON).build();
           builder.status(Status.CREATED );
           return builder;
          //return linkResponse;
   }
    
    
    /**
     * <Method>
     * Method type : View Book
     * Payload : @GET
     * URI: library/v1/books/isbn
     * @param isbn
     * @return
     */
    
    @GET
    @Path("/{isbn}")

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
    @Timed(name = "view-book")
    public BookDto getBookByIsbn(@PathParam("isbn") LongParam isbn) {
	Book book = new Book();
	book.setIsbn(isbn.get());
	book.setTitle("Programming Amazon EC2");
	book.setPublicationDate("2/11/2011");
	book.setLanguage("eng");
	book.setNumPages(185);
	book.setStatus("available");
	book.setReview(new String[]{"1" , "2" , "3" });
	LinksDto dto = new LinksDto();
	dto.addLink(new LinkDto("view-author", "/books/" + isbn.get()  + "/authors/1", "GET"));
	dto.addLink(new LinkDto("view-author", "/books/" + isbn.get()  + "/authors/2", "GET"));
    book.setAuthor(dto);

	BookDto bookResponse = new BookDto(book);
	bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),
		"GET"));
	bookResponse.addLink(new LinkDto("update-book",
		"/books/" + book.getIsbn(), "PUT"));
	
	bookResponse.addLink(new LinkDto("delete-book",
			"/books/" + book.getIsbn(), "DELETE"));
	
	bookResponse.addLink(new LinkDto("create-review",
			"/books/" + book.getIsbn()+"/reviews", "POST"));
	
	if(book.getReview()!=null && book.getReview().length > 0){
	bookResponse.addLink(new LinkDto("view-all-reviews",
			"/books/" + book.getIsbn() + "/reviews", "GET"));
	
	}
	
 
	return bookResponse;
    }


    /**
     * <Method>
     * Method type : Delete Book
     * Payload : @DELETE 
     * URI: library/v1/books/isbn
     * @param isbn
     * @return
     */  

    @DELETE
    @Path("/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
        @Timed(name = "delete-book")
        public Response getRoot() {
    	LinksDto links = new LinksDto();
    	links.addLink(new LinkDto("create-book", "/books", "POST"));

    	return Response.ok(links).build();
        }
    
    

    /**
     * <Method>
     * Method type : Update Book
     * Payload : @PUT 
     * URI: library/v1/books/isbn/{status}
     * @param isbn
     * @return
     */ 
     
     @PUT
     @Path("/{isbn}/{status}")

 @Produces(MediaType.APPLICATION_JSON)
 @Consumes(MediaType.APPLICATION_JSON)
     @Timed(name = "update-book")
     public LinksDto updateBookByIsbn(@PathParam("isbn") LongParam isbn , @PathParam("status") String status) {
 	Book book = new Book();
 	book.setIsbn(isbn.get());
 	System.out.println("isbn : " + isbn.get());
 	 System.out.println("Status : " + status);
 	book.setTitle("Programming Amazon EC2");
 	book.setPublicationDate("2/11/2011");
 	book.setLanguage("en");
 	book.setNumPages(185);
 	if(status.equalsIgnoreCase("available") ){
 		book.setStatus("lost");
 	}else{
 		book.setStatus("available");
 	}
 	
 	book.setReview(new String[]{"1" , "2" , "3" });
 	LinksDto dto = new LinksDto();
 	dto.addLink(new LinkDto("view-author", "/books/" + isbn.get()  + "/authors/1", "GET"));
 	dto.addLink(new LinkDto("view-author", "/books/" + isbn.get()  + "/authors/2", "GET"));
     book.setAuthor(dto);
     
     LinksDto link = new LinksDto();
 	link.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),
 		"GET"));
 	link.addLink(new LinkDto("update-book",
 		"/books/" + book.getIsbn(), "PUT"));
 	
 	link.addLink(new LinkDto("delete-book",
 			"/books/" + book.getIsbn(), "DELETE"));
 	
 	link.addLink(new LinkDto("create-review",
 			"/books/" + book.getIsbn()+"/reviews", "POST"));
 	
 	if(book.getReview()!=null && book.getReview().length > 0){
 	link.addLink(new LinkDto("view-all-reviews",
 			"/books/" + book.getIsbn() + "/reviews", "GET"));
 	
 	}
 	
  
 	return link;
     }
     

     /**
      * <Method>
      * Method type : Create-Reviews
      * Payload : @POST 
      * URI: library/v1/books/isbn/reviews
      * @param isbn
      * @return
      */
     

	    @POST
	    @Path("/{isbn}/reviews")
	     @Produces(MediaType.APPLICATION_JSON)
	     @Consumes(MediaType.APPLICATION_JSON)
	    @Timed(name = "create-reviews")
	    public Response createReview(@PathParam("isbn") LongParam isbn, Review review) {
			System.out.println("-------review.getRating()-----" +review.getRating());
			System.out.println("-------review.getComments()-----" +review.getComments());

			
	    		LinksDto linkResponse=new LinksDto();
	            linkResponse.addLink(new LinkDto("view-review","/books/" + isbn.get()  + "/reviews/1","GET"));   
	            Response builder = Response.ok(linkResponse,MediaType.APPLICATION_JSON).build();
	            builder.status(Status.CREATED );
	            return builder;
	           //return linkResponse;
	    }
	    

	    /**
	     * <Method>
	     * Method type : View Book Reviews
	     * Payload : @GET
	     * URI: library/v1/books/isbn
	     * @param isbn
	     * @return
	     */
	    
	    @GET
	    @Path("/{isbn}/reviews/{reviewId}")

	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	    @Timed(name = "view-book review")
	    public ReviewDto getReviewById(@PathParam("isbn") LongParam isbn,@PathParam("reviewId") LongParam reviewId) {
		
	    	Review review = new Review();
	    	review.setId(reviewId.get());
	    	review.setRating(4L);
	    	review.setComments("Good book on AWS fundamentals");
	    	
		
		ReviewDto reviewDto = new ReviewDto(review);
		reviewDto.addLink(new LinkDto("view-review", "/books/"+ isbn.get() +"/reviews/"+reviewId.get(), "GET"));

		return reviewDto;
	    }

	    /**
	     * <Method>
	     * Method type : View all reviews
	     * Payload : @GET
	     * URI: library/v1/books/isbn/reviews
	     * @param isbn
	     * @return
	     */
	    
	    
	    @GET
	    @Path("/{isbn}/reviews")

	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	    @Timed(name = "view-all-review")
	    public ReviewListDto getReviewById(@PathParam("isbn") LongParam isbn) {
	    	List<Review> reviewList= new ArrayList<Review>();
	    	Review review = new Review();
	    	review.setId(1L);
	    	review.setRating(4L);
	    	review.setComments("Good book on AWS fundamentals");
	    	reviewList.add(review);
	    	
	    	review = new Review();
	    	review.setId(2L);
	    	review.setRating(5L);
	    	review.setComments("Must read if you’re new to AWS");
	    	reviewList.add(review);
	    	
	    	ReviewListDto dtos = new ReviewListDto(reviewList);

		
		return dtos;
	    }


	    /**
	     * <Method>
	     * Method type : View Book Author
	     * Payload : @GET
	     * URI: library/v1/books/isbn/authors/{authorId}
	     * @param isbn
	     * @return
	     */
	    
	    @GET
	    @Path("/{isbn}/authors/{authorId}")

	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	    @Timed(name = "view-book-author")
	    public AuthorDto getAuthorById(@PathParam("isbn") LongParam isbn,@PathParam("authorId") LongParam authorId) {
		
	    	Author author = new Author();
	    	author.setId(authorId.get());
	    	//author.setRating(4L);
	    	author.setName("Jurg Vilet");
	    	
		
		AuthorDto authorDto = new AuthorDto(author);
		authorDto.addLink(new LinkDto("view-author", "/books/"+ isbn.get() +"/author/"+authorId.get(), "GET"));

		return authorDto;
	    }
	    

	    /**
	     * <Method>
	     * Method type : View All Authors
	     * Payload : @GET
	     * URI: library/v1/books/isbn/authors
	     * @param isbn
	     * @return
	     */
    

	    
	    @GET
	    @Path("/{isbn}/authors")
	    @Produces(MediaType.APPLICATION_JSON)
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Timed(name = "view-all-authors")
	    public AuthorListDto getAuthorById(@PathParam("isbn") LongParam isbn) {
	    	List<Author> authorList= new ArrayList<Author>();
	    	Author author = new Author();
	    	author.setId(1L);
	    	//review.setRating(4L);
	    	author.setName("Jurg Vilet");
	    	authorList.add(author);
	    	
	    	author = new Author();
	    	author.setId(2L);
	    	//author.setRating(5L);
	    	author.setName("Flavia Pagenelli");
	    	authorList.add(author);
	    	
	    	AuthorListDto dtos = new AuthorListDto(authorList);

		
		return dtos;
	    }

}

