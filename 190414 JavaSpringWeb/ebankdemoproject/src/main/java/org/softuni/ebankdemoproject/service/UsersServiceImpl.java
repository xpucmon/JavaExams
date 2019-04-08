package org.softuni.ebankdemoproject.service;

import org.modelmapper.ModelMapper;
import org.softuni.ebankdemoproject.domain.entities.users.RoleConstant;
import org.softuni.ebankdemoproject.domain.entities.users.User;
import org.softuni.ebankdemoproject.domain.models.service.RoleServiceModel;
import org.softuni.ebankdemoproject.domain.models.service.UserServiceModel;
import org.softuni.ebankdemoproject.domain.models.view.UsersViewModel;
import org.softuni.ebankdemoproject.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Validator validator;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository,
                            RoleService roleService,
                            ModelMapper modelMapper,
                            BCryptPasswordEncoder bCryptPasswordEncoder, Validator validator) {
        this.usersRepository = usersRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = bCryptPasswordEncoder;
        this.validator = validator;
    }

    @Override
    public boolean registerUser(UserServiceModel userServiceModel) {
        if (this.validator.validate(userServiceModel).size() != 0) {
            throw new IllegalArgumentException("Error during registration!");
        }

        if (this.usersRepository.count() == 0) {
            this.roleService.seedRolesInDb();
        }
        this.assignRolesToUser(userServiceModel);

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.passwordEncoder.encode(userServiceModel.getPassword()));

        try {
            this.usersRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<UsersViewModel> listAllUsers() {
        List<UserServiceModel> userServiceModels = Arrays.asList(this.modelMapper
                .map(this.usersRepository.findAll().toArray(), UserServiceModel[].class));

        return userServiceModels.stream().map(u -> {
            UsersViewModel usersViewModel = this.modelMapper.map(u, UsersViewModel.class);

            usersViewModel.setAuthorities(
                    u.getAuthorities()
                            .stream()
                            .map(RoleServiceModel::getAuthority)
                            .collect(Collectors.toSet()));

            return usersViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public void changeRole(String id, String role) {

        User user = this.usersRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);

        if (userServiceModel.getAuthorities().stream().noneMatch(u -> u
                .getAuthority().equals(RoleConstant.ROOT.name()))) {

            userServiceModel.getAuthorities().clear();

            if (role.equals(RoleConstant.USER.name())) {
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleConstant.USER.name()));
            } else if (role.equals(RoleConstant.EMPLOYEE.name())) {
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleConstant.USER.name()));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleConstant.EMPLOYEE.name()));
            } else if (role.equals(RoleConstant.ADMIN.name())) {
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleConstant.USER.name()));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleConstant.EMPLOYEE.name()));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleConstant.ADMIN.name()));
            }
        };

        this.usersRepository.save(this.modelMapper.map(userServiceModel, User.class));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.usersRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private void assignRolesToUser(UserServiceModel userServiceModel) {
        if (this.usersRepository.count() == 0) {
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleConstant.ROOT.name()));
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleConstant.ADMIN.name()));
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleConstant.EMPLOYEE.name()));
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleConstant.USER.name()));
        } else {
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority(RoleConstant.USER.name()));
        }
    }
}
