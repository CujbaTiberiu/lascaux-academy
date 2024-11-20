package it.corso.Controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.corso.Models.UserDto;
import it.corso.Services.User_service;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "*", maxAge = 3600)
public class User_controller {

	@Autowired
	private User_service Us;

	@PostMapping("/add")
	public ResponseEntity<UserDto> addNewUser(@RequestBody UserDto userDto) {
		return ResponseEntity.ok(Us.insertUser(userDto));
	}

	@GetMapping("/id={id}")
	public ResponseEntity<?> getUserWitDetails(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(Us.getLoansWithBooksAndReviewsByUserId(id));
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/startDate={startDate}/endDate={endDate}/numberOfLoans={numberofLoans}")
	public ResponseEntity<?> getUserByNumberOfLoan(@PathVariable LocalDate startDate,@PathVariable LocalDate endDate, @PathVariable int numberofLoans) {
		try {
			return ResponseEntity.ok(Us.getUserLoanedNumberOfBookInTimeline(startDate, endDate, numberofLoans));
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping("/getRatersWhere/bookId={bookId}")
	public ResponseEntity<?> getTopThreeRatersByBook(@PathVariable Long bookId){
		try {
			return ResponseEntity.ok(Us.getTopRatersByBook(bookId));
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
