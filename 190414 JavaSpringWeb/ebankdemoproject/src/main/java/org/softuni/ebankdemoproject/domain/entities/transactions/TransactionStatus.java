package org.softuni.ebankdemoproject.domain.entities.transactions;

public enum TransactionStatus {
    AWAITING_CONFIRMATION,
    CANCELED,
    PENDING_APPROVAL,
    REJECTED,
    COMPLETED;
}