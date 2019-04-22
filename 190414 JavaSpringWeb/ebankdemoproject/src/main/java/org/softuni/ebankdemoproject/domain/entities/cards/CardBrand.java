package org.softuni.ebankdemoproject.domain.entities.cards;

public enum CardBrand {
    VISA_CREDIT_CARD("VISA"),
    MASTERCARD_CREDIT_CARD("MasterCard"),
    VISA_ELECTRON_DEBIT_CARD("Visa Electron"),
    MAESTRO_DEBIT_CARD("Maestro");

    private String fieldDescription;

    private CardBrand(String value) {
        fieldDescription = value;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }
}