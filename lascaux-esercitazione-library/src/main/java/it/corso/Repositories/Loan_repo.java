package it.corso.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.corso.Entities.Loan;

public interface Loan_repo extends JpaRepository<Loan, Long> {

}
