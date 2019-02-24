package jwbexam.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import jwbexam.domain.entities.User;
import jwbexam.domain.models.service.UserServiceModel;
import jwbexam.repository.UserRepository;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Inject
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void userRegister(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(DigestUtils.sha256Hex(userServiceModel.getPassword()) );

        this.userRepository.save(user);
    }

    @Override
    public UserServiceModel userLogin(UserServiceModel userServiceModel) {
        User user = this.userRepository.findByUsername(userServiceModel.getUsername());

        if (user == null || !DigestUtils.sha256Hex(userServiceModel.getPassword()).equals(user.getPassword()) ){
            return null;
        }

        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        return this.modelMapper.map(this.userRepository
                .findByUsername(username), UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        return Arrays.asList(this.modelMapper
                .map(this.userRepository.findAll(), UserServiceModel[].class));
    }

	@Override
    public boolean updateUser(UserServiceModel userServiceModel) {
        User user = this.userRepository.update(this.modelMapper.map(userServiceModel, User.class));

        return user != null;
    }
}
