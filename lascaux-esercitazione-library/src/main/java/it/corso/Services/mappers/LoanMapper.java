package it.corso.Services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.corso.Entities.Loan;
import it.corso.Models.LoanDetailsDto;
import it.corso.Models.LoanDto;

@Mapper(componentModel = "spring")
public interface LoanMapper {
	
	
	@Mapping(target = "userName", source = "user.name")
	@Mapping(target = "bookTitle", source = "book.title")
	LoanDto loanToLoanDto(Loan loan);
	
	Loan loanDtoToLoan(LoanDto loanDto);

	LoanDetailsDto toLoanDetailsDto(Loan loan);
	
	Loan toLoan(LoanDetailsDto loansDetailsDto);
}
