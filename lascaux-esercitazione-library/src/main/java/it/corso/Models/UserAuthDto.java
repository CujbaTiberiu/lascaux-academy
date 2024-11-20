package it.corso.Models;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import it.corso.Entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAuthDto {
	
	private long id;
		
	private String name;
	
	private String surname;
	
	private String username;
	
	private String token;

	private Date tokenExpireDate;
	
	private String role;
	
}