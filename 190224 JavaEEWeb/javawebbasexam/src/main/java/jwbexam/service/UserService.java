package jwbexam.service;

import jwbexam.domain.models.service.UserServiceModel;

import java.util.List;

public interface UserService {

    void userRegister(UserServiceModel userServiceModel);

    UserServiceModel userLogin(UserServiceModel userServiceModel);

    UserServiceModel findUserByUsername(String username);

    List<UserServiceModel> findAllUsers();
	
	boolean updateUser(UserServiceModel userServiceModel);

}
