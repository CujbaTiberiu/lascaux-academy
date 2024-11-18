package it.corso.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import it.corso.Entities.User;
import it.corso.Models.UserDto;
import it.corso.Repositories.User_repo;
import it.corso.Services.mappers.UserMapper;
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
	private UserMapper um;

	@PersistenceContext
	private EntityManager entityManager;

//	public User_service(UserMapper um) {
//		super();
//		this.um = um;
//	}
//
//	@Transactional
//	public UserDto insertUser(UserDto ud) {
//		User user = new User();
//		user.setName(ud.getName());
//		user.setSurname(ud.getSurname());
//		user.setRegistrationDate(ud.getRegistrationDate());
//		Ur.save(user);
//		return ud;
//
//	}
//
	@Transactional
	public UserDto insertUser(UserDto ud) {
		User user = um.toUser(ud);
		User savedUser = Ur.save(user);
		return um.toUserDto(savedUser);
	}
	
	@Transactional
	public UserDto insertNewUser(User user) {
		User savedUser = Ur.save(user);
		return um.toUserDto(savedUser);
	}

	public UserDto getLoansWithBooksAndReviewsByUserId(Long userId) throws Exception {
		UserDto userDto = Ur.findById(userId).map(um::toUserDto)
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
		List<UserDto> usersDto = users.stream().map(um::toUserDto).toList();

		return usersDto;
	}

	public List<UserDto> getTopRatersByBook(Long bookId) throws Exception {
		String jpql = "Select u From User u " + "Join u.reviews r " + "Join r.book b " + "Where b.id = :bookId "
				+ "Group By u " + "Order By Max(r.vote) Desc";
		TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
		query.setParameter("bookId", bookId);
		query.setMaxResults(3);

		List<User> users = query.getResultList();
		List<UserDto> usersDto = users.stream().map(um::toUserDto).toList();

		return usersDto;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = Ur.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

		Set<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toSet());

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}

}
