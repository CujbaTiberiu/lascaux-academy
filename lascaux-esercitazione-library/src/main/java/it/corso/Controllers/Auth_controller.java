package it.corso.Controllers;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import it.corso.Services.Auth_service;
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
	private Auth_service As;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody UserDto userAutDto) {

		try {
			return ResponseEntity.ok(As.signup(userAutDto));
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody UserAuthDto loginRequest) throws Exception {
		try {
			return ResponseEntity.ok(As.signin(loginRequest));
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

}
