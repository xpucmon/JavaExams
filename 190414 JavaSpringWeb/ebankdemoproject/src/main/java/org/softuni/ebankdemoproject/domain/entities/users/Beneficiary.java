package org.softuni.ebankdemoproject.domain.entities.users;

import org.iban4j.Iban;
import org.softuni.ebankdemoproject.domain.entities.BaseEntity;
import org.softuni.ebankdemoproject.domain.entities.users.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "beneficiaries")
public class Beneficiary extends BaseEntity {
    private User author;
    private String firstName;
    private String lastName;
    private Iban iban;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "author", referencedColumnName = "id")
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(nullable = false)
    public Iban getIban() {
        return iban;
    }

    public void setIban(Iban iban) {
        this.iban = iban;
    }
}
