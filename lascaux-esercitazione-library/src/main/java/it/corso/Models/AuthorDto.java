package it.corso.Models;

import java.time.LocalDate;
import java.util.List;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthorDto {

	private long id;

	@NonNull
	private String name;

	@NonNull
	private String surname;

	@NonNull
	private LocalDate birthDate;
	
	private List<BookForAuthorDto> books;
	
	private int voteAvg;
}
