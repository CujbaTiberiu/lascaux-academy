package it.corso.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.corso.Entities.Book;
import it.corso.Entities.Loan;
import it.corso.Entities.User;
import it.corso.Models.BookDto;
import it.corso.Models.LoanDto;
import it.corso.Models.UserDto;
import it.corso.Repositories.Book_repo;
import it.corso.Repositories.Loan_repo;
import it.corso.Repositories.User_repo;
import it.corso.Services.mappers.BookMapper;
import it.corso.Services.mappers.LoanMapper;
import it.corso.Services.mappers.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class Loan_service {

	@Autowired
	private Loan_repo Lr;

	@Autowired
	private User_repo Ur;

	@Autowired
	private Book_repo Br;

	@Autowired
	private UserMapper um;

	@Autowired
	private BookMapper bm;

	@Autowired
	private LoanMapper lm;;

	@Transactional
	public LoanDto insertLoan(Long userId, Long bookId) throws Exception {
		System.out.println(userId + " "+ bookId);
		UserDto userDto = Ur.findById(userId).map(um::toUserDto)
				.orElseThrow(() -> new EntityNotFoundException("User non esite in db!"));

		BookDto bookDto = Br.findById(bookId).map(bm::bookToBookDTO)
				.orElseThrow(() -> new EntityNotFoundException("Book non esiste in db!"));

		User user = um.toUser(userDto);
		Book book = bm.bookDtoToBook(bookDto);
		Random rand = new Random();

		Loan loan = new Loan(user, book, LocalDate.now(), LocalDate.now().plusDays(rand.nextLong(1, 30)));
		
		Lr.save(loan);

		//meglio usare mapper
		LoanDto loanDto = new LoanDto(bookId, userDto.getName(), bookDto.getTitle(),LocalDate.now(),
				LocalDate.now().plusDays(rand.nextLong(1, 30)));

		return loanDto;

	}

}
