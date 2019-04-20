package org.softuni.ebankdemoproject.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.ebankdemoproject.domain.entities.transactions.TransactionRegularity;
import org.softuni.ebankdemoproject.domain.entities.transactions.TransactionStatus;
import org.softuni.ebankdemoproject.domain.entities.transactions.TransactionType;
import org.softuni.ebankdemoproject.domain.models.binding.TransactionEditBindingModel;
import org.softuni.ebankdemoproject.domain.models.binding.TransactionInitiateBindingModel;
import org.softuni.ebankdemoproject.domain.models.view.TransactionViewModel;
import org.softuni.ebankdemoproject.service.BankAccountsService;
import org.softuni.ebankdemoproject.service.TransactionsService;
import org.softuni.ebankdemoproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;
    private final BankAccountsService bankAccountsService;
    private final UsersService usersService;
    private final ModelMapper modelMapper;

    @Autowired
    public TransactionsController(TransactionsService transactionsService, BankAccountsService bankAccountsService, UsersService usersService, ModelMapper modelMapper) {
        this.transactionsService = transactionsService;
        this.bankAccountsService = bankAccountsService;
        this.usersService = usersService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/home")
    @PreAuthorize(value = "isAuthenticated()")
    public ModelAndView home(Principal principal, ModelAndView modelAndView) {

        modelAndView.addObject("bankAccountOwner",
                this.usersService.loadUserByUsername(principal.getName()));
        modelAndView.addObject("ownTransactions",
                this.transactionsService.listAllUserTransactions(principal.getName()));
        modelAndView.addObject("allTransactions",
                this.transactionsService.listAllTransactions());

        modelAndView.setViewName("transactions/home-transacts");
        return modelAndView;
    }

    @GetMapping("/initiate")
    public ModelAndView initiateTransaction(Principal principal, ModelAndView modelAndView,
                                       @ModelAttribute(name = "bindingModel")
                                               TransactionInitiateBindingModel transactionInitiateBindingModel) {

        modelAndView.addObject("bindingModel", transactionInitiateBindingModel);
        modelAndView.addObject("transactionTypes",
                this.transactionsService.listInitiateTransactionTypes());
        modelAndView.addObject("regularities",  TransactionRegularity.values());
        modelAndView.addObject("ownBankAccounts",
                this.bankAccountsService.listAllUserBankAccounts(principal.getName()));
        modelAndView.addObject("allBankAccounts",
                this.bankAccountsService.listAllBankAccounts());

        modelAndView.setViewName("transactions/initiate-transact");
        return modelAndView;
    }

    @PostMapping("/initiate")
    public ModelAndView initiateTransactionConfirm(
            Principal principal, ModelAndView modelAndView,
            @Valid @ModelAttribute(name = "bindingModel")
                    TransactionInitiateBindingModel transactionInitiateBindingModel, BindingResult bindingResult){

        modelAndView.addObject("bindingModel", transactionInitiateBindingModel);

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("transactions/initiate-transact");
            return modelAndView;
        }

        this.transactionsService.initiateTransaction(transactionInitiateBindingModel);

        modelAndView.setViewName("redirect:/transactions/home");
        return modelAndView;
    }

    @GetMapping("/own")
    public ModelAndView showOwnBankAccounts(Principal principal, ModelAndView modelAndView) {

        TransactionViewModel[] ownTransactions = this.modelMapper.map(this.transactionsService
                .listAllUserTransactions(principal.getName()), TransactionViewModel[].class);

        modelAndView.addObject("ownTransactions", ownTransactions);
        modelAndView.addObject("notConfirmedStatus", TransactionStatus.NOT_CONFIRMED);

        modelAndView.setViewName("transactions/own-transacts");
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView showAllBankAccounts(ModelAndView modelAndView) {

        modelAndView.addObject("allTransactions", this.transactionsService.listAllTransactions());
        modelAndView.addObject("notConfirmedStatus", TransactionStatus.NOT_CONFIRMED);

        modelAndView.setViewName("transactions/users-transacts");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editTransaction(@PathVariable("id") String id, ModelAndView modelAndView) {

        TransactionViewModel transactionViewModel = this.modelMapper
                .map(this.transactionsService.loadTransactionById(id), TransactionViewModel.class);

        modelAndView.addObject("bindingModel", transactionViewModel);
        addEnumAndAccountsObjects(modelAndView, transactionViewModel);
        modelAndView.addObject("transferType", TransactionType.TRANSFER);
        modelAndView.addObject("notConfirmedStatus", TransactionStatus.NOT_CONFIRMED);

        modelAndView.setViewName("transactions/edit-transaction");
        return modelAndView;
    }

    private void addBindingModelAndEnumObjects(ModelAndView modelAndView, TransactionViewModel transactionViewModel) {

    }

    @PostMapping("/edit/{id}")
    public ModelAndView editTransactionConfirm(
            @PathVariable("id") String id,
            @Valid @ModelAttribute(name = "bindingModel") TransactionEditBindingModel transactionEditBindingModel,
            BindingResult bindingResult, ModelAndView modelAndView) {

        modelAndView.addObject("bindingModel", transactionEditBindingModel);

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("transactions/edit-transaction");
            return modelAndView;
        }

        this.transactionsService.editTransaction(transactionEditBindingModel);

        modelAndView.setViewName("redirect:/transactions/home");
        return modelAndView;
    }

    @GetMapping("/confirm/{id}")
    public ModelAndView confirmTransaction(@PathVariable("id") String id, ModelAndView modelAndView) {
        TransactionViewModel transactionViewModel = this.modelMapper
                .map(this.transactionsService.loadTransactionById(id), TransactionViewModel.class);

        modelAndView.addObject("bindingModel", transactionViewModel);
        addEnumAndAccountsObjects(modelAndView, transactionViewModel);

        modelAndView.setViewName("transactions/confirm-transaction");
        return modelAndView;
    }

    @PostMapping("/confirm/{id}")
    public ModelAndView confirmBankAccountTransactionConfirm(@PathVariable("id") String id, ModelAndView modelAndView){

        this.transactionsService.confirmTransaction(id);

        modelAndView.setViewName("redirect:/transactions/home");
        return modelAndView;
    }

    @GetMapping("/cancel/{id}")
    public ModelAndView cancelTransaction(@PathVariable("id") String id, ModelAndView modelAndView) {
        TransactionViewModel transactionViewModel = this.modelMapper
                .map(this.transactionsService.loadTransactionById(id), TransactionViewModel.class);

        modelAndView.addObject("bindingModel", transactionViewModel);
        addEnumAndAccountsObjects(modelAndView, transactionViewModel);
        modelAndView.setViewName("transactions/cancel-transaction");
        return modelAndView;
    }

    @PostMapping("/cancel/{id}")
    public ModelAndView cancelBankAccountTransactionConfirm(@PathVariable("id") String id,
                                                            ModelAndView modelAndView,
                                                            Principal principal) {

        this.transactionsService.cancelTransaction(id, principal.getName());

        modelAndView.setViewName("redirect:/transactions/home");
        return modelAndView;
    }

    private void addEnumAndAccountsObjects(ModelAndView modelAndView, TransactionViewModel transactionViewModel) {
        modelAndView.addObject("transactionTypes", TransactionType.values());
        modelAndView.addObject("transactionStatuses", TransactionStatus.values());
        modelAndView.addObject("transactionRegularities", TransactionRegularity.values());

        modelAndView.addObject("ownBankAccounts", this.bankAccountsService
                .listAllUserBankAccounts(transactionViewModel.getBankAccount().getAccountOwner().getUsername()));

    }
}
