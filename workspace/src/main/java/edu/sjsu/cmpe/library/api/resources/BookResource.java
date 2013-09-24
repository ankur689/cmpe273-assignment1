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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.backend.LibraryUtil;
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
	 * <Method> Method type : Create Book Payload : @POST Path: library/v1/books
	 * 
	 * @param isbn
	 * @return
	 */

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Timed(name = "create-book")
	public Response createBookByIsbn(Book book) {
		BookDto dto = new BookDto(book);
		Long isbn = LibraryUtil.generateISBN();
		book.setIsbn(isbn);
		
		if(book!=null && book.getAuthors() !=null ){
			for(Author author : book.getAuthors()){
		 	if(author.getId() == null){
				author.setId(LibraryUtil.generateauthorId());
			}
		}
		}
		LinksDto linkResponse = new LinksDto();

		List<LinkDto> linkDtoList = new ArrayList<LinkDto>();
		linkDtoList.add(new LinkDto("view-book", "/books/" + isbn, "GET"));
		linkDtoList.add(new LinkDto("update", "/books/" + isbn, "PUT"));
		linkDtoList.add(new LinkDto("delete-book", "/books/" + isbn, "DELETE"));
		linkDtoList.add(new LinkDto("create-book", "/books/" + isbn, "POST"));

		linkResponse.setLinks(linkDtoList);
		dto.setLinks(linkDtoList);
		try {
			LibraryUtil.populateBook(dto);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Response builder = Response
				.ok(linkResponse, MediaType.APPLICATION_JSON).status(201)
				.build();
		builder.status(Status.CREATED);

		return builder;
		// return linkResponse;
	}

	/**
	 * <Method> Method type : View Book Payload : @GET URI:
	 * library/v1/books/isbn
	 * 
	 * @param isbn
	 * @return
	 */

	@GET
	@Path("/{isbn}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Timed(name = "view-book")
	public BookDto getBookByIsbn(@PathParam("isbn") LongParam isbn) {
		LibraryUtil util = new LibraryUtil();
		BookDto bookResponse = util.getBookFromMap(isbn.get());
		getViewAllReviewLinks(bookResponse);
		return bookResponse;
	}

	/**
	 * <Method> Method type : Delete Book Payload : @DELETE URI:
	 * library/v1/books/isbn
	 * 
	 * @param isbn
	 * @return
	 */

	@DELETE
	@Path("/{isbn}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Timed(name = "delete-book")
	public Response deleteBookByIsbn(@PathParam("isbn") LongParam isbn) {

		System.out.println("isbn value from app=" + isbn.get());
		LibraryUtil.deleteBook(isbn.get());
		LinksDto links = new LinksDto();
		links.addLink(new LinkDto("create-book", "/books", "POST"));
		return Response.ok(links).build();

	}

	/**
	 * <Method> Method type : Update Book Payload : @PUT URI:
	 * library/v1/books/isbn/{status}
	 * 
	 * @param isbn
	 * @return
	 */

	@PUT
	@Path("/{isbn}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Timed(name = "update-book")
	public LinksDto updateBookByIsbn(@PathParam("isbn") LongParam isbn,
			@QueryParam("status") String status) {
		BookDto bookDto = this.getBookByIsbn(isbn);
		if (bookDto != null) {
			bookDto.getBook().setStatus(status);
			LibraryUtil.updateBook(isbn.get(), bookDto);
		}
		getViewAllReviewLinks(bookDto);
		LinksDto link = new LinksDto();
		link.setLinks(bookDto.getLinks());
		return link;
	}

	/**
	 * 
	 * @param bookDto
	 */
	private void getViewAllReviewLinks(BookDto bookDto) {
		 
		List<LinkDto> dto = bookDto.getLinks();
		boolean flag = true;
		if (bookDto.getBook().getReviews() != null
				&& bookDto.getBook().getReviews().size() > 0) {
			for(LinkDto linkDto : dto ){
				if(linkDto.getRel().equalsIgnoreCase("view-all-reviews")){
					flag = false;
				}
			}
			if(flag){
			 dto.add( new LinkDto("view-all-reviews", "/books/"
					+ bookDto.getBook().getIsbn() + "/reviews", "GET"));
			}
		}
		LibraryUtil.booksMap.put(bookDto.getBook().getIsbn(), bookDto);

	}

	/**
	 * <Method> Method type : Create-Reviews Payload : @POST URI:
	 * library/v1/books/isbn/reviews
	 * 
	 * @param isbn
	 * @return
	 * @throws Exception 
	 */

	@POST
	@Path("/{isbn}/reviews")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Timed(name = "create-reviews")
	public Response createReview(@PathParam("isbn") LongParam isbn,
			Review review) throws Exception {
	    LinksDto linkResponse = new LinksDto();
	    BookDto bookDto = getBookByIsbn(isbn);
	    List<Review> reviewList = bookDto.getBook().getReviews();
	    
	    Long reviewId =  LibraryUtil.generateReviewId();
	    review.setId(reviewId);
	    reviewList.add(review);
	    bookDto.getBook().setReviews(reviewList);
	    LibraryUtil.booksMap.put(bookDto.getBook().getIsbn(), bookDto);
	    linkResponse.addLink(new LinkDto("view-review", "/books/" + bookDto.getBook().getIsbn()
				+ "/reviews/" + reviewId, "GET"));
		LibraryUtil.booksMap.put( bookDto.getBook().getIsbn(), bookDto);
		Response builder = Response
				.ok(linkResponse, MediaType.APPLICATION_JSON).build();
		builder.status(Status.CREATED);
		return builder;
	}

	/**
	 * <Method> Method type : View Book Reviews Payload : @GET URI:
	 * library/v1/books/isbn
	 * 
	 * @param isbn
	 * @return
	 */

	@GET
	@Path("/{isbn}/reviews/{reviewId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Timed(name = "view-book review")
	public ReviewDto getReviewById(@PathParam("isbn") LongParam isbn,
			@PathParam("reviewId") LongParam reviewId) {
		Review reviewRet = new Review();
		for(Review review : getBookByIsbn(isbn).getBook().getReviews()){
			if(review.getId() == reviewId.get()){
				reviewRet = review;
			}
		}
		ReviewDto reviewDto= new ReviewDto(reviewRet);
		
		reviewDto.addLink(new LinkDto("view-review", "/books/" + isbn.get()
				+ "/reviews/" + reviewId.get(), "GET"));

		return reviewDto;
	}

	/**
	 * <Method> Method type : View all reviews Payload : @GET URI:
	 * library/v1/books/isbn/reviews
	 * 
	 * @param isbn
	 * @return
	 */

	@GET
	@Path("/{isbn}/reviews")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Timed(name = "view-all-review")
	public ReviewListDto getReviewById(@PathParam("isbn") LongParam isbn) {
		ReviewListDto dtos = new ReviewListDto((this.getBookByIsbn(isbn)).getBook().getReviews());
		return dtos;
	}

	/**
	 * <Method> Method type : View Book Author Payload : @GET URI:
	 * library/v1/books/isbn/authors/{authorId}
	 * 
	 * @param isbn
	 * @return
	 */

	@GET
	@Path("/{isbn}/authors/{authorId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Timed(name = "view-book-author")
	public AuthorDto getAuthorById(@PathParam("isbn") LongParam isbn,
			@PathParam("authorId") LongParam authorId) {
		Author authReturn = new Author();
		List<Author> authorList = this.getBookByIsbn(isbn).getBook().getAuthors();
		if(authorList!=null){
			for(Author author : authorList){
				if(author.getId()== authorId.get()){
					authReturn = author;
				}
			}
		}

		AuthorDto authorDto = new AuthorDto(authReturn);
		authorDto.addLink(new LinkDto("view-author", "/books/" + isbn.get()
				+ "/author/" + authorId.get(), "GET"));

		return authorDto;
	}

	/**
	 * <Method> Method type : View All Authors Payload : @GET URI:
	 * library/v1/books/isbn/authors
	 * 
	 * @param isbn
	 * @return
	 */

	@GET
	@Path("/{isbn}/authors")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Timed(name = "view-all-authors")
	public AuthorListDto getAuthorById(@PathParam("isbn") LongParam isbn) {
		List<Author> authorList = this.getBookByIsbn(isbn).getBook().getAuthors();
		AuthorListDto dtos = new AuthorListDto(authorList);
		return dtos;
	}

}