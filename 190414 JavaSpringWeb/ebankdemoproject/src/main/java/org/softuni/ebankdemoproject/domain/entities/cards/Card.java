package org.softuni.ebankdemoproject.domain.entities.cards;

import org.iban4j.Iban;
import org.softuni.ebankdemoproject.domain.entities.BaseEntity;
import org.softuni.ebankdemoproject.domain.entities.users.User;
import org.softuni.ebankdemoproject.domain.entities.loans.CreditCardLoan;
import org.softuni.ebankdemoproject.domain.entities.transactions.Transaction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "cards")
public class Card extends BaseEntity {
    private String cardNumber;
    private User cardHolder;
    private CardType cardType;
    private CardStatus cardStatus;
    private LocalDateTime issueDate;
    private LocalDateTime expirationDate;
    private Iban bankAccount;
    private CreditCardLoan creditCardLoan;
    private List<Transaction> transactions;

    public Card() {
        this.transactions = new ArrayList<>();
    }

    @Column(name = "cardNumber", nullable = false, unique = true, updatable = false)
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "card_holder", referencedColumnName = "id")
    public User getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(User cardHolder) {
        this.cardHolder = cardHolder;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "card_type")
    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "card_status")
    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    @Column(name = "issue_date", nullable = false, updatable = false)
    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    @Column(name = "expiration_date", nullable = false, unique = true, updatable = false)
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Column(name = "bank_account", nullable = false)
    public Iban getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(Iban bankAccount) {
        this.bankAccount = bankAccount;
    }

    @OneToOne(targetEntity = CreditCardLoan.class)
    @JoinColumn(name = "credit_card_loan")
    public CreditCardLoan getCreditCardLoan() {
        return creditCardLoan;
    }

    public void setCreditCardLoan(CreditCardLoan creditCardLoan) {
        this.creditCardLoan = creditCardLoan;
    }

    @OneToMany(targetEntity = Transaction.class)
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
