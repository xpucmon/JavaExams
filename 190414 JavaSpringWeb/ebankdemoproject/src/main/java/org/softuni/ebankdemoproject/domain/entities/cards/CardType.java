package org.softuni.ebankdemoproject.domain.entities.cards;

public enum CardType {
    DEBIT_CARD("Debit card"),
    CREDIT_CARD("Credit card");

    private String fieldDescription;

    private CardType(String value) {
        fieldDescription = value;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }
}