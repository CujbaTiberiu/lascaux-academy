package it.corso.Models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import it.corso.Entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private long id;

	private String name;

	private String surname;
	
	private String username;
	
	private String password;

	private LocalDate registrationDate;
	
	private List<GrantedAuthority> authorities = new ArrayList<>();
	
	private List<LoanDetailsDto> loans;

}
