package org.softuni.ebankdemoproject.domain.entities.loans;

import org.softuni.ebankdemoproject.domain.entities.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "card_loans")
public class CreditCardLoan extends BaseEntity {
    private BigDecimal creditAmountMax;
    private BigDecimal creditAmountCurrent;
    private BigDecimal creditAmountDue;

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
