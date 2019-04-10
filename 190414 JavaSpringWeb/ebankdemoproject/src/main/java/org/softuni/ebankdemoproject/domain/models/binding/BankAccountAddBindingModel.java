package org.softuni.ebankdemoproject.domain.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BankAccountAddBindingModel {
    private String accountType;

    @NotEmpty
    @NotNull
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
