package it.corso.Models;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {

	private long id;

	private String title;

	private LocalDate pubblicationDate;

	private List<String> genres;

	private List<String> categories;

	private List<AuthorDto> authors;

	private List<LoanDto> loans;

	private List<ReviewForUserDto> reviews;
}
