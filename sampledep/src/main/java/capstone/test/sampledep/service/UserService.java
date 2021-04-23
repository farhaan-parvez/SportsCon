package capstone.test.sampledep.service;

import capstone.test.sampledep.data.User;
import capstone.test.sampledep.repository.UserRepository;
import capstone.test.sampledep.request.LoginRequest;
import capstone.test.sampledep.request.RegisterUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public User registerUser(RegisterUserRequest registerUserRequest) throws Exception{
        User user = userRepository.findByEmail(registerUserRequest.getEmail());
        if (Objects.nonNull(user)) {
            throw new Exception("User with Email already exists");
        }
        user = new User();
        user.setEmail(registerUserRequest.getEmail());
        user.setPhone(registerUserRequest.getPhone());
        user.setName(registerUserRequest.getName());
        user.setPassword(registerUserRequest.getPassword());
        return userRepository.save(user);
    }

    public User loginUser(LoginRequest loginRequest) throws Exception{
        User user = userRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        if (Objects.isNull(user)) {
            throw new Exception("Email or Password is incorrect");
        }
        return user;
    }
}
