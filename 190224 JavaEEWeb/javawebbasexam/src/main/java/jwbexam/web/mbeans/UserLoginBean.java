package jwbexam.web.mbeans;

import org.modelmapper.ModelMapper;
import jwbexam.domain.models.binding.UserLoginBindingModel;
import jwbexam.domain.models.service.UserServiceModel;
import jwbexam.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Named
@RequestScoped
public class UserLoginBean {

    private UserLoginBindingModel userLoginBindingModel;
    private UserService userService;
    private ModelMapper modelMapper;

    public UserLoginBean() {
        this.userLoginBindingModel = new UserLoginBindingModel();
    }

    @Inject
    public UserLoginBean(UserService userService, ModelMapper modelMapper) {
        this();
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public UserLoginBindingModel getUserLoginBindingModel() {
        return userLoginBindingModel;
    }

    public void setUserLoginBindingModel(UserLoginBindingModel userLoginBindingModel) {
        this.userLoginBindingModel = userLoginBindingModel;
    }

    public void login() throws IOException {
        UserServiceModel userServiceModel = this.userService.userLogin(this.modelMapper
                .map(this.userLoginBindingModel, UserServiceModel.class));

        if (userServiceModel == null){
            FacesContext.getCurrentInstance().getExternalContext().redirect("/faces/jsf/login.xhtml");
            return;
        }

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);

        session.setAttribute("username", userServiceModel.getUsername());
        FacesContext.getCurrentInstance().getExternalContext().redirect("/faces/jsf/home.xhtml");
    }
}
