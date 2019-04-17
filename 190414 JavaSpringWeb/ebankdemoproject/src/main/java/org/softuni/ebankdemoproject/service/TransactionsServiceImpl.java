package org.softuni.ebankdemoproject.service;

import org.modelmapper.ModelMapper;
import org.softuni.ebankdemoproject.domain.entities.bankaccounts.AccountStatus;
import org.softuni.ebankdemoproject.domain.entities.bankaccounts.BankAccount;
import org.softuni.ebankdemoproject.domain.entities.transactions.Transaction;
import org.softuni.ebankdemoproject.domain.entities.transactions.TransactionStatus;
import org.softuni.ebankdemoproject.domain.entities.transactions.TransactionType;
import org.softuni.ebankdemoproject.domain.entities.users.User;
import org.softuni.ebankdemoproject.domain.models.binding.TransactionInitiateBindingModel;
import org.softuni.ebankdemoproject.domain.models.service.TransactionServiceModel;
import org.softuni.ebankdemoproject.repository.BankAccountsRepository;
import org.softuni.ebankdemoproject.repository.TransactionsRepository;
import org.softuni.ebankdemoproject.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionsServiceImpl implements TransactionsService{
    private final TransactionsRepository transactionsRepository;
    private final BankAccountsRepository bankAccountsRepository;
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    @Autowired
    public TransactionsServiceImpl(TransactionsRepository transactionsRepository,
                                   BankAccountsRepository bankAccountsRepository,
                                   UsersRepository usersRepository,
                                   ModelMapper modelMapper,
                                   Validator validator) {
        this.transactionsRepository = transactionsRepository;
        this.bankAccountsRepository = bankAccountsRepository;
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean initiateTransaction(TransactionInitiateBindingModel transactionInitiateBindingModel){
        if (this.validator.validate(transactionInitiateBindingModel).size() != 0) {
            throw new IllegalArgumentException("Error during transaction initiation!");
        }

        BankAccount bankAccount = this.bankAccountsRepository
                .findBankAccountByIban(transactionInitiateBindingModel.getBankAccount())
                .orElseThrow(() -> new IllegalArgumentException("No such active bank account!"));

        if (!bankAccount.getAccountStatus().equals(AccountStatus.ACTIVE)){
            throw new IllegalArgumentException("This bank account is not active");
        }

        TransactionServiceModel transactionServiceModel = this.modelMapper
                .map(transactionInitiateBindingModel, TransactionServiceModel.class);

        transactionServiceModel.setBankAccount(bankAccount);
        transactionServiceModel.setTransactionFee(BigDecimal.ZERO);
        transactionServiceModel.setTransactionDateTime(LocalDateTime.now());
        transactionServiceModel.setStatus(TransactionStatus.AWAITING_CONFIRMATION);

        Transaction toSave = this.modelMapper.map(transactionServiceModel, Transaction.class);

        try {
            this.transactionsRepository.save(toSave);
            //TODO add logic to deposit/withdrow/transfer money to BankAccount
            bankAccount.getTransactions().add(toSave);
            this.bankAccountsRepository.save(bankAccount);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<TransactionServiceModel> listAllUserTransactions(String name) {
        User user = this.usersRepository.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        List<BankAccount> allAccountsByAccountOwner = this.bankAccountsRepository.findAllByAccountOwner(user);
        List<Transaction> allTransactionsByAccountOwner = new ArrayList<>();

        for (BankAccount bankAccount : allAccountsByAccountOwner) {
            allTransactionsByAccountOwner.addAll(this.transactionsRepository.findAllByBankAccount(bankAccount));
        }

        return Arrays.asList(this.modelMapper.map(allTransactionsByAccountOwner, TransactionServiceModel[].class));
    }

    @Override
    public List<TransactionServiceModel> listAllTransactions() {
        return Arrays.asList(this.modelMapper.map(
                this.transactionsRepository.findAllByOrderByTransactionDateTimeDesc(),
                TransactionServiceModel[].class));
    }

    @Override
    public TransactionServiceModel loadTransactionById(String id) {
        Transaction transactionById = this.transactionsRepository.findTransactionById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        return this.modelMapper.map(transactionById, TransactionServiceModel.class);
    }

    @Override
    public TransactionServiceModel editTransaction(TransactionServiceModel transactionServiceModel) {
        Transaction transactionById = this.transactionsRepository.findTransactionById(transactionServiceModel.getId())
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        transactionById.setBankAccount(transactionServiceModel.getBankAccount());
        transactionById.setTransactionFee(transactionServiceModel.getTransactionFee());
        transactionById.setTransactionDateTime(transactionServiceModel.getTransactionDateTime());
        transactionById.setStatus(transactionServiceModel.getStatus());

        Transaction saveTransaction = this.transactionsRepository.save(transactionById);

        return this.modelMapper.map(saveTransaction, TransactionServiceModel.class);
    }

    @Override
    public void deleteTransaction(String id) {
        Transaction transactionById = this.transactionsRepository.findTransactionById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        BankAccount bankAccount = transactionById.getBankAccount();

        try {
            bankAccount.getTransactions().remove(transactionById);
            this.bankAccountsRepository.save(bankAccount);
            this.transactionsRepository.deleteById(id);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<TransactionType> listInitiateTransactionTypes() {
        return Arrays.stream(TransactionType.values())
                .filter(t -> !t.equals(TransactionType.CARD_PAYMENT))
                .collect(Collectors.toList());
    }
}
