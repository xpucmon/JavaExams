package org.softuni.ebankdemoproject.service;

import org.softuni.ebankdemoproject.domain.models.service.RoleServiceModel;

import java.util.Set;

public interface RolesService {

    void seedRolesInDb();

    Set<RoleServiceModel> findAllRoles();

    RoleServiceModel findByAuthority(String authority);
}
