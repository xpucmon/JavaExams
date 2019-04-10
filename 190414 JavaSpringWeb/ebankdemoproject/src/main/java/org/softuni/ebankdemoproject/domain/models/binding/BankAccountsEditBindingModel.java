package org.softuni.ebankdemoproject.domain.models.binding;

import org.softuni.ebankdemoproject.domain.entities.cards.Card;
import org.softuni.ebankdemoproject.domain.entities.transactions.Transaction;
import org.softuni.ebankdemoproject.domain.models.service.BaseServiceModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class BankAccountsEditBindingModel extends BaseServiceModel {
    private String iban;
    private String accountType;
    private String accountStatus;
    private String accountOwner;
    private LocalDateTime dateOpened;
    private BigDecimal balance;
    private BigDecimal interestRate;
    private Set<Card> cards;
    private Set<Transaction> transactions;

    public BankAccountsEditBindingModel() {
        this.cards = new HashSet<>();
        this.transactions = new HashSet<>();
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(String accountOwner) {
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
