package org.softuni.ebankdemoproject.domain.models.binding;

import org.softuni.ebankdemoproject.domain.models.service.BaseServiceModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CardEditBindingModel extends BaseServiceModel {
    private String cardNumber;
    private String cardHolder;
    private String cardType;
    private String cardBrand;
    private String cardStatus;
    private LocalDateTime issueDate;
    private LocalDateTime expirationDate;
    private String bankAccount;
    private BigDecimal creditAmountMax;
    private BigDecimal creditAmountCurrent;
    private BigDecimal creditAmountDue;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public String getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(String cardBrand) {
        this.cardBrand = cardBrand;
    }

    public void setCardStatus(String cardStatus) {
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

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
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
