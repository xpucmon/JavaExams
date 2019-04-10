package org.softuni.ebankdemoproject.domain.models.service;

import org.softuni.ebankdemoproject.domain.entities.bankaccounts.AccountStatus;
import org.softuni.ebankdemoproject.domain.entities.bankaccounts.AccountType;
import org.softuni.ebankdemoproject.domain.entities.cards.Card;
import org.softuni.ebankdemoproject.domain.entities.transactions.Transaction;
import org.softuni.ebankdemoproject.domain.entities.users.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class BankAccountsServiceModel extends BaseServiceModel {
    private String iban;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private User accountOwner;
    private LocalDateTime dateOpened;
    private BigDecimal balance;
    private BigDecimal interestRate;
    private Set<Card> cards;
    private Set<Transaction> transactions;

    public BankAccountsServiceModel() {
        this.cards = new HashSet<>();
        this.transactions = new HashSet<>();
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public User getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(User accountOwner) {
        this.accountOwner = accountOwner;
    }

    public LocalDateTime getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(LocalDateTime dateOpened) {
        this.dateOpened = dateOpened;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}
