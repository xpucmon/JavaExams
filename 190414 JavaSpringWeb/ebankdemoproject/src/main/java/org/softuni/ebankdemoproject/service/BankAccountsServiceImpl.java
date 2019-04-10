package org.softuni.ebankdemoproject.service;

import org.modelmapper.ModelMapper;
import org.softuni.ebankdemoproject.domain.entities.bankaccounts.AccountStatus;
import org.softuni.ebankdemoproject.domain.entities.bankaccounts.BankAccount;
import org.softuni.ebankdemoproject.domain.entities.users.User;
import org.softuni.ebankdemoproject.domain.models.service.BankAccountsServiceModel;
import org.softuni.ebankdemoproject.repository.BankAccountsRepository;
import org.softuni.ebankdemoproject.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BankAccountsServiceImpl implements BankAccountsService {
    private final BankAccountsRepository bankAccountsRepository;
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    @Autowired
    public BankAccountsServiceImpl(BankAccountsRepository bankAccountsRepository, UsersRepository usersRepository, ModelMapper modelMapper, Validator validator) {
        this.bankAccountsRepository = bankAccountsRepository;
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean createBankAccount(BankAccountsServiceModel bankAccountsServiceModel, String name) {

        if (this.validator.validate(bankAccountsServiceModel).size() != 0) {
            throw new IllegalArgumentException("Error during account creation!");
        }

        User owner = this.usersRepository.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        bankAccountsServiceModel.setIban(this.generateNewIban());
        bankAccountsServiceModel.setAccountStatus(AccountStatus.PENDING);
        bankAccountsServiceModel.setAccountOwner(owner);
        bankAccountsServiceModel.setDateOpened(LocalDateTime.now());
        bankAccountsServiceModel.setBalance(BigDecimal.ZERO);
        bankAccountsServiceModel.setInterestRate(BigDecimal.ZERO);

        BankAccount toSave = this.modelMapper.map(bankAccountsServiceModel, BankAccount.class);

        try {
            this.bankAccountsRepository.save(toSave);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateNewIban() {
        StringBuilder accountCode = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            accountCode.append(	new Random().nextInt(10));
        }

        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 14; i++) {
            accountNumber.append(	new Random().nextInt(10));
        }

        String iban = "BG" + accountCode.toString() + "EBANK" + accountNumber.toString();

        BankAccount bankAccountByIban = this.bankAccountsRepository
                .findBankAccountByIban(iban).orElse(null);

        if (bankAccountByIban != null){
            iban = generateNewIban();
        }
        return iban;
    }

    @Override
    public List<BankAccountsServiceModel> listAllUserBankAccounts(String name) {
        User user = this.usersRepository.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        List<BankAccount> allByAccountOwner = this.bankAccountsRepository.findAllByAccountOwner(user);

        return Arrays.asList(this.modelMapper.map(allByAccountOwner, BankAccountsServiceModel[].class));
    }

    @Override
    public BankAccountsServiceModel editBankAccount(BankAccountsServiceModel bankAccountsServiceModel) {
        BankAccount bankAccount = this.bankAccountsRepository.findBankAccountByIban(bankAccountsServiceModel.getIban())
                .orElseThrow(() -> new IllegalArgumentException("Bank account was not found!"));

        bankAccount.setAccountType(bankAccountsServiceModel.getAccountType());
        bankAccount.setAccountStatus(bankAccountsServiceModel.getAccountStatus());
        bankAccount.setBalance(bankAccountsServiceModel.getBalance());
        bankAccount.setInterestRate(bankAccountsServiceModel.getInterestRate());

        BankAccount saveBankAccount = this.bankAccountsRepository.save(bankAccount);

        return this.modelMapper.map(saveBankAccount, BankAccountsServiceModel.class);
    }

    @Override
    public void deleteBankAccount(String id) {
        try {
            this.bankAccountsRepository.deleteById(id);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public BankAccountsServiceModel loadBankAccountByIban(String iban) {
        BankAccount bankAccountById = this.bankAccountsRepository.findBankAccountByIban(iban)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return this.modelMapper.map(bankAccountById, BankAccountsServiceModel.class);
    }
}
