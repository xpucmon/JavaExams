package org.softuni.ebankdemoproject.domain.models.view;

import org.softuni.ebankdemoproject.domain.entities.users.Role;

import java.util.Set;

public class UsersViewModel {
    private String id;
    private String username;
    private String email;
    private Set<Role> authorities;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }
}
