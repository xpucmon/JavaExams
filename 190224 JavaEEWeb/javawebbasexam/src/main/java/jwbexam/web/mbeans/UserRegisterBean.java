package jwbexam.web.mbeans;

import org.modelmapper.ModelMapper;
import jwbexam.domain.models.binding.UserRegisterBindingModel;
import jwbexam.domain.models.service.UserServiceModel;
import jwbexam.service.UserService;
import jwbexam.util.ValidationUtil;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScoped
public class UserRegisterBean {

    private UserRegisterBindingModel userRegisterBindingModel;
    private UserService userService;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;

    public UserRegisterBean() {
        this.userRegisterBindingModel = new UserRegisterBindingModel();
    }

    @Inject
    public UserRegisterBean(UserService userService, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this();
        this.userService = userService;
        this.modelMapper = modelMapper;
		this.validationUtil = validationUtil;
    }

    public UserRegisterBindingModel getUserRegisterBindingModel() {
        return userRegisterBindingModel;
    }

    public void setUserRegisterBindingModel(UserRegisterBindingModel userRegisterBindingModel) {
        this.userRegisterBindingModel = userRegisterBindingModel;
    }

    public void register() throws IOException {
        if (!this.userRegisterBindingModel.getPassword().equals(this.userRegisterBindingModel.getConfirmPassword())){
            throw new IllegalArgumentException("Passwords do not match!");
        }

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		
		if (validationUtil.isValid(this.userRegisterBindingModel)) {
			this.userService.userRegister(this.modelMapper
					.map(this.userRegisterBindingModel, UserServiceModel.class));

			context.redirect("/faces/jsf/login.xhtml");
		} else {
            context.redirect("/faces/jsf/register.xhtml");
        }
    }
}
