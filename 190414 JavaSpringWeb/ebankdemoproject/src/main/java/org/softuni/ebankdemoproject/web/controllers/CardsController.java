package org.softuni.ebankdemoproject.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.ebankdemoproject.domain.entities.bankaccounts.AccountType;
import org.softuni.ebankdemoproject.domain.entities.cards.CardBrand;
import org.softuni.ebankdemoproject.domain.entities.cards.CardStatus;
import org.softuni.ebankdemoproject.domain.entities.cards.CardType;
import org.softuni.ebankdemoproject.domain.models.binding.CardAddBindingModel;
import org.softuni.ebankdemoproject.domain.models.binding.CardEditBindingModel;
import org.softuni.ebankdemoproject.service.BankAccountsService;
import org.softuni.ebankdemoproject.service.CardsService;
import org.softuni.ebankdemoproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cards")
public class CardsController {

    private final CardsService cardsService;
    private final UsersService usersService;
    private final BankAccountsService bankAccountsService;
    private final ModelMapper modelMapper;

    @Autowired
    public CardsController(CardsService cardsService, UsersService usersService, BankAccountsService bankAccountsService,
                           ModelMapper modelMapper) {
        this.cardsService = cardsService;
        this.usersService = usersService;
        this.bankAccountsService = bankAccountsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/home")
    @PreAuthorize(value = "isAuthenticated()")
    public ModelAndView home(Principal principal, ModelAndView modelAndView) {

        modelAndView.addObject("cardHolder",
                this.usersService.loadUserByUsername(principal.getName()));
        modelAndView.addObject("owncards",
                this.cardsService.listUserCards(principal.getName()));
        modelAndView.addObject("allUsersCards",
                this.cardsService.listAllCards());

        modelAndView.setViewName("cards/home-cards");
        return modelAndView;
    }

    @GetMapping("/credit")
    public ModelAndView addCreditCard(Principal principal, ModelAndView modelAndView,
                                @ModelAttribute(name = "bindingModel") CardAddBindingModel cardAddBindingModel) {

        modelAndView.addObject("bindingModel", cardAddBindingModel);

        modelAndView.addObject("cardOwners", this.usersService.listAllUsers());
        modelAndView.addObject("creditCardBrands",
                new CardBrand[]{CardBrand.VISA_CREDIT_CARD, CardBrand.MASTERCARD_CREDIT_CARD});
        modelAndView.addObject("ownCreditAccounts",
                this.bankAccountsService.listUserBankAccounts(principal.getName())
                        .stream().filter(b -> b.getAccountType().equals(AccountType.CREDIT))
                        .collect(Collectors.toList()));
        modelAndView.addObject("allCreditAccounts",
                this.bankAccountsService.listAllBankAccounts()
                        .stream().filter(b -> b.getAccountType().equals(AccountType.CREDIT))
                        .collect(Collectors.toList()));

        modelAndView.setViewName("cards/add-creditcard");
        return modelAndView;
    }

    @PostMapping("/credit")
    public ModelAndView addCreditCardConfirm(
            @Valid @ModelAttribute(name = "bindingModel") CardAddBindingModel cardAddBindingModel,
            BindingResult bindingResult, Principal principal, ModelAndView modelAndView) {

        modelAndView.addObject("bindingModel", cardAddBindingModel);

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("cards/add-creditcard");
            return modelAndView;
        }

        this.cardsService.addCard(cardAddBindingModel, CardType.CREDIT_CARD);

        modelAndView.setViewName("redirect:/cards/home");
        return modelAndView;
    }

    @GetMapping("/debit")
    public ModelAndView addDebitCard(Principal principal, ModelAndView modelAndView,
                                @ModelAttribute(name = "bindingModel") CardAddBindingModel cardAddBindingModel) {

        modelAndView.addObject("bindingModel", cardAddBindingModel);

        modelAndView.addObject("cardOwners", this.usersService.listAllUsers());
        modelAndView.addObject("debitCardBrands",
                new CardBrand[]{CardBrand.VISA_ELECTRON_DEBIT_CARD, CardBrand.MAESTRO_DEBIT_CARD});
        modelAndView.addObject("ownCheckingAccounts",
                this.bankAccountsService.listUserBankAccounts(principal.getName())
                        .stream().filter(b -> b.getAccountType().equals(AccountType.CHECKING))
                        .collect(Collectors.toList()));
        modelAndView.addObject("allCheckingAccounts",
                this.bankAccountsService.listAllBankAccounts()
                        .stream().filter(b -> b.getAccountType().equals(AccountType.CHECKING))
                        .collect(Collectors.toList()));

        modelAndView.setViewName("cards/add-debitcard");
        return modelAndView;
    }

    @PostMapping("/debit")
    public ModelAndView addDebitCardConfirm(
            @Valid @ModelAttribute(name = "bindingModel") CardAddBindingModel cardAddBindingModel,
            BindingResult bindingResult, Principal principal, ModelAndView modelAndView) {

        modelAndView.addObject("bindingModel", cardAddBindingModel);

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("cards/add-debitcard");
            return modelAndView;
        }

        this.cardsService.addCard(cardAddBindingModel, CardType.DEBIT_CARD);

        modelAndView.setViewName("redirect:/cards/home");
        return modelAndView;
    }

    @GetMapping("/own")
    public ModelAndView showOwnCards(Principal principal, ModelAndView modelAndView) {

        modelAndView.addObject("userCards", this.cardsService.listUserCards(principal.getName()));
        modelAndView.addObject("notActiveStatus", CardStatus.NOT_ACTIVE);

        modelAndView.setViewName("cards/own-cards");
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView showAllCards(ModelAndView modelAndView) {

        modelAndView.addObject("allUsersCards", this.cardsService.listAllCards());
        modelAndView.addObject("notActiveStatus", CardStatus.NOT_ACTIVE);

        modelAndView.setViewName("cards/all-cards");
        return modelAndView;
    }

//    @GetMapping("/edit/{cardNumber}")
//    public ModelAndView editBankAccount(@PathVariable("cardNumber") String cardNumber, ModelAndView modelAndView) {
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
//            @Valid @ModelAttribute(name = "bindingModel") BankAccountsEditBindingModel bankAccountsEditBindingModel,
//            BindingResult bindingResult, @PathVariable("iban") String iban, ModelAndView modelAndView) {
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

    @GetMapping("/deactivate/{cardNumber}")
    public ModelAndView delete(@PathVariable("cardNumber") String cardNumber, ModelAndView modelAndView) {
        CardEditBindingModel cardEditBindingModel = this.modelMapper
                .map(this.bankAccountsService.loadBankAccountByIban(cardNumber), CardEditBindingModel.class);

        modelAndView.addObject("bindingModel", cardEditBindingModel);
        modelAndView.addObject("cardTypes", CardType.values());
        modelAndView.addObject("cardStatuses", CardStatus.values());

        modelAndView.setViewName("bankaccounts/deactivate-card");
        return modelAndView;
    }

    @PostMapping("/deactivate/{cardNumber}")
    public ModelAndView deleteCardConfirm(@PathVariable("cardNumber") String cardNumber, ModelAndView modelAndView) {

        try {
            this.cardsService.deleteCard(cardNumber);

            modelAndView.setViewName("redirect:/cards/home");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return modelAndView;
    }
}
