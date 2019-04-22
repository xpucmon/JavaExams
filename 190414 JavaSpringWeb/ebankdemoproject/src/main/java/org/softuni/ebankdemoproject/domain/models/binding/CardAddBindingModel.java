package org.softuni.ebankdemoproject.domain.models.binding;

import org.softuni.ebankdemoproject.domain.models.service.BaseServiceModel;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CardAddBindingModel extends BaseServiceModel {
    private String cardBrand;
    private String bankAccount;
    private BigDecimal creditAmountMax = new BigDecimal(0);

    @NotNull
    public String getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(String cardBrand) {
        this.cardBrand = cardBrand;
    }

    @NotNull
    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public BigDecimal getCreditAmountMax() {
        return creditAmountMax;
    }

    public void setCreditAmountMax(BigDecimal creditAmountMax) {
        this.creditAmountMax = creditAmountMax;
    }
}
