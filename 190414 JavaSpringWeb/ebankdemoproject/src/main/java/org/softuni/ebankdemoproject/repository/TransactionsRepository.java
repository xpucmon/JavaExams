package org.softuni.ebankdemoproject.repository;

import org.softuni.ebankdemoproject.domain.entities.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transaction, String> {
}
