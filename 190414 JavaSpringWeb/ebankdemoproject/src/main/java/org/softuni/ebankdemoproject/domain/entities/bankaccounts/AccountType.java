package org.softuni.ebankdemoproject.domain.entities.bankaccounts;

public enum AccountType {
    CHECKING("Checking account"),
    SAVINGS("Savings account"),
    CREDIT("Credit account");

    private String fieldDescription;

    private AccountType(String value) {
        fieldDescription = value;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }
}