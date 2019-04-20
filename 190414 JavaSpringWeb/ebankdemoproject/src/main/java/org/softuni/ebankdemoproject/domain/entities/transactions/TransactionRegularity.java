package org.softuni.ebankdemoproject.domain.entities.transactions;

public enum TransactionRegularity {
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly"),
    YEARLY("Yearly");

    private String fieldDescription;

    private TransactionRegularity(String value) {
        fieldDescription = value;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }
}
