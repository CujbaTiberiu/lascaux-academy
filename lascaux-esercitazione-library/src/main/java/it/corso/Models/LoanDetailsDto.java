package it.corso.Models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoanDetailsDto {
	
	private long id;
	
	private BookForLoansDto book;
	
	private LocalDate endDate;

}
