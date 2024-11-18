package it.corso.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.corso.Entities.Role;

public interface Role_repo extends JpaRepository<Role,Long>{
	Optional<Role> findByCode(String name); 
	boolean existsByCode(String name);
}
