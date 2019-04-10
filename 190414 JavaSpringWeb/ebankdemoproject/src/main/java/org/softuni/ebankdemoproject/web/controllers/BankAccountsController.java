package org.softuni.ebankdemoproject.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.ebankdemoproject.domain.entities.bankaccounts.AccountStatus;
import org.softuni.ebankdemoproject.domain.entities.bankaccounts.AccountType;
import org.softuni.ebankdemoproject.domain.models.binding.BankAccountAddBindingModel;
import org.softuni.ebankdemoproject.domain.models.binding.BankAccountsEditBindingModel;
import org.softuni.ebankdemoproject.domain.models.service.BankAccountsServiceModel;
import org.softuni.ebankdemoproject.service.BankAccountsService;
import org.softuni.ebankdemoproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
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
    public ModelAndView home(HttpSession session, Authentication principal, ModelAndView modelAndView) {

//        modelAndView.addObject("user", principal.getName());
        modelAndView.setViewName("bankaccounts/home-accounts");

        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createBankAccount(ModelAndView modelAndView,
                                       @ModelAttribute(name = "bindingModel")
                                               BankAccountAddBindingModel bankAccountAddBindingModel) {

        modelAndView.addObject("bindingModel", bankAccountAddBindingModel);
        modelAndView.addObject("accountTypes", AccountType.values());

        modelAndView.setViewName("bankaccounts/create-account");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createBankAccountConfirm( Principal principal,
            @Valid @ModelAttribute(name = "bindingModel") BankAccountAddBindingModel bankAccountAddBindingModel,
            BindingResult bindingResult, ModelAndView modelAndView) {

        modelAndView.addObject("bindingModel", bankAccountAddBindingModel);

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("bankaccounts/create");
            return modelAndView;
        }

        BankAccountsServiceModel bankAccountsServiceModel = this.modelMapper
                .map(bankAccountAddBindingModel, BankAccountsServiceModel.class);

        this.bankAccountsService.createBankAccount(bankAccountsServiceModel, principal.getName());

        modelAndView.setViewName("bankaccounts/home-accounts");
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView showBankAccounts(Principal principal, ModelAndView modelAndView) {

        modelAndView.addObject("bankAccountOwner",
                this.usersService.loadUserByUsername(principal.getName()));

        modelAndView.addObject("bankaccounts",
                this.bankAccountsService.listAllUserBankAccounts(principal.getName()));

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
            @PathVariable("iban") String iban,
            @Valid @ModelAttribute(name = "bindingModel") BankAccountsEditBindingModel bankAccountsEditBindingModel,
            BindingResult bindingResult, ModelAndView modelAndView) {

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

    @PostMapping("/delete/{id}")
    public ModelAndView deleteBankAccountConfirm(@PathVariable("id") String id, ModelAndView modelAndView) {

        try {
            this.bankAccountsService.deleteBankAccount(id);

            modelAndView.setViewName("redirect:/bankaccounts/home");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return modelAndView;
    }
}
