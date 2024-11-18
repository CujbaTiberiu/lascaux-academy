package it.corso.Controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.corso.Entities.Role;
import it.corso.Entities.User;
import it.corso.Models.UserAuthDto;
import it.corso.Models.UserDto;
import it.corso.Repositories.Role_repo;
import it.corso.Repositories.User_repo;
import it.corso.Services.User_service;
import it.corso.Services.mappers.LoanMapper;
import it.corso.Services.mappers.ReviewMapper;
import it.corso.Services.mappers.UserMapper;
import it.corso.config.JwtUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class Auth_controller {

	@Autowired
	private User_service Us;

	@Autowired
	private User_repo Ur;

	@Autowired
	private Role_repo Rr;

	@Autowired
	private PasswordEncoder Pe;

	@Autowired
	private UserMapper Um;

	@Autowired
	private JwtUtil Jwt;;
	
	@Autowired
	private LoanMapper Lm;
	
	@Autowired
	private ReviewMapper Rm;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody UserDto userAutDto) throws Exception {
		System.out.println(userAutDto.toString());
		
		userAutDto.setPassword(Pe.encode(userAutDto.getPassword()));

		Role userRole = Rr.findByCode("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found"));

		User newUser = Um.toUser(userAutDto);
		newUser.getRoles().add(userRole);
		newUser.setRegistrationDate(LocalDate.now());

		Us.insertNewUser(newUser);
		return ResponseEntity.ok("User registered successfully");
		
	}

	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody UserAuthDto loginRequest) throws Exception {
		
		User user = Ur.findByUsername(loginRequest.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
			Role role = user.getRoles().iterator().next();

		if (Pe.matches(loginRequest.getPassword(), user.getPassword())) {
			
			String token = Jwt.generateToken(user.getUsername());
			loginRequest.setId(user.getId());
			loginRequest.setName(user.getName());
			loginRequest.setSurname(user.getSurname());
			loginRequest.setToken(token);
			loginRequest.setRole(role.getCode());
			loginRequest.setTokenExpireDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10));
			loginRequest.setLoans(user.getLoans().stream().map(Lm::loanToLoanDto).toList());
			loginRequest.setReviews(user.getReviews().stream().map(Rm::toReviewDto).toList());
			
			return ResponseEntity.ok(loginRequest);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}
	}

}
