package it.corso.Services;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.corso.Entities.Author;
import it.corso.Entities.Book;
import it.corso.Entities.Category;
import it.corso.Models.AuthorDto;
import it.corso.Models.BookDto;
import it.corso.Models.BookForCategoryDto;
import it.corso.Models.BooksPerCategoryDto;
import it.corso.Models.ReviewForUserDto;
import it.corso.Repositories.Author_repo;
import it.corso.Repositories.Book_repo;
import it.corso.Services.mappers.AuthorMapper;
import it.corso.Services.mappers.BookMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class Book_service {

	@Autowired
	private Book_repo Br;

	@Autowired
	private BookMapper Bm;

	@Autowired
	private Author_repo Ar;

	@Autowired
	private AuthorMapper Am;

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public BookDto insertBook(BookDto bookDto) throws Exception {
		Book book = Bm.bookDtoToBook(bookDto);

		List<Author> listAuthors = Ar.findAll();
		List<Author> existingAuthorsToAssociate = new ArrayList<Author>();

		Set<Author> authorsSet = new HashSet<>(listAuthors);

		for (AuthorDto authorDto : bookDto.getAuthors()) {
			Author author = Am.toAuthor(authorDto);

			boolean authorExists = authorsSet.stream()
					.anyMatch(existingAuthor -> existingAuthor.getBirthDate().equals(author.getBirthDate())
							&& existingAuthor.getName().equalsIgnoreCase(author.getName())
							&& existingAuthor.getSurname().equalsIgnoreCase(author.getSurname()));
			if (authorExists) {
				authorsSet.stream()
						.filter(existingAuthor -> existingAuthor.getBirthDate().equals(author.getBirthDate())
								&& existingAuthor.getName().equalsIgnoreCase(author.getName())
								&& existingAuthor.getSurname().equalsIgnoreCase(author.getSurname()))
						.findFirst().ifPresent(existingAuthorsToAssociate::add);
			} else {
				existingAuthorsToAssociate.add(author);
				Ar.save(author);
			}

		}

//		for(Book exisitngBook : Br.findAll()) {
//			boolean bookExists = 
//		}

		book.setAuthors(existingAuthorsToAssociate);
		Book savedBook = Br.save(book);
		return Bm.bookToBookDTO(savedBook);
	}

	public List<BookForCategoryDto> getAllBooksWithAuthorsAndVoteByCategory(String category) throws Exception {
		Category cat = Category.valueOf(category.toUpperCase());

		if (cat == null) {
			throw new Exception("Nessun libro nel db con categoria: " + category);
		}

		List<Book> books = Br.findAll().stream().filter(b -> b.getCategories().contains(cat)).toList();

		List<BookForCategoryDto> booksDto = books.stream().map(Bm::toBookForCategoryDto).toList();

		for (BookForCategoryDto book : booksDto) {
			List<ReviewForUserDto> reviews = book.getReviews();
			int reviewAvg = 0;
			for (ReviewForUserDto review : reviews) {
				reviewAvg += review.getVote();
			}
			book.setVoteAvg(reviewAvg);
		}

		return booksDto;
	}

	public List<BooksPerCategoryDto> getBookByCategoryLoanedLastYear() throws Exception {
		LocalDate oneYearAgo = LocalDate.now().minusYears(1);
		// "SELECT b, COUNT(l) AS loanCount " +
		String jpql = "SELECT b, COUNT(l) AS loanCount " + "FROM Book b " + "JOIN b.loans l "
				+ "WHERE l.startDate >= :oneYearAgo " + "GROUP BY b";
		// "ORDER BY b.categories";
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		query.setParameter("oneYearAgo", oneYearAgo);

		List<Object[]> objs = query.getResultList();

		List<BooksPerCategoryDto> booksDtos = new ArrayList<>();
		long id = 0;

		for (Object[] obj : objs) {
			BooksPerCategoryDto book = new BooksPerCategoryDto();

			book.setId(id++);
			List<Book> b = (List<Book>) obj[0];
			int loanCount = ((Number) obj[1]).intValue();
			book.setBooks(b.stream().map(Bm::bookToBookDTO).toList());
			book.setNumberOfLoans(loanCount);
			booksDtos.add(book);
		}

		return booksDtos;
	}

	public List<BookForCategoryDto> getAllBooks() {
		List<Book> books = Br.findAll();
		List<BookForCategoryDto> booksDto = books.stream().map(Bm::toBookForCategoryDto).toList();
		for (BookForCategoryDto book : booksDto) {
			List<ReviewForUserDto> reviews = book.getReviews();
			int reviewAvg = 0;
			for (ReviewForUserDto review : reviews) {
				reviewAvg += review.getVote();
			}
			book.setVoteAvg(reviewAvg);
		}

		return booksDto;
	}

}
