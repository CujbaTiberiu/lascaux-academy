package it.corso.Models;

import java.time.LocalDate;
import java.util.List;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorForBookDto {
	private long id;

	@NonNull
	private String name;

	@NonNull
	private String surname;

	@NonNull
	private LocalDate birthDate;
}
