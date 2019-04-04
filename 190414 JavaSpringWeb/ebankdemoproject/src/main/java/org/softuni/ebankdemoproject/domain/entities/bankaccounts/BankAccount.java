package org.softuni.ebankdemoproject.domain.entities.bankaccounts;

import org.iban4j.Iban;
import org.softuni.ebankdemoproject.domain.entities.BaseEntity;
import org.softuni.ebankdemoproject.domain.entities.cards.Card;
import org.softuni.ebankdemoproject.domain.entities.transactions.Transaction;
import org.softuni.ebankdemoproject.domain.entities.users.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "bank_accounts")
public class BankAccount extends BaseEntity {
    private Iban iban;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private User accountOwner;
    private LocalDateTime dateOpened;
    private BigDecimal balance;
    private BigDecimal interestRate;
    private Set<Card> cards;
    private Set<Transaction> transactions;

//    Iban iban = new Iban.Builder()
//                .countryCode(CountryCode.AT)
//                .bankCode("19043")
//                .accountNumber("00234573201")
//                .build();


    public BankAccount() {
        this.cards = new HashSet<>();
        this.transactions = new HashSet<>();
    }

    @Column(nullable = false, unique = true, updatable = false)
    public Iban getIban() {
        return iban;
    }

    public void setIban(Iban iban) {
        this.iban = iban;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "account_type")
    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "account_status")
    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "account_owner", referencedColumnName = "id", nullable = false)
    public User getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(User accountOwner) {
        this.accountOwner = accountOwner;
    }

    @Column(name = "date_opened", nullable = false)
    public LocalDateTime getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(LocalDateTime dateOpened) {
        this.dateOpened = dateOpened;
    }

    @Column(nullable = false)
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Column(name = "interest_rate")
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    @OneToMany(targetEntity = Card.class)
    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    @OneToMany(targetEntity = Transaction.class, mappedBy = "bankAccount")
    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}


