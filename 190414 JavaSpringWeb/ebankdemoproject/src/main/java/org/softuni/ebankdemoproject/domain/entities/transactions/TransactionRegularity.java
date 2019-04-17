package org.softuni.ebankdemoproject.domain.entities.transactions;

public enum TransactionRegularity {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
