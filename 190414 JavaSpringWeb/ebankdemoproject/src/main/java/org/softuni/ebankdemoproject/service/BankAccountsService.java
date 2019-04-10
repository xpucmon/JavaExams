package org.softuni.ebankdemoproject.service;

import org.softuni.ebankdemoproject.domain.models.service.BankAccountsServiceModel;
import org.softuni.ebankdemoproject.domain.models.view.BankAccountsViewModel;

import java.util.List;

public interface BankAccountsService {

    boolean createBankAccount(BankAccountsServiceModel bankAccountsServiceModel, String name);

    List<BankAccountsServiceModel> listAllUserBankAccounts(String name);

    void deleteBankAccount(String id);

    BankAccountsServiceModel loadBankAccountByIban(String iban);

    BankAccountsServiceModel editBankAccount(BankAccountsServiceModel bankAccountsServiceModel);
}
