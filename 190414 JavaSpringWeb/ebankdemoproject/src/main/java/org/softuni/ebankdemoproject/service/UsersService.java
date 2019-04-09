package org.softuni.ebankdemoproject.service;

import org.softuni.ebankdemoproject.domain.models.service.UserServiceModel;
import org.softuni.ebankdemoproject.domain.models.view.UsersViewModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsersService extends UserDetailsService {

    boolean registerUser(UserServiceModel userServiceModel);

    List<UsersViewModel> listAllUsers();

    void changeRole(String id, String role);

    UserServiceModel editUserProfile(UserServiceModel userServiceModel);

    UserDetails loadUserById(String id);

    void deleteUser(String id);
}
