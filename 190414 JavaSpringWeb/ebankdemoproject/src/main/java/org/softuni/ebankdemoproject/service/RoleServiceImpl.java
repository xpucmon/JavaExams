package org.softuni.ebankdemoproject.service;

import org.modelmapper.ModelMapper;
import org.softuni.ebankdemoproject.domain.entities.users.Role;
import org.softuni.ebankdemoproject.domain.entities.users.RoleConstant;
import org.softuni.ebankdemoproject.domain.models.service.RoleServiceModel;
import org.softuni.ebankdemoproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedRolesInDb() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.save(new Role(RoleConstant.ROOT.name()));
            this.roleRepository.save(new Role(RoleConstant.ADMIN.name()));
            this.roleRepository.save(new Role(RoleConstant.EMPLOYEE.name()));
            this.roleRepository.save(new Role(RoleConstant.USER.name()));
        }
    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return this.roleRepository.findAll()
                .stream()
                .map(r -> this.modelMapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        return this.modelMapper.map(this.roleRepository.findByAuthority(authority), RoleServiceModel.class);
    }
}
