package org.softuni.ebankdemoproject.repository;

import org.softuni.ebankdemoproject.domain.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

}
