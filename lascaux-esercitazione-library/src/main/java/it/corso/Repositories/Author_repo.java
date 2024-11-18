package it.corso.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.corso.Entities.Author;

public interface Author_repo extends JpaRepository<Author, Long> {

}
