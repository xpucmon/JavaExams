package org.softuni.ebankdemoproject.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.ebankdemoproject.domain.models.binding.UserRegisterBindingModel;
import org.softuni.ebankdemoproject.domain.models.service.UserServiceModel;
import org.softuni.ebankdemoproject.domain.models.view.UsersViewModel;
import org.softuni.ebankdemoproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

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
                                 @ModelAttribute(name = "bindingModel") UserRegisterBindingModel bindingModel){

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

        if (!bindingModel.getPassword().equals(bindingModel.getConfirmPassword())){
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
                              @ModelAttribute(name = "bindingModel") UserRegisterBindingModel bindingModel){
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/users")
    public ModelAndView showUsers(ModelAndView modelAndView) {
        List<UsersViewModel> usersViewModels = Arrays.asList(this.modelMapper
                .map(this.usersService.listAllUsers(), UsersViewModel[].class));

        modelAndView.addObject("users", usersViewModels);

        modelAndView.setViewName("users");
        return modelAndView;
    }

//    @GetMapping("/users/rolechange/admin/{id}")
//    public ModelAndView makeAdmin(@PathVariable(name = "id") String id, ModelAndView modelAndView){
//        return changeRole(modelAndView, id, "ADMIN");
//    }
//
//    @GetMapping("/users/rolechange/moderator/{id}")
//    public ModelAndView makeModerator(@PathVariable(name = "id") String id, ModelAndView modelAndView){
//        return changeRole(modelAndView, id, "MODERATOR");
//    }
//
//    @GetMapping("/users/rolechange/user/{id}")
//    public ModelAndView makeUser(@PathVariable(name = "id") String id, ModelAndView modelAndView){
//        return changeRole(modelAndView, id, "USER");
//    }
//
//    private ModelAndView changeRole(ModelAndView modelAndView, String id, String role) {
//        this.usersService.changeRole(id, new RoleServiceModel(){{
//            setAuthority(role);
//        }});
//        modelAndView.setViewName("redirect:/users");
//        return modelAndView;
//    }
}
