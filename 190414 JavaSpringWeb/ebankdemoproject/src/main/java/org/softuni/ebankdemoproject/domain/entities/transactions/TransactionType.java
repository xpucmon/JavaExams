package org.softuni.ebankdemoproject.domain.entities.transactions;

public enum TransactionType {
    DEPOSIT("Deposit"),
    WITHDRAW("Withdraw"),
    TRANSFER("Transfer"),
    CARD_PAYMENT("Card payment");

    private String fieldDescription;

    private TransactionType(String value) {
        fieldDescription = value;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }
}