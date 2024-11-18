package it.corso.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDto {

	private long id;
	
	private String userName;
	
	private String bookTitle;
	
	private int vote;
	
	private String comment;
}
