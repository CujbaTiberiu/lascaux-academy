package it.corso.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.corso.Entities.Review;

public interface Review_repo extends JpaRepository<Review, Long> {

}
