package it.corso.Services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.corso.Entities.Review;
import it.corso.Models.ReviewDto;
import it.corso.Models.ReviewForUserDto;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

	@Mapping(target = "userName", source = "user.name")
	@Mapping(target = "bookTitle", source = "book.title")
	ReviewDto toReviewDto(Review review);

	Review toReview(ReviewDto reviewDto);
	
	ReviewForUserDto toReviewForUserDto(Review review);
}
