package org.softuni.ebankdemoproject.domain.entities.users;

import org.softuni.ebankdemoproject.domain.entities.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

@Entity(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {
    private String authority;

    public Role() {
    }

    public Role(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return this.getAuthority();
    }
}