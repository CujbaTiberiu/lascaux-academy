package it.corso.Services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import it.corso.Entities.Role;
import it.corso.Entities.User;
import it.corso.Models.UserAuthDto;
import it.corso.Models.UserDto;

@Mapper(componentModel = "spring", uses = LoanMapper.class)
public interface UserMapper {

	// UserMapper instance = Mappers.getMapper(UserMapper.class);

	User toUser(UserDto userDto);

	@Mapping(target = "authorities", source = "roles")
	UserDto toUserDto(User user);
	
	User toUser(UserAuthDto userAuthDto);
	
	default GrantedAuthority toGrantedAuthority(Role role) {
        return new SimpleGrantedAuthority(role.getCode());
    }
}
