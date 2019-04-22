package org.softuni.ebankdemoproject.repository;

import org.softuni.ebankdemoproject.domain.entities.cards.Card;
import org.softuni.ebankdemoproject.domain.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardsRepository extends JpaRepository<Card, String> {

    List<Card> findAllByOrderByCardHolder();

    Optional<Card> findCardByCardNumber(String cardNumber);

    List<Card> findAllByCardHolder(User user);
}
