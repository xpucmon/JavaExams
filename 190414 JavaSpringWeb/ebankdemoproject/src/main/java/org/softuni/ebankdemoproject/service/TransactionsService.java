package org.softuni.ebankdemoproject.service;

import org.softuni.ebankdemoproject.domain.entities.transactions.TransactionType;
import org.softuni.ebankdemoproject.domain.models.binding.TransactionEditBindingModel;
import org.softuni.ebankdemoproject.domain.models.binding.TransactionInitiateBindingModel;
import org.softuni.ebankdemoproject.domain.models.service.TransactionServiceModel;
import org.softuni.ebankdemoproject.domain.models.view.TransactionViewModel;

import java.util.List;

public interface TransactionsService {

    boolean initiateTransaction(TransactionInitiateBindingModel transactionInitiateBindingModel);

    List<TransactionType> listInitiateTransactionTypes();

    List<TransactionServiceModel> listAllTransactions();

    List<TransactionServiceModel> listAllUserTransactions(String name);

    TransactionServiceModel loadTransactionById(String id);

    TransactionServiceModel editTransaction(TransactionEditBindingModel transactionEditBindingModel);

    void confirmTransaction(String id);

    void cancelTransaction(String id, String principalName);
}