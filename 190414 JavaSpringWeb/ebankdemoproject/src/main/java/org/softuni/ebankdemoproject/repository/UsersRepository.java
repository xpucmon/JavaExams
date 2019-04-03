package org.softuni.ebankdemoproject.repository;

import org.softuni.ebankdemoproject.domain.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, String> {
}
