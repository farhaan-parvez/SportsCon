package capstone.test.sampledep.service;

import capstone.test.sampledep.data.User;
import capstone.test.sampledep.repository.UserRepository;
import capstone.test.sampledep.request.EditUserRequest;
import capstone.test.sampledep.request.LoginRequest;
import capstone.test.sampledep.request.RegisterUserRequest;
import capstone.test.sampledep.response.UserProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingsService ratingsService;

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public UserProfileResponse getUserProfile(Long userId) throws Exception{
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent())
            throw new Exception("No such user with id " +  userId);
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setAccountDetails(optionalUser.get());
        ratingsService.setUserProfileResponse(userId, userProfileResponse);
        return userProfileResponse;
    }

    public User registerUser(RegisterUserRequest registerUserRequest) throws Exception{
        User user = userRepository.findByEmail(registerUserRequest.getEmail());
        if (Objects.nonNull(user)) {
            throw new Exception("User with Email already exists");
        }
        if (StringUtils.isEmpty(registerUserRequest.getEmail())|| StringUtils.isEmpty(registerUserRequest.getName()))
            throw new Exception("Email and Name cannot be empty");
        user = new User();
        user.setEmail(registerUserRequest.getEmail());
        user.setPhone(registerUserRequest.getPhone());
        user.setName(registerUserRequest.getName());
        user.setPassword(registerUserRequest.getPassword());
        user.setImage(registerUserRequest.getImage());
        return userRepository.save(user);
    }

    public User editUser(Long userId, EditUserRequest request) throws Exception{
        User user = userRepository.findById(userId).get();
        if (Objects.isNull(user)) {
            throw new Exception("No such user with id : " + userId);
        }
        user.setPhone(request.getPhone());
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setImage(request.getImage());
        return userRepository.save(user);
    }

    public User loginUser(String email, String password) throws Exception{
        User user = userRepository.findByEmailAndPassword(email, password);
        if (Objects.isNull(user)) {
            throw new Exception("Email or Password is incorrect");
        }
        return user;
    }
}
