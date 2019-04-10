package org.softuni.ebankdemoproject.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.ebankdemoproject.domain.entities.users.RoleConstant;
import org.softuni.ebankdemoproject.domain.models.binding.UserEditBindingModel;
import org.softuni.ebankdemoproject.domain.models.binding.UserRegisterBindingModel;
import org.softuni.ebankdemoproject.domain.models.service.UserServiceModel;
import org.softuni.ebankdemoproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
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

        modelAndView.setViewName("users/register");
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
            modelAndView.setViewName("users/register");
            return modelAndView;
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("users/register");
            return modelAndView;
        }

        UserServiceModel userServiceModel = this.modelMapper.map(bindingModel, UserServiceModel.class);
        this.usersService.registerUser(userServiceModel);

        modelAndView.setViewName("users/login");
        return modelAndView;
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login(ModelAndView modelAndView,
                              @ModelAttribute(name = "bindingModel") UserRegisterBindingModel bindingModel) {
        modelAndView.setViewName("users/login");
        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView profile(ModelAndView modelAndView, Principal principal) {

        UserEditBindingModel userEditBindingModel = this.modelMapper.map(this.usersService
                .loadUserByUsername(principal.getName()), UserEditBindingModel.class);

        modelAndView.addObject("bindingModel", userEditBindingModel);

        modelAndView.setViewName("users/profile");
        return modelAndView;
    }

    @GetMapping("/all-users")
    public ModelAndView showUsers(ModelAndView modelAndView) {

        modelAndView.addObject("users", this.usersService.listAllUsers());
        modelAndView.addObject("roleRoot", RoleConstant.ROOT.name());
        modelAndView.addObject("roleAdmin", RoleConstant.ADMIN.name());
        modelAndView.addObject("roleEmployee", RoleConstant.EMPLOYEE.name());
        modelAndView.addObject("roleUser", RoleConstant.USER.name());

        modelAndView.setViewName("users/all-users");
        return modelAndView;
    }

    @GetMapping("/rolechange/admin/{id}")
    public ModelAndView makeAdmin(@PathVariable(name = "id") String id, ModelAndView modelAndView){
        this.usersService.changeRole(id, RoleConstant.ADMIN.name());

        modelAndView.setViewName("redirect:/users/all-users");
        return modelAndView;
    }

    @GetMapping("/rolechange/employee/{id}")
    public ModelAndView makeEmployee(@PathVariable(name = "id") String id, ModelAndView modelAndView){
        this.usersService.changeRole(id, RoleConstant.EMPLOYEE.name());

        modelAndView.setViewName("redirect:/users/all-users");
        return modelAndView;
    }

    @GetMapping("/rolechange/user/{id}")
    public ModelAndView makeUser(@PathVariable(name = "id") String id, ModelAndView modelAndView){
        this.usersService.changeRole(id, RoleConstant.USER.name());

        modelAndView.setViewName("redirect:/users/all-users");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editProfile(@PathVariable("id") String id, ModelAndView modelAndView) {

        UserEditBindingModel userEditBindingModel = this.modelMapper.map(this.usersService
                .loadUserById(id), UserEditBindingModel.class);

        modelAndView.addObject("bindingModel", userEditBindingModel);

        modelAndView.setViewName("users/edit-profile");
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editProfileConfirm(
            @PathVariable("id") String id,
            @Valid @ModelAttribute(name = "bindingModel") UserEditBindingModel userEditBindingModel,
            BindingResult bindingResult, ModelAndView modelAndView) {

        modelAndView.addObject("bindingModel", userEditBindingModel);

        if (!userEditBindingModel.getPassword().equals(userEditBindingModel.getConfirmPassword())) {
            modelAndView.setViewName("users/edit-profile");
            return modelAndView;
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("users/edit-profile");
            return modelAndView;
        }

        this.usersService.editUserProfile(this.modelMapper.map(userEditBindingModel, UserServiceModel.class));

        modelAndView.setViewName("redirect:/users/all-users");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") String id, ModelAndView modelAndView) {
        UserEditBindingModel userEditBindingModel = this.modelMapper.map(this.usersService
                .loadUserById(id), UserEditBindingModel.class);

        modelAndView.addObject("bindingModel", userEditBindingModel);

        modelAndView.setViewName("users/delete-profile");
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteProfileConfirm(@PathVariable("id") String id, ModelAndView modelAndView) {

        try {
            this.usersService.deleteUser(id);

            modelAndView.setViewName("redirect:/users/all-users");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return modelAndView;
    }
}
