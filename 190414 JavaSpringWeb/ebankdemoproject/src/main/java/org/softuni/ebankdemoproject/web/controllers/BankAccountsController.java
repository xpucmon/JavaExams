package org.softuni.ebankdemoproject.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.ebankdemoproject.domain.entities.bankaccounts.AccountStatus;
import org.softuni.ebankdemoproject.domain.entities.bankaccounts.AccountType;
import org.softuni.ebankdemoproject.domain.entities.users.User;
import org.softuni.ebankdemoproject.domain.models.binding.BankAccountAddBindingModel;
import org.softuni.ebankdemoproject.domain.models.binding.BankAccountsEditBindingModel;
import org.softuni.ebankdemoproject.domain.models.service.BankAccountsServiceModel;
import org.softuni.ebankdemoproject.service.BankAccountsService;
import org.softuni.ebankdemoproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/bankaccounts")
public class BankAccountsController {

    private final BankAccountsService bankAccountsService;
    private final UsersService usersService;
    private final ModelMapper modelMapper;

    @Autowired
    public BankAccountsController(BankAccountsService bankAccountsService, UsersService usersService, ModelMapper modelMapper) {
        this.bankAccountsService = bankAccountsService;
        this.usersService = usersService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/home")
    @PreAuthorize(value = "isAuthenticated()")
    public ModelAndView home(Principal principal, ModelAndView modelAndView) {

        modelAndView.addObject("bankAccountOwner",
                this.usersService.loadUserByUsername(principal.getName()));
        modelAndView.addObject("ownBankAccounts",
                this.bankAccountsService.listAllUserBankAccounts(principal.getName()));
        modelAndView.addObject("allBankAccounts",
                this.bankAccountsService.listAllBankAccounts());

        modelAndView.setViewName("bankaccounts/home-accounts");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createBankAccount(ModelAndView modelAndView,
                                          @ModelAttribute(name = "bindingModel") BankAccountAddBindingModel bankAccountAddBindingModel) {

        modelAndView.addObject("bindingModel", bankAccountAddBindingModel);
        modelAndView.addObject("accountTypes", AccountType.values());
        modelAndView.addObject("accountOwners", this.usersService.listAllUsers());

        modelAndView.setViewName("bankaccounts/create-account");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createBankAccountConfirm(
            @Valid @ModelAttribute(name = "bindingModel") BankAccountAddBindingModel bankAccountAddBindingModel,
            BindingResult bindingResult, Principal principal, ModelAndView modelAndView) {

        modelAndView.addObject("bindingModel", bankAccountAddBindingModel);

        if (bindingResult.hasErrors() && bankAccountAddBindingModel.getAccountOwner() != null) {
            modelAndView.setViewName("bankaccounts/create-account");
            return modelAndView;
        }

        BankAccountsServiceModel bankAccountsServiceModel = this.modelMapper
                .map(bankAccountAddBindingModel, BankAccountsServiceModel.class);

        this.bankAccountsService.createBankAccount(bankAccountsServiceModel, bankAccountAddBindingModel
                .getAccountOwner() != null ? bankAccountAddBindingModel.getAccountOwner() : principal.getName());

        modelAndView.setViewName("redirect:/bankaccounts/home");
        return modelAndView;
    }

    @GetMapping("/own")
    public ModelAndView showOwnBankAccounts(Principal principal, ModelAndView modelAndView) {

        //TODO integrate the ViewModel instead of the owner's list
        modelAndView.addObject("bankAccountsOwner",
                (User) this.usersService.loadUserByUsername(principal.getName()));

        modelAndView.setViewName("bankaccounts/own-accounts");
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView showAllBankAccounts(ModelAndView modelAndView) {

        modelAndView.addObject("allBankAccounts",
                this.bankAccountsService.listAllBankAccounts());

        modelAndView.setViewName("bankaccounts/all-accounts");
        return modelAndView;
    }

    @GetMapping("/edit/{iban}")
    public ModelAndView editBankAccount(@PathVariable("iban") String iban, ModelAndView modelAndView) {
        BankAccountsEditBindingModel bankAccountsEditBindingModel = this.modelMapper
                .map(this.bankAccountsService.loadBankAccountByIban(iban), BankAccountsEditBindingModel.class);

        modelAndView.addObject("bindingModel", bankAccountsEditBindingModel);
        modelAndView.addObject("accountTypes", AccountType.values());
        modelAndView.addObject("accountStatuses", AccountStatus.values());

        modelAndView.setViewName("bankaccounts/edit-account");
        return modelAndView;
    }

    @PostMapping("/edit/{iban}")
    public ModelAndView editBankAccountConfirm(
            @Valid @ModelAttribute(name = "bindingModel") BankAccountsEditBindingModel bankAccountsEditBindingModel,
            BindingResult bindingResult, @PathVariable("iban") String iban, ModelAndView modelAndView) {

        modelAndView.addObject("bindingModel", bankAccountsEditBindingModel);

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("bankaccounts/edit-account");
            return modelAndView;
        }

        BankAccountsServiceModel bankAccountsServiceModel = this.modelMapper
                .map(bankAccountsEditBindingModel, BankAccountsServiceModel.class);

        this.bankAccountsService.editBankAccount(bankAccountsServiceModel);

        modelAndView.setViewName("redirect:/bankaccounts/home");
        return modelAndView;
    }

    @GetMapping("/delete/{iban}")
    public ModelAndView delete(@PathVariable("iban") String iban, ModelAndView modelAndView) {
        BankAccountsEditBindingModel bankAccountsEditBindingModel = this.modelMapper
                .map(this.bankAccountsService.loadBankAccountByIban(iban), BankAccountsEditBindingModel.class);

        modelAndView.addObject("bindingModel", bankAccountsEditBindingModel);
        modelAndView.addObject("accountTypes", AccountType.values());
        modelAndView.addObject("accountStatuses", AccountStatus.values());

        modelAndView.setViewName("bankaccounts/delete-account");
        return modelAndView;
    }

    @PostMapping("/delete/{iban}")
    public ModelAndView deleteBankAccountConfirm(@PathVariable("iban") String iban, ModelAndView modelAndView) {

        try {
            this.bankAccountsService.deleteBankAccount(iban);

            modelAndView.setViewName("redirect:/bankaccounts/home");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return modelAndView;
    }
}
