package org.softuni.ebankdemoproject.domain.entities.cards;

import org.softuni.ebankdemoproject.domain.entities.BaseEntity;
import org.softuni.ebankdemoproject.domain.entities.bankaccounts.BankAccount;
import org.softuni.ebankdemoproject.domain.entities.users.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "cards")
public class Card extends BaseEntity {
    private String cardNumber;
    private User cardHolder;
    private CardType cardType;
    private CardBrand cardBrand;
    private CardStatus cardStatus;
    private LocalDateTime issueDate;
    private LocalDateTime expirationDate;
    private BankAccount bankAccount;
    private BigDecimal creditAmountMax;
    private BigDecimal creditAmountCurrent;
    private BigDecimal creditAmountDue;

    public Card() {
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
    @Column(name = "card_brand")
    public CardBrand getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(CardBrand cardBrand) {
        this.cardBrand = cardBrand;
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

    @ManyToOne(targetEntity = BankAccount.class)
    @JoinColumn(name = "bank_account", referencedColumnName = "id", nullable = false)
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Column(name = "credit_amount_max")
    public BigDecimal getCreditAmountMax() {
        return creditAmountMax;
    }

    public void setCreditAmountMax(BigDecimal creditAmountMax) {
        this.creditAmountMax = creditAmountMax;
    }

    @Column(name = "credit_amount_current")
    public BigDecimal getCreditAmountCurrent() {
        return creditAmountCurrent;
    }

    public void setCreditAmountCurrent(BigDecimal creditAmountCurrent) {
        this.creditAmountCurrent = creditAmountCurrent;
    }

    @Column(name = "credit_amount_due")
    public BigDecimal getCreditAmountDue() {
        return creditAmountDue;
    }

    public void setCreditAmountDue(BigDecimal creditAmountDue) {
        this.creditAmountDue = creditAmountDue;
    }
}
