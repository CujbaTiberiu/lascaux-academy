package it.corso.Services;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.corso.Entities.Role;
import it.corso.Entities.User;
import it.corso.Models.LoginDto;
import it.corso.Models.UserAuthDto;
import it.corso.Models.UserDto;
import it.corso.Repositories.Role_repo;
import it.corso.Repositories.User_repo;
import it.corso.Services.mappers.LoanMapper;
import it.corso.Services.mappers.ReviewMapper;
import it.corso.Services.mappers.UserMapper;
import it.corso.config.JwtUtil;
import jakarta.persistence.EntityNotFoundException;

@Service
public class Auth_service {
	
	@Value("${app.jwt-expiration-milliseconds}")
	private long jwtExpirationDate;
	
	@Autowired
	private User_repo Ur;
	
	@Autowired
	private PasswordEncoder Pe;
	
	@Autowired
	private Role_repo Rr;
	
	@Autowired
	private UserMapper Um;
	
	@Autowired
	@Lazy
	private JwtUtil Jwt;

	@Autowired
	private LoanMapper Lm;
	
	@Autowired
	private ReviewMapper Rm;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = Ur.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

		Set<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toSet());

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}

	public String signup(UserDto userDto) throws Exception {

		if (Ur.existsByUsername(userDto.getUsername())) {
			throw new EntityNotFoundException("User exist in db!");
		}

		userDto.setPassword(Pe.encode(userDto.getPassword()));

		Role userRole = Rr.findByCode("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found"));

		User newUser = Um.toUser(userDto);
		newUser.getRoles().add(userRole);
		newUser.setRegistrationDate(LocalDate.now());

		Ur.save(newUser);
		return "User registered successfully";
	}

	public UserAuthDto signin(LoginDto loginRequest) throws Exception {

		User user = Ur.findByUsername(loginRequest.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		Role role = user.getRoles().iterator().next();
		
		UserAuthDto responseDto = new UserAuthDto();

		if (Pe.matches(loginRequest.getPassword(), user.getPassword())) {

			String token = Jwt.generateToken(user.getUsername());
			responseDto.setId(user.getId());
			responseDto.setName(user.getName());
			responseDto.setSurname(user.getSurname());
			responseDto.setUsername(user.getUsername());
			responseDto.setToken(token);
			responseDto.setRole(role.getCode());
			responseDto.setTokenExpireDate(new Date(System.currentTimeMillis() + jwtExpirationDate));

			return responseDto;
		} else {
			return null;
		}
	}


}
