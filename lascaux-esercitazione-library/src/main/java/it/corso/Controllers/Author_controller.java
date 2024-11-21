package it.corso.Controllers;

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

import it.corso.Models.AuthorDto;
import it.corso.Models.AuthorForBookDto;
import it.corso.Services.Author_service;

@RestController
@RequestMapping("/writer")
//@CrossOrigin(origins = "*", maxAge = 3600)
public class Author_controller {

	@Autowired
	private Author_service As;

	@PostMapping("/add")
	public ResponseEntity<?> addAuthor(@RequestBody AuthorForBookDto authorDto) {
		try {
			return ResponseEntity.ok(As.insertAuthor(authorDto));
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/writerId={id}")
	public ResponseEntity<?> getAuthorWithDetailsById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(As.getAuthorDetails(id));
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/wroteInDiverseCategories")
	public ResponseEntity<?> getAuthors() {
		try {
			return  ResponseEntity.ok(As.getAuthorsWithBooksInMultipleCategories());
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
