package org.softuni.ebankdemoproject.service;

import org.softuni.ebankdemoproject.domain.entities.cards.CardType;
import org.softuni.ebankdemoproject.domain.models.binding.CardAddBindingModel;
import org.softuni.ebankdemoproject.domain.models.service.CardServiceModel;

import java.util.List;

public interface CardsService {

    boolean addCard(CardAddBindingModel cardAddBindingModel, CardType cardType);

    List<CardServiceModel> listUserCards(String cardHolderName);

    List<CardServiceModel> listAllCards();

    CardServiceModel loadCardByCardNumber(String cardNumber);

    CardServiceModel editCard(CardServiceModel cardServiceModel);

    void deactivateCard(String cardNumber, String principalName);

    void deleteCard(String id);
}