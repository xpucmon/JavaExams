package org.softuni.ebankdemoproject.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.ebankdemoproject.domain.entities.users.RoleConstant;
import org.softuni.ebankdemoproject.domain.models.binding.UserRegisterBindingModel;
import org.softuni.ebankdemoproject.domain.models.service.UserServiceModel;
import org.softuni.ebankdemoproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UsersService usersService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UsersService usersService, ModelMapper modelMapper) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(ModelAndView modelAndView,
                                 @ModelAttribute(name = "bindingModel") UserRegisterBindingModel bindingModel) {

        modelAndView.addObject("bindingModel", bindingModel);

        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(
            @Valid
            @ModelAttribute(name = "bindingModel") UserRegisterBindingModel bindingModel,
            BindingResult bindingResult,
            ModelAndView modelAndView) {

        modelAndView.addObject("bindingModel", bindingModel);

        if (!bindingModel.getPassword().equals(bindingModel.getConfirmPassword())) {
            modelAndView.setViewName("register");
            return modelAndView;
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
            return modelAndView;
        }

        UserServiceModel userServiceModel = this.modelMapper.map(bindingModel, UserServiceModel.class);
        this.usersService.registerUser(userServiceModel);

        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login(ModelAndView modelAndView,
                              @ModelAttribute(name = "bindingModel") UserRegisterBindingModel bindingModel) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/users")
    public ModelAndView showUsers(ModelAndView modelAndView) {

        modelAndView.addObject("users", this.usersService.listAllUsers());
        modelAndView.addObject("roleRoot", RoleConstant.ROOT.name());
        modelAndView.addObject("roleAdmin", RoleConstant.ADMIN.name());
        modelAndView.addObject("roleEmployee", RoleConstant.EMPLOYEE.name());
        modelAndView.addObject("roleUser", RoleConstant.USER.name());

        modelAndView.setViewName("users");
        return modelAndView;
    }

    @GetMapping("/users/rolechange/admin/{id}")
    public ModelAndView makeAdmin(@PathVariable(name = "id") String id, ModelAndView modelAndView){
        this.usersService.changeRole(id, RoleConstant.ADMIN.name());

        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }

    @GetMapping("/users/rolechange/employee/{id}")
    public ModelAndView makeEmployee(@PathVariable(name = "id") String id, ModelAndView modelAndView){
        this.usersService.changeRole(id, RoleConstant.EMPLOYEE.name());

        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }

    @GetMapping("/users/rolechange/user/{id}")
    public ModelAndView makeUser(@PathVariable(name = "id") String id, ModelAndView modelAndView){
        this.usersService.changeRole(id, RoleConstant.USER.name());

        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }
}
