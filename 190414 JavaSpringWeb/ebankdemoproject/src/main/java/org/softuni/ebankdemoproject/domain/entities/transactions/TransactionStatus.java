package org.softuni.ebankdemoproject.domain.entities.transactions;

public enum TransactionStatus {
    NOT_CONFIRMED("Not confirmed"),
    CANCELED("Canceled"),
    PENDING_APPROVAL("Pending approval"),
    REJECTED("Rejected"),
    COMPLETED("Completed");

    private String fieldDescription;

    private TransactionStatus(String value) {
        fieldDescription = value;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }
}