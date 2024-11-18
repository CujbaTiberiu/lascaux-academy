package it.corso.Models;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoanDto {

	private long id;
	
	private String userName;
	
	private String bookTitle;
	
	private LocalDate endDate;
	
	private LocalDate startDate;
	
}
