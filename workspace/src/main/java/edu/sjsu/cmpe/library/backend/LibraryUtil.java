package edu.sjsu.cmpe.library.backend;

import java.util.HashMap;
import java.util.Map;

import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;

public class LibraryUtil {

	public static Map<Long, BookDto> booksMap = new HashMap<Long, BookDto>();

	private static Long isbnNumber = 0L;
	private static Long reviewNumber = 0L;
	private static Long authorNumber = 0L;


	public static void populateBook(BookDto bookDto) throws Exception {

		if (booksMap.get(bookDto.getBook().getIsbn()) == null) {
			System.out.println("ISBN Number:: " + bookDto.getBook().getIsbn());

			booksMap.put(bookDto.getBook().getIsbn(), bookDto);
			System.out.println("MAP Number:: " + booksMap);

		} else {
			System.out.println(" ISBN Number for book was "
					+ bookDto.getBook().getIsbn());
			throw new Exception("Book already exist.");
		}

	}

	/**
	 * 
	 * @param bookDto
	 * @param newLinkDTO
	 * @throws Exception
	 */
	public static void populateReviewForIsbn(BookDto bookDto, LinkDto newLinkDTO)
			throws Exception {
		if (booksMap.get(bookDto.getBook().getIsbn()) != null) {
			bookDto.getLinks().add(newLinkDTO);
		}
	}

	public BookDto getBookFromMap(Long isbn) {
		System.out.println("isbn=" + isbn);
		System.out.println(booksMap);
		return booksMap.get(isbn);
	}

	public static Long generateISBN() {
		return ++isbnNumber;
	}
	
	public static Long generateReviewId() {
		return ++reviewNumber;
	}
	
	public static Long generateauthorId() {
		return ++authorNumber;
	}

	public static void deleteBook(Long isbn) {
		booksMap.remove(isbn);
	}

	public static void updateBook(Long isbn, BookDto booksDTO) {
		booksMap.put(isbn, booksDTO);
		System.out.println("Updated Map" + booksMap);
	}

}
