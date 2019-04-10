package org.softuni.ebankdemoproject.domain.entities.cards;

public enum CardStatus {
    NOT_ACTIVE("Not active"),
    ACTIVE("Active"),
    SUSPENDED("Suspended"),
    EXPIRED("Expired");

    private String fieldDescription;

    private CardStatus(String value) {
        fieldDescription = value;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }
}