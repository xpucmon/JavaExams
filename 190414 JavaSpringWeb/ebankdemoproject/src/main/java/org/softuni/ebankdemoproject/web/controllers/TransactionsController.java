package org.softuni.ebankdemoproject.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.ebankdemoproject.domain.entities.transactions.TransactionRegularity;
import org.softuni.ebankdemoproject.domain.entities.transactions.TransactionType;
import org.softuni.ebankdemoproject.domain.entities.users.User;
import org.softuni.ebankdemoproject.domain.models.binding.TransactionInitiateBindingModel;
import org.softuni.ebankdemoproject.domain.models.service.TransactionServiceModel;
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
        modelAndView.addObject("ownBankAccounts",
                this.bankAccountsService.listAllUserBankAccounts(principal.getName()));
        modelAndView.addObject("allBankAccounts",
                this.bankAccountsService.listAllBankAccounts());
        modelAndView.addObject("transactionTypes", this.transactionsService.listInitiateTransactionTypes());
        modelAndView.addObject("regularities", TransactionRegularity.values());

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
            modelAndView.setViewName("transactions/initiate");
            return modelAndView;
        }

        this.transactionsService.initiateTransaction(transactionInitiateBindingModel);

        modelAndView.setViewName("redirect:/transactions/home");
        return modelAndView;
    }

    @GetMapping("/has-regularity")
    public ModelAndView showRegularityInputField(ModelAndView modelAndView) {

        modelAndView.setViewName("fragments/regularity-input-field");
        return modelAndView;
    }

    @GetMapping("/own")
    public ModelAndView showOwnBankAccounts(Principal principal, ModelAndView modelAndView) {

        modelAndView.addObject("transactionInitiator",
                (User)this.usersService.loadUserByUsername(principal.getName()));

        modelAndView.setViewName("transactions/own-transacts");
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView showAllBankAccounts(ModelAndView modelAndView) {

        modelAndView.addObject("allTransactions",
                this.transactionsService.listAllTransactions());

        modelAndView.setViewName("transactions/users-transacts");
        return modelAndView;
    }

//    @GetMapping("/edit/{iban}")
//    public ModelAndView editBankAccount(@PathVariable("iban") String iban, ModelAndView modelAndView) {
//        BankAccountsEditBindingModel bankAccountsEditBindingModel = this.modelMapper
//                .map(this.bankAccountsService.loadBankAccountByIban(iban), BankAccountsEditBindingModel.class);
//
//        modelAndView.addObject("bindingModel", bankAccountsEditBindingModel);
//        modelAndView.addObject("accountTypes", AccountType.values());
//        modelAndView.addObject("accountStatuses", AccountStatus.values());
//
//        modelAndView.setViewName("bankaccounts/edit-account");
//        return modelAndView;
//    }
//
//    @PostMapping("/edit/{iban}")
//    public ModelAndView editBankAccountConfirm(
//            @PathVariable("iban") String iban,
//            @Valid @ModelAttribute(name = "bindingModel") BankAccountsEditBindingModel bankAccountsEditBindingModel,
//            BindingResult bindingResult, ModelAndView modelAndView) {
//
//        modelAndView.addObject("bindingModel", bankAccountsEditBindingModel);
//
//        if (bindingResult.hasErrors()) {
//            modelAndView.setViewName("bankaccounts/edit-account");
//            return modelAndView;
//        }
//
//        BankAccountsServiceModel bankAccountsServiceModel = this.modelMapper
//                .map(bankAccountsEditBindingModel, BankAccountsServiceModel.class);
//
//        this.bankAccountsService.editBankAccount(bankAccountsServiceModel);
//
//        modelAndView.setViewName("redirect:/bankaccounts/home");
//        return modelAndView;
//    }
//
//    @GetMapping("/delete/{iban}")
//    public ModelAndView delete(@PathVariable("iban") String iban, ModelAndView modelAndView) {
//        BankAccountsEditBindingModel bankAccountsEditBindingModel = this.modelMapper
//                .map(this.bankAccountsService.loadBankAccountByIban(iban), BankAccountsEditBindingModel.class);
//
//        modelAndView.addObject("bindingModel", bankAccountsEditBindingModel);
//        modelAndView.addObject("accountTypes", AccountType.values());
//        modelAndView.addObject("accountStatuses", AccountStatus.values());
//
//        modelAndView.setViewName("bankaccounts/delete-account");
//        return modelAndView;
//    }
//
//    @PostMapping("/delete/{iban}")
//    public ModelAndView deleteBankAccountConfirm(@PathVariable("iban") String iban, ModelAndView modelAndView) {
//
//        try {
//            this.bankAccountsService.deleteBankAccount(iban);
//
//            modelAndView.setViewName("redirect:/bankaccounts/home");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return modelAndView;
//    }
}
