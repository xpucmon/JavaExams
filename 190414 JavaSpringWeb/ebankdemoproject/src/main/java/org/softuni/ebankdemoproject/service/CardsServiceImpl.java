package org.softuni.ebankdemoproject.service;

import org.modelmapper.ModelMapper;
import org.softuni.ebankdemoproject.domain.entities.bankaccounts.BankAccount;
import org.softuni.ebankdemoproject.domain.entities.cards.Card;
import org.softuni.ebankdemoproject.domain.entities.cards.CardStatus;
import org.softuni.ebankdemoproject.domain.entities.cards.CardType;
import org.softuni.ebankdemoproject.domain.entities.users.User;
import org.softuni.ebankdemoproject.domain.models.binding.CardAddBindingModel;
import org.softuni.ebankdemoproject.domain.models.service.BankAccountsServiceModel;
import org.softuni.ebankdemoproject.domain.models.service.CardServiceModel;
import org.softuni.ebankdemoproject.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class CardsServiceImpl implements CardsService {
    private final CardsRepository cardsRepository;
    private final BankAccountsService bankAccountsService;
    private final UsersService usersService;
    private final ModelMapper modelMapper;
    private final Validator validator;

    @Autowired
    public CardsServiceImpl(CardsRepository cardsRepository, BankAccountsService bankAccountsService, UsersService usersService, ModelMapper modelMapper, Validator validator) {
        this.cardsRepository = cardsRepository;
        this.bankAccountsService = bankAccountsService;
        this.usersService = usersService;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean addCard(CardAddBindingModel cardAddBindingModel, CardType cardType) {

        if (this.validator.validate(cardAddBindingModel).size() != 0) {
            throw new IllegalArgumentException("Error processing new card request!");
        }

        BankAccountsServiceModel bankAccountsServiceModel = this.bankAccountsService
                .loadBankAccountByIban(cardAddBindingModel.getBankAccount());

        CardServiceModel cardServiceModel = this.modelMapper
                .map(cardAddBindingModel, CardServiceModel.class);

        UserDetails cardHolder = this.usersService
                .loadUserByUsername(bankAccountsServiceModel.getAccountOwner().getUsername());

        cardServiceModel.setCardNumber(this.generateNewCardNumber());
        cardServiceModel.setCardHolder((User) cardHolder);
        cardServiceModel.setBankAccount(this.modelMapper.map(bankAccountsServiceModel, BankAccount.class));
        cardServiceModel.setCardType(cardType);
        cardServiceModel.setCardStatus(CardStatus.NOT_ACTIVE);
        cardServiceModel.setIssueDate(LocalDateTime.now());
        cardServiceModel.setExpirationDate(LocalDateTime.now().plusYears(3));
        cardServiceModel.setCreditAmountCurrent(cardServiceModel.getCreditAmountMax());
        cardServiceModel.setCreditAmountDue(new BigDecimal(0));

        Card toSave = this.modelMapper.map(cardServiceModel, Card.class);

        try {
            this.cardsRepository.save(toSave);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateNewCardNumber() {
        StringBuilder accountCode = new StringBuilder();

        StringBuilder number = new StringBuilder();
        for (int i = 1; i <= 16; i++) {
            number.append(new Random().nextInt(10));
            if (i % 4 == 0 && i < 16) number.append(" ");
        }

        String cardNumber = number.toString();
        Card cardByCardNumber = this.cardsRepository.findCardByCardNumber(cardNumber).orElse(null);

        if (cardByCardNumber != null) {
            cardNumber = generateNewCardNumber();
        }
        return cardNumber;
    }

    @Override
    public List<CardServiceModel> listUserCards(String cardHolderName) {
        User cardHolder = (User) this.usersService.loadUserByUsername(cardHolderName);

        List<Card> allByCardHolder = this.cardsRepository.findAllByCardHolder(cardHolder);

        return Arrays.asList(this.modelMapper.map(allByCardHolder, CardServiceModel[].class));
    }

    @Override
    public List<CardServiceModel> listAllCards() {
        return Arrays.asList(this.modelMapper.map(
                this.cardsRepository.findAllByOrderByCardHolder(),
                CardServiceModel[].class));
    }


    @Override
    public CardServiceModel loadCardByCardNumber(String cardNumber) {
        Card cardByCardNumber = this.cardsRepository.findCardByCardNumber(cardNumber)
                .orElseThrow(() -> new IllegalArgumentException("This card could not be found!"));

        return this.modelMapper.map(cardByCardNumber, CardServiceModel.class);
    }

    @Override
    public CardServiceModel editCard(CardServiceModel cardServiceModel) {
        return null;
    }

    //    @Override
//    public BankAccountsServiceModel editBankAccount(BankAccountsServiceModel bankAccountsServiceModel) {
//        BankAccount bankAccount = this.bankAccountsRepository.findBankAccountByIban(bankAccountsServiceModel.getIban())
//                .orElseThrow(() -> new IllegalArgumentException("This bank account does not exist!"));
//
//        bankAccount.setAccountType(bankAccountsServiceModel.getAccountType());
//        bankAccount.setAccountStatus(bankAccountsServiceModel.getAccountStatus());
//        bankAccount.setBalance(bankAccountsServiceModel.getBalance());
//        bankAccount.setInterestRate(bankAccountsServiceModel.getInterestRate());
//
//        BankAccount saveBankAccount = this.bankAccountsRepository.save(bankAccount);
//
//        return this.modelMapper.map(saveBankAccount, BankAccountsServiceModel.class);
//    }


    @Override
    public void deactivateCard(String cardNumber, String principalName) {
        Card cardByCardNumber = this.cardsRepository.findCardByCardNumber(cardNumber)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        try {
            cardByCardNumber.setCardStatus(CardStatus.DEACTIVATED);

            this.cardsRepository.save(cardByCardNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCard(String cardNumber) {
        Card card = this.cardsRepository.findCardByCardNumber(cardNumber)
                .orElseThrow(() -> new IllegalArgumentException("This card could not be found!"));

        try {
            this.cardsRepository.deleteById(card.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
