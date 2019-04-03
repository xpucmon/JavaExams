package org.softuni.ebankdemoproject.domain.entities.loans;

import org.softuni.ebankdemoproject.domain.entities.BaseEntity;
import org.softuni.ebankdemoproject.domain.entities.users.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "loans")
public class Loan extends BaseEntity {
    private User borrower;
    private BigDecimal initialLoanAmount;
    private BigDecimal loanAmountDue;
    private LocalDateTime issueDate;
    private Long installmentsDueCount;
    private BigDecimal installmentAmount;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "borrower", referencedColumnName = "id")
    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    @Column(name = "initial_loan_amount")
    public BigDecimal getInitialLoanAmount() {
        return initialLoanAmount;
    }

    public void setInitialLoanAmount(BigDecimal initialLoanAmount) {
        this.initialLoanAmount = initialLoanAmount;
    }

    @Column(name = "loan_amount_due")
    public BigDecimal getLoanAmountDue() {
        return loanAmountDue;
    }

    public void setLoanAmountDue(BigDecimal loanAmountDue) {
        this.loanAmountDue = loanAmountDue;
    }

    @Column(name = "issue_date", nullable = false, updatable = false)
    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    @Column(name = "installments_due_count")
    public Long getInstallmentsDueCount() {
        return installmentsDueCount;
    }

    public void setInstallmentsDueCount(Long installmentsDueCount) {
        this.installmentsDueCount = installmentsDueCount;
    }

    @Column(name = "installment_amount")
    public BigDecimal getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(BigDecimal installmentAmount) {
        this.installmentAmount = installmentAmount;
    }
}
