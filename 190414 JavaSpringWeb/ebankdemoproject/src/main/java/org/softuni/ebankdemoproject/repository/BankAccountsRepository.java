package org.softuni.ebankdemoproject.repository;

import org.softuni.ebankdemoproject.domain.entities.bankaccounts.BankAccount;
import org.softuni.ebankdemoproject.domain.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccountsRepository extends JpaRepository<BankAccount, String> {
    Optional<BankAccount> findBankAccountByIban(String iban);

    List<BankAccount> findAllByAccountOwner(User user);
}
