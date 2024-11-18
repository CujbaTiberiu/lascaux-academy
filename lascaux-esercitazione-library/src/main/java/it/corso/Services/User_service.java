package it.corso.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import it.corso.Entities.Role;
import it.corso.Entities.User;
import it.corso.Models.UserAuthDto;
import it.corso.Models.UserDto;
import it.corso.Repositories.Role_repo;
import it.corso.Repositories.User_repo;
import it.corso.Services.mappers.LoanMapper;
import it.corso.Services.mappers.ReviewMapper;
import it.corso.Services.mappers.UserMapper;
import it.corso.config.JwtUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class User_service {

	@Autowired
	private User_repo Ur;

	@Autowired
	private UserMapper Um;

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public UserDto insertUser(UserDto ud) {
		User user = Um.toUser(ud);
		User savedUser = Ur.save(user);
		return Um.toUserDto(savedUser);
	}

	@Transactional
	public UserDto insertNewUser(User user) {
		User savedUser = Ur.save(user);
		return Um.toUserDto(savedUser);
	}

	public UserDto getLoansWithBooksAndReviewsByUserId(Long userId) throws Exception {
		UserDto userDto = Ur.findById(userId).map(Um::toUserDto)
				.orElseThrow(() -> new EntityNotFoundException("User non esite in db!"));

		// User user = um.toUser(userDto);

		return userDto;

	}

	public List<UserDto> getUserLoanedNumberOfBookInTimeline(LocalDate startDate, LocalDate endDate, int numberOfLoans)
			throws Exception {
		String jpql = "SELECT u FROM User u " + "JOIN u.loans l " + "WHERE l.startDate between :startDate and :endDate "
				+ "GROUP BY u " + "HAVING COUNT(l) > :numberOfLoans";
		TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("numberOfLoans", numberOfLoans);

		List<User> users = query.getResultList();
		List<UserDto> usersDto = users.stream().map(Um::toUserDto).toList();

		return usersDto;
	}

	public List<UserDto> getTopRatersByBook(Long bookId) throws Exception {
		String jpql = "Select u From User u " + "Join u.reviews r " + "Join r.book b " + "Where b.id = :bookId "
				+ "Group By u " + "Order By Max(r.vote) Desc";
		TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
		query.setParameter("bookId", bookId);
		query.setMaxResults(3);

		List<User> users = query.getResultList();
		List<UserDto> usersDto = users.stream().map(Um::toUserDto).toList();

		return usersDto;
	}

}
