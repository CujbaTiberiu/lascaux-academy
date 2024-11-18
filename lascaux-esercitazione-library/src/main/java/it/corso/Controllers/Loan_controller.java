package it.corso.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.corso.Models.LoanDto;
import it.corso.Services.Loan_service;
//@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/loan")
@CrossOrigin(origins = "*", maxAge = 3600)
public class Loan_controller {

	@Autowired
	private Loan_service Ls;
	
	@PostMapping("/userId={userId}/bookId={bookId}")
	public ResponseEntity<?> addLoan(@PathVariable Long userId, @PathVariable Long bookId){
		try {
			return ResponseEntity.ok(Ls.insertLoan(userId, bookId));
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
	
	
	
}
