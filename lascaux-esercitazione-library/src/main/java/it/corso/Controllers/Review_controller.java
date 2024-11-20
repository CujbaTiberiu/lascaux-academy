package it.corso.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.corso.Models.ReviewDto;
import it.corso.Services.Review_service;

@RestController
@RequestMapping("/review")
//@CrossOrigin(origins = "*", maxAge = 3600)
public class Review_controller {

	@Autowired
	private Review_service Rs;

	@PostMapping("/userId={userId}/bookId={bookId}")
	public ResponseEntity<?> addReview(@RequestBody ReviewDto reviewDto, @PathVariable Long userId,
			@PathVariable Long bookId) {

		try {
			return ResponseEntity.ok(Rs.insertReview(reviewDto, userId, bookId));
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
