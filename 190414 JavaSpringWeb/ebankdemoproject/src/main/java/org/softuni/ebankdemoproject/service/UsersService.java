package org.softuni.ebankdemoproject.service;

import org.softuni.ebankdemoproject.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsersService extends UserDetailsService {

    boolean registerUser(UserServiceModel userServiceModel);

    List<UserServiceModel> listAllUsers();

//    void changeRole(String id, RoleServiceModel roleServiceModel);

}
