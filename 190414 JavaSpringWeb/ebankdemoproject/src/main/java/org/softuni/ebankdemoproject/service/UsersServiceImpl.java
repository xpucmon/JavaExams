package org.softuni.ebankdemoproject.service;

import org.modelmapper.ModelMapper;
import org.softuni.ebankdemoproject.domain.entities.users.Role;
import org.softuni.ebankdemoproject.domain.entities.users.User;
import org.softuni.ebankdemoproject.domain.models.service.UserServiceModel;
import org.softuni.ebankdemoproject.repository.RoleRepository;
import org.softuni.ebankdemoproject.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Validator validator;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository,
                            RoleRepository roleRepository,
                            ModelMapper modelMapper,
                            BCryptPasswordEncoder bCryptPasswordEncoder, Validator validator) {
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = bCryptPasswordEncoder;
        this.validator = validator;
    }

    @Override
    public boolean registerUser(UserServiceModel userServiceModel) {
        if (this.validator.validate(userServiceModel).size() != 0){
            throw new IllegalArgumentException("Error during registration!");
        }

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.passwordEncoder.encode(userServiceModel.getPassword()));
        this.seedRolesInDb();
        this.assignRolesToUser(user);

        try {
            this.usersRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<UserServiceModel> listAllUsers() {
        return Arrays.asList(this.modelMapper
                .map(this.usersRepository.findAll().toArray(), UserServiceModel[].class));
    }

//    @Override
//    public void changeRole(String id, RoleServiceModel roleServiceModel) {
//        UserServiceModel userServiceModel = this.modelMapper
//                .map(this.usersRepository.findById(id).get(), UserServiceModel.class);
//
//        if (userServiceModel.getAuthorities().stream().noneMatch(u -> u.getAuthority().equals("ADMIN"))){
//            Set<RoleServiceModel> roleServiceModels = new HashSet<>();
//            this.roleRepository.findAll().forEach(r -> {
//                RoleServiceModel map = this.modelMapper.map(r, RoleServiceModel.class);
//
//                if (!map.equals(roleServiceModel)){
//                    roleServiceModels.add(map);
//                }
//            });
//            userServiceModel.setAuthorities(roleServiceModels.stream()
//                    .map(r -> this.modelMapper.map(r, Role.class))
//                    .collect(Collectors.toSet()));
//        }
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.usersRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    private void seedRolesInDb() {
        if (this.usersRepository.count() == 0) {
            Role admin = new Role();
            admin.setAuthority("ADMIN");
            this.roleRepository.saveAndFlush(admin);

            Role moderator = new Role();
            moderator.setAuthority("MODERATOR");
            this.roleRepository.saveAndFlush(moderator);

            Role user = new Role();
            user.setAuthority("USER");
            this.roleRepository.saveAndFlush(user);
        }
    }

    private void assignRolesToUser(User user) {
        if (this.usersRepository.count() == 0) {
            user.getAuthorities().add(this.roleRepository.findByAuthority("ADMIN"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("MODERATOR"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("USER"));
        } else {
            user.getAuthorities().add(this.roleRepository.findByAuthority("USER"));
        }
    }
}
