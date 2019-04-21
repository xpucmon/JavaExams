package org.softuni.ebankdemoproject.repository;

import org.softuni.ebankdemoproject.domain.entities.bankaccounts.BankAccount;
import org.softuni.ebankdemoproject.domain.entities.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findAllByBankAccountOrderByTransactionDateTimeDesc(BankAccount bankAccount);

    Optional<Transaction> findTransactionById(String id);

    List<Transaction> findAllByOrderByTransactionDateTimeDesc();

}
