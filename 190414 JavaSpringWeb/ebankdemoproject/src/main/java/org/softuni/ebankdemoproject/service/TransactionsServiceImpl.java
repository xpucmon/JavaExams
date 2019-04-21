package org.softuni.ebankdemoproject.service;

import org.modelmapper.ModelMapper;
import org.softuni.ebankdemoproject.domain.entities.bankaccounts.AccountStatus;
import org.softuni.ebankdemoproject.domain.entities.bankaccounts.BankAccount;
import org.softuni.ebankdemoproject.domain.entities.transactions.Transaction;
import org.softuni.ebankdemoproject.domain.entities.transactions.TransactionStatus;
import org.softuni.ebankdemoproject.domain.entities.transactions.TransactionType;
import org.softuni.ebankdemoproject.domain.entities.users.RoleConstant;
import org.softuni.ebankdemoproject.domain.models.binding.TransactionEditBindingModel;
import org.softuni.ebankdemoproject.domain.models.binding.TransactionInitiateBindingModel;
import org.softuni.ebankdemoproject.domain.models.service.BankAccountsServiceModel;
import org.softuni.ebankdemoproject.domain.models.service.TransactionServiceModel;
import org.softuni.ebankdemoproject.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.softuni.ebankdemoproject.domain.entities.transactions.TransactionType.*;

@Service
public class TransactionsServiceImpl implements TransactionsService {
    private final TransactionsRepository transactionsRepository;
    private final BankAccountsService bankAccountsService;
    private final UsersService usersService;
    private final ModelMapper modelMapper;
    private final Validator validator;

    @Autowired
    public TransactionsServiceImpl(TransactionsRepository transactionsRepository,
                                   BankAccountsService bankAccountsService, UsersService usersService, ModelMapper modelMapper,
                                   Validator validator) {
        this.transactionsRepository = transactionsRepository;
        this.bankAccountsService = bankAccountsService;
        this.usersService = usersService;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean initiateTransaction(TransactionInitiateBindingModel transactionInitiateBindingModel) {
        if (this.validator.validate(transactionInitiateBindingModel).size() != 0) {
            throw new IllegalArgumentException("Error during transaction initiation!");
        }

        BankAccountsServiceModel bankAccountsServiceModel = this.bankAccountsService
                .loadBankAccountByIban(transactionInitiateBindingModel.getBankAccount());

        if (!bankAccountsServiceModel.getAccountStatus().equals(AccountStatus.ACTIVE)) {
            throw new IllegalArgumentException("This bank account is not active");
        }

        TransactionServiceModel transactionServiceModel = this.modelMapper
                .map(transactionInitiateBindingModel, TransactionServiceModel.class);

        transactionServiceModel.setBankAccount(this.modelMapper.map(bankAccountsServiceModel, BankAccount.class));
        transactionServiceModel.setTransactionFee(BigDecimal.ZERO);
        transactionServiceModel.setTransactionDateTime(LocalDateTime.now());
        transactionServiceModel.setStatus(TransactionStatus.NOT_CONFIRMED);

        Transaction toSave = this.modelMapper.map(transactionServiceModel, Transaction.class);

        try {
            this.transactionsRepository.save(toSave);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<TransactionServiceModel> listAllUserTransactions(String name) {

        List<BankAccountsServiceModel> allAccountsByAccountOwner = this.bankAccountsService
                .listAllUserBankAccounts(name);

        List<TransactionServiceModel> allTransactionsByAccountOwner = new ArrayList<>();

        allAccountsByAccountOwner
                .forEach(b -> allTransactionsByAccountOwner.addAll(Arrays.asList(this.modelMapper
                        .map(this.transactionsRepository.findAllByBankAccountOrderByTransactionDateTimeDesc(
                                this.modelMapper.map(b, BankAccount.class)), TransactionServiceModel[].class))));

        return allTransactionsByAccountOwner;
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
    public TransactionServiceModel editTransaction(TransactionEditBindingModel transactionEditBindingModel) {
        TransactionServiceModel transactionServiceModel = this.modelMapper
                .map(transactionEditBindingModel, TransactionServiceModel.class);

        transactionServiceModel.setBankAccount(this.modelMapper.map(this.bankAccountsService
                        .loadBankAccountByIban(transactionEditBindingModel.getBankAccount()), BankAccount.class));

        Transaction transactionById = this.transactionsRepository.findTransactionById(transactionServiceModel.getId())
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        transactionById.setBankAccount(transactionServiceModel.getBankAccount());
        transactionById.setAmount(transactionServiceModel.getAmount());
        transactionById.setRegular(transactionServiceModel.isRegular());
        transactionById.setRegularity(transactionServiceModel.getRegularity());

        if (transactionServiceModel.getRecipientIban() != null){
            transactionById.setRecipientIban(transactionServiceModel.getRecipientIban());
            transactionById.setRecipientFirstName(transactionServiceModel.getRecipientFirstName());
            transactionById.setRecipientLastName(transactionServiceModel.getRecipientLastName());
        }

        Transaction saveTransaction = this.transactionsRepository.save(transactionById);

        return this.modelMapper.map(saveTransaction, TransactionServiceModel.class);
    }

    @Override
    public void confirmTransaction(String id) {
        Transaction transactionById = this.transactionsRepository.findTransactionById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        TransactionServiceModel transactionServiceModel = this.modelMapper
                .map(transactionById, TransactionServiceModel.class);

        String iban = transactionServiceModel.getBankAccount().getIban();

        BankAccountsServiceModel bankAccountsServiceModel = this.bankAccountsService
                .loadBankAccountByIban(iban);

        try {
            switch (transactionById.getTransactionType()) {
                case DEPOSIT:
                    depositMoney(bankAccountsServiceModel, transactionServiceModel);
                    assignCompletionStatus(transactionById, transactionServiceModel);
                    break;
                case WITHDRAW:
                case CARD_PAYMENT:
                    withdrawMoney(bankAccountsServiceModel, transactionServiceModel);
                    assignCompletionStatus(transactionById, transactionServiceModel);
                    break;
                case TRANSFER:
                    withdrawMoney(bankAccountsServiceModel, transactionServiceModel);
                    //TODO check money deposited to the Recipient's account / if IBAN does not exist in DB???
                    if (this.bankAccountsService.loadBankAccountByIban(transactionServiceModel.getRecipientIban()) != null){
                        depositMoney(bankAccountsServiceModel, transactionServiceModel);
                    }
                    assignCompletionStatus(transactionById, transactionServiceModel);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown operation");
            }
            this.transactionsRepository.save(transactionById);
            this.bankAccountsService.editBankAccount(bankAccountsServiceModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void withdrawMoney(BankAccountsServiceModel bankAccountsServiceModel, TransactionServiceModel transactionServiceModel) {
        if (transactionServiceModel.getAmount().add(transactionServiceModel.getTransactionFee())
                .compareTo(bankAccountsServiceModel.getBalance()) <= 0) {
            bankAccountsServiceModel.setBalance(bankAccountsServiceModel.getBalance()
                    .subtract(transactionServiceModel.getAmount())
                    .add(transactionServiceModel.getTransactionFee()));
        } else {
            throw new IllegalArgumentException("Not enough funds!");
        }
    }

    private void depositMoney(BankAccountsServiceModel bankAccountsServiceModel, TransactionServiceModel transactionServiceModel) {
        if (bankAccountsServiceModel.getBalance().add(transactionServiceModel.getAmount())
                .compareTo(transactionServiceModel.getTransactionFee()) >= 0) {
            bankAccountsServiceModel.setBalance(bankAccountsServiceModel.getBalance()
                    .add(transactionServiceModel.getAmount())
                    .subtract(transactionServiceModel.getTransactionFee()));
        } else {
            throw new IllegalArgumentException("Not enough funds to cover the fee!");
        }
    }

    private void assignCompletionStatus(Transaction transactionById, TransactionServiceModel transactionServiceModel) {
        if (transactionServiceModel.isRegular()) {
            transactionById.setStatus(TransactionStatus.ONGOING_RECURRENT);
        } else {
            transactionById.setStatus(TransactionStatus.COMPLETED);
        }
    }

    @Override
    public void cancelTransaction(String id, String principalName) {
        Transaction transactionById = this.transactionsRepository.findTransactionById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        UserDetails userDetails = this.usersService.loadUserByUsername(principalName);

        try {
            if (!transactionById.getBankAccount().getAccountOwner().getUsername().equals(userDetails.getUsername())
                    && userDetails.getAuthorities().contains(RoleConstant.EMPLOYEE)) {
                transactionById.setStatus(TransactionStatus.REJECTED);
            } else {
                transactionById.setStatus(TransactionStatus.CANCELED);
            }
            this.transactionsRepository.save(transactionById);
//                this.transactionsRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TransactionType> listInitiateTransactionTypes() {
        return Arrays.stream(values())
                .filter(t -> !t.equals(CARD_PAYMENT))
                .collect(Collectors.toList());
    }
}
