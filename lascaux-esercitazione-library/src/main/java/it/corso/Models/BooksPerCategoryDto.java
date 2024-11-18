package it.corso.Models;

import java.util.List;
import java.util.Locale.Category;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BooksPerCategoryDto {
	
	private long id;

	private Category category;
	
	private List<BookDto> books;
	
	private int numberOfLoans;
}
