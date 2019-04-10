package org.softuni.ebankdemoproject.domain.entities.bankaccounts;

public enum AccountStatus {
    PENDING("Pending"),
    ACTIVE("Active"),
    SUSPENDED("Suspended"),
    CLOSED("Closed");

    private String fieldDescription;

    private AccountStatus(String value) {
        fieldDescription = value;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

//    public String toString() {
//        return name().charAt(0) + name().substring(1).toLowerCase();
//    }
}