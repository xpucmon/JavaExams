package org.softuni.ebankdemoproject.domain.entities.transactions;

import org.iban4j.Iban;
import org.softuni.ebankdemoproject.domain.entities.BaseEntity;
import org.softuni.ebankdemoproject.domain.entities.bankaccounts.BankAccount;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transactions")
public class Transaction extends BaseEntity {
    private TransactionType transactionType;
    private BankAccount bankAccount;
    private String recipientFirstName;
    private String recipientLastName;
    private String recipientIban;
    private BigDecimal amount;
    private BigDecimal transactionFee;
    private LocalDateTime transactionDateTime;
    private boolean isRegular = false;
    private TransactionRegularity regularity;
    private TransactionStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "transaction_type")
    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @ManyToOne(targetEntity = BankAccount.class)
    @JoinColumn(name = "bank_account", referencedColumnName = "id", nullable = false)
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Column(name = "recipient_first_name", nullable = false)
    public String getRecipientFirstName() {
        return recipientFirstName;
    }

    public void setRecipientFirstName(String recipientFirstName) {
        this.recipientFirstName = recipientFirstName;
    }

    @Column(name = "recipient_last_name", nullable = false)
    public String getRecipientLastName() {
        return recipientLastName;
    }

    public void setRecipientLastName(String recipientLastName) {
        this.recipientLastName = recipientLastName;
    }

    @Column(name = "recipient_iban", nullable = false)
    public String getRecipientIban() {
        return recipientIban;
    }

    public void setRecipientIban(String recipientIban) {
        this.recipientIban = recipientIban;
    }

    @Column(nullable = false)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column(name = "transaction_fee")
    public BigDecimal getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(BigDecimal transactionFee) {
        this.transactionFee = transactionFee;
    }

    @Column(name = "transaction_date_time", nullable = false)
    public LocalDateTime getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(LocalDateTime transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    @Column(name = "is_regular")
    public boolean isRegular() {
        return isRegular;
    }

    public void setRegular(boolean regular) {
        isRegular = regular;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "transaction_regularity")
    public TransactionRegularity getRegularity() {
        return regularity;
    }

    public void setRegularity(TransactionRegularity regularity) {
        this.regularity = regularity;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "transaction_status")
    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
