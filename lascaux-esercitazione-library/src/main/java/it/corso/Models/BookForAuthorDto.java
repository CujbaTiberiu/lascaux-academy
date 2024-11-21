package it.corso.Models;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookForAuthorDto {
	private long id;

	private String title;

	private LocalDate publicationDate;
	
	private List<String> genres;

	private List<String> categories;
	
	private List<ReviewForUserDto> reviews;
	
}
