package it.corso.Services.mappers;

import org.mapstruct.Mapper;

import it.corso.Entities.Book;
import it.corso.Models.BookDto;
import it.corso.Models.BookForAuthorDto;
import it.corso.Models.BookForCategoryDto;
import it.corso.Models.BookForLoansDto;

@Mapper(componentModel = "spring", uses = {ReviewMapper.class})
public interface BookMapper {

	BookDto bookToBookDTO(Book book);

	Book bookDtoToBook(BookDto bookDTO);
	
	BookForLoansDto toBookForLoansDto(Book book);
	
	BookForAuthorDto toBookForAuthorDto(Book book);
	
	BookForCategoryDto toBookForCategoryDto(Book book);
	
}
