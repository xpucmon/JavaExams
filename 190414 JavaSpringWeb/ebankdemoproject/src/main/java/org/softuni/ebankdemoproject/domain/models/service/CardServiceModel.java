package org.softuni.ebankdemoproject.domain.models.service;

import org.softuni.ebankdemoproject.domain.entities.bankaccounts.BankAccount;
import org.softuni.ebankdemoproject.domain.entities.cards.CardBrand;
import org.softuni.ebankdemoproject.domain.entities.cards.CardStatus;
import org.softuni.ebankdemoproject.domain.entities.cards.CardType;
import org.softuni.ebankdemoproject.domain.entities.users.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CardServiceModel extends BaseServiceModel {
    private String cardNumber;
    private User cardHolder;
    private CardType cardType;
    private CardStatus cardStatus;
    private CardBrand cardBrand;
    private LocalDateTime issueDate;
    private LocalDateTime expirationDate;
    private BankAccount bankAccount;
    private BigDecimal creditAmountMax;
    private BigDecimal creditAmountCurrent;
    private BigDecimal creditAmountDue;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public User getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(User cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CardBrand getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(CardBrand cardBrand) {
        this.cardBrand = cardBrand;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public BigDecimal getCreditAmountMax() {
        return creditAmountMax;
    }

    public void setCreditAmountMax(BigDecimal creditAmountMax) {
        this.creditAmountMax = creditAmountMax;
    }

    public BigDecimal getCreditAmountCurrent() {
        return creditAmountCurrent;
    }

    public void setCreditAmountCurrent(BigDecimal creditAmountCurrent) {
        this.creditAmountCurrent = creditAmountCurrent;
    }

    public BigDecimal getCreditAmountDue() {
        return creditAmountDue;
    }

    public void setCreditAmountDue(BigDecimal creditAmountDue) {
        this.creditAmountDue = creditAmountDue;
    }
}
