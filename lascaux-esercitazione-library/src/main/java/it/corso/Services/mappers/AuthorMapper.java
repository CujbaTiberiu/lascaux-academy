package it.corso.Services.mappers;

import org.mapstruct.Mapper;

import it.corso.Entities.Author;
import it.corso.Models.AuthorDto;
import it.corso.Models.AuthorForBookDto;

@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface AuthorMapper {

	Author toAuthor(AuthorDto authorDto);
	
	AuthorDto toAuthorDto(Author author);
	
	AuthorForBookDto toAuthorForBookDto(Author author);
}
