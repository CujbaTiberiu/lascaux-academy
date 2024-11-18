package it.corso.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.corso.Entities.Book;
import java.util.List;
import it.corso.Entities.Category;


public interface Book_repo extends JpaRepository<Book, Long> {

	List<Book> findByCategories(List<Category> categories);
	
	@Query(value = "Select * from books where categories = :category", nativeQuery = true)
	List<Book> findBooksByCategory(@Param("category") String category);
}
