package org.softuni.ebankdemoproject.repository;

import org.softuni.ebankdemoproject.domain.entities.loans.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoansRepository extends JpaRepository<Loan, String> {
}
