package org.softuni.ebankdemoproject.repository;

import org.softuni.ebankdemoproject.domain.entities.bankaccounts.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountsRepository extends JpaRepository<BankAccount, String> {
}
