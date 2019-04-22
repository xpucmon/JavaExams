package org.softuni.ebankdemoproject.service;

import org.softuni.ebankdemoproject.domain.models.service.BankAccountsServiceModel;

import java.util.List;

public interface BankAccountsService {

    boolean createBankAccount(BankAccountsServiceModel bankAccountsServiceModel, String name);

    List<BankAccountsServiceModel> listAllBankAccounts();

    List<BankAccountsServiceModel> listUserBankAccounts(String name);

    void deleteBankAccount(String id);

    BankAccountsServiceModel loadBankAccountByIban(String iban);

    BankAccountsServiceModel editBankAccount(BankAccountsServiceModel bankAccountsServiceModel);
}
