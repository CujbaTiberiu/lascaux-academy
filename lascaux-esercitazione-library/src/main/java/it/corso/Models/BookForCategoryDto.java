package it.corso.Models;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookForCategoryDto {

	private long id;

	private String title;

	private LocalDate publicationDate;

	private List<String> categories;
	
	private List<AuthorForBookDto> authors;
	
	private List<ReviewForUserDto> reviews;
	
	private long voteAvg;
}
