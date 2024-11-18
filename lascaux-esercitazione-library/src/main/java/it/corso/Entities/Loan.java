package it.corso.Entities;

import java.time.LocalDate;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books_loaned")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	@NonNull
	@Column(name = "start_date")
	private LocalDate startDate;

	@NonNull
	@Column(name = "end_date")
	private LocalDate endDate;

	public Loan(User user, Book book, LocalDate startDate, LocalDate endDate) {
		super();
		this.user = user;
		this.book = book;
		this.startDate = startDate;
		this.endDate = endDate;
	}

}
