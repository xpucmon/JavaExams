package org.softuni.ebankdemoproject.domain.entities.transactions;

public enum TransactionStatus {
    NOT_CONFIRMED("Not confirmed"),
    CANCELED("Canceled"),
    PENDING_APPROVAL("Pending approval"),
    REJECTED("Rejected"),
    COMPLETED("Completed"),
    ONGOING_RECURRENT("Ongoing recurrent");

    private String fieldDescription;

    private TransactionStatus(String value) {
        fieldDescription = value;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }
}