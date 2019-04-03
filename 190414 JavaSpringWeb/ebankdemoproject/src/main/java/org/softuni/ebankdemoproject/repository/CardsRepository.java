package org.softuni.ebankdemoproject.repository;

import org.softuni.ebankdemoproject.domain.entities.cards.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardsRepository extends JpaRepository<Card, String> {
}
