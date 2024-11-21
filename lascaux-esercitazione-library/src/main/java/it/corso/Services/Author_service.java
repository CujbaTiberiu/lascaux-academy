package it.corso.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.corso.Entities.Author;
import it.corso.Models.AuthorDto;
import it.corso.Models.AuthorForBookDto;
import it.corso.Models.BookForAuthorDto;
import it.corso.Models.ReviewForUserDto;
import it.corso.Repositories.Author_repo;
import it.corso.Services.mappers.AuthorMapper;
import it.corso.Services.mappers.BookMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class Author_service {

	@Autowired
	private Author_repo Ar;

	@Autowired
	private AuthorMapper Am;

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public AuthorForBookDto insertAuthor(AuthorForBookDto authorDto) throws Exception {
		Author author = Am.toAuthorFromAuthorForBookDto(authorDto);
		Author savedAuthor = Ar.save(author);
		return Am.toAuthorForBookDto(savedAuthor);
	}

	public AuthorDto getAuthorDetails(Long authorId) throws Exception {
		AuthorDto authorDto = Ar.findById(authorId).map(Am::toAuthorDto)
				.orElseThrow(() -> new EntityExistsException("Autore non esiste in db!"));

		List<BookForAuthorDto> bDto = authorDto.getBooks();
		List<Long> listVotes = new ArrayList<>();

		if(bDto.size() >= 1 && listVotes.size() >= 1) {
			int total = 0;

			for (BookForAuthorDto book : bDto) {
				for (ReviewForUserDto review : book.getReviews()) {
					listVotes.add(review.getVote());
				}
			}
			for (Long vote : listVotes) {
				total += vote;
			}

			int avg = total / listVotes.size();

			authorDto.setVoteAvg(avg);
		}
		
		return authorDto;
	}
	
	public List<AuthorDto> getAuthorsWithBooksInMultipleCategories() throws Exception {
	    String jpql = "SELECT a " +
	                   "FROM Author a " +
	                   "JOIN a.books b " +
	                   "GROUP BY a " +
	                   "HAVING COUNT(DISTINCT b.categories) > 1";

	    TypedQuery<Author> query = entityManager.createQuery(jpql, Author.class);
	    List<Author> authors = query.getResultList();
	    List<AuthorDto> authorsDto = authors.stream().map(Am::toAuthorDto).toList();
	    return authorsDto;
	}

}
