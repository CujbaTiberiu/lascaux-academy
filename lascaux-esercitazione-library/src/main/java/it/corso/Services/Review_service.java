package it.corso.Services;

import java.beans.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.corso.Entities.Book;
import it.corso.Entities.Review;
import it.corso.Entities.User;
import it.corso.Models.BookDto;
import it.corso.Models.ReviewDto;
import it.corso.Models.UserDto;
import it.corso.Repositories.Book_repo;
import it.corso.Repositories.Review_repo;
import it.corso.Repositories.User_repo;
import it.corso.Services.mappers.BookMapper;
import it.corso.Services.mappers.ReviewMapper;
import it.corso.Services.mappers.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class Review_service {

	@Autowired
	private Review_repo Rr;

	@Autowired
	private User_repo Ur;

	@Autowired
	private Book_repo Br;

	@Autowired
	private UserMapper um;

	@Autowired
	private BookMapper bm;

	@Autowired
	private ReviewMapper rm;

	@Transactional
	public ReviewDto insertReview(ReviewDto reviewDto, Long userId, Long bookId) throws Exception {
		UserDto userDto = Ur.findById(userId).map(um::toUserDto)
				.orElseThrow(() -> new EntityNotFoundException("User non esite in db!"));

		BookDto bookDto = Br.findById(bookId).map(bm::bookToBookDTO)
				.orElseThrow(() -> new EntityNotFoundException("Book non esiste in db!"));

		User user = um.toUser(userDto);
		Book book = bm.bookDtoToBook(bookDto);

		Review review = new Review(user, book, reviewDto.getVote(),reviewDto.getComment());

		Rr.save(review);

		return rm.toReviewDto(review);

	}

}
