package org.softuni.ebankdemoproject.service;

import org.softuni.ebankdemoproject.domain.entities.transactions.TransactionType;
import org.softuni.ebankdemoproject.domain.models.binding.TransactionInitiateBindingModel;
import org.softuni.ebankdemoproject.domain.models.service.TransactionServiceModel;

import java.util.List;

public interface TransactionsService {

    boolean initiateTransaction(TransactionInitiateBindingModel transactionInitiateBindingModel);

    List<TransactionServiceModel> listAllTransactions();

    List<TransactionServiceModel> listAllUserTransactions(String name);

    TransactionServiceModel loadTransactionById(String id);

    TransactionServiceModel editTransaction(TransactionServiceModel transactionServiceModel);

    void deleteTransaction(String id);

    List<TransactionType> listInitiateTransactionTypes();
}
